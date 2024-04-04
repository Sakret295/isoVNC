package SipInterface.SipJNI;

import Core.AbstractSipManager;
import Core.Channel.AbstractChannel;
import Core.Channel.AbstractChannelCallback;
import Core.Channel.ChannelAccessor;
import Core.Channel.Enums.ActiveStatus;
import Core.SipManager;
import javafx.application.Platform;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OldChannelCallback extends AbstractChannelCallback {
    public OldChannelCallback(AbstractSipManager manager) {
        super(manager);
    }

    private static int unsign(byte x){
        return Byte.toUnsignedInt(x);
    }

    private String[] channelNames = {"channel 1", "channel 2", "channel 3", "channel 4", "channel 5", "channel 6", "channel 7", "channel 8"};
    //TODO: implement shift button callback

    @Override
    public void processCallback(byte[] data, int length) {

        System.err.println("called from C");
//        System.out.println("data = " + Arrays.toString(data) + " length: " + length + "actual length: " + data.length);
        System.out.println("\nhexdata = ");
        String text = "not found";
        int channelNum = -1;
        int set = -1;
        int volume = -1;
        for (int i = 0; i < data.length; i++) {
            if (unsign(data[i]) == 0xF1 && unsign(data[i + 1]) == 0x09 && unsign(data[i + 2]) == 0x01 && unsign(data[i + 3]) == 0x03){
                if (unsign(data[i + 4]) == 0x7f) {
                    if (unsign(data[i + 6]) == 0x01 && unsign(data[i + 7]) == 0x01) {
                        set = 1;
                    } else
                        set = 0;
                }

                if (unsign(data[i + 4]) >= 0x00 && unsign(data[i+4]) <= 0x03){
                    volume = Integer.numberOfTrailingZeros(unsign(data[i + 7]));
                    channelNum = unsign(data[i+4]);
                    System.out.println("volume = " + volume + " channel = " + unsign(data[i+4]));

                    int finalChannelNum1 = channelNum;
                    int finalVolume = volume;
                    int color = 0;
                    AbstractChannel channel = manager.getChannel(finalChannelNum1);
                    if (unsign(data[i + 6]) == 0xFF){
                        color = 1;
                        ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.ACTIVE);
                    } else
                    if (unsign(data[i + 6]) == 0x00){
                        color = -1;
                        ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.INACTIVE);
                    } else
                    if(unsign(data[i + 6]) == 0xFE){
                        color = 2;
                        ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.ACTIVE);
                    } else {
                        ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.INACTIVE);
                    }
                    int finalColor = color;
//                    Platform.runLater(() -> HelloApplication.controller.setChannelVolume(finalChannelNum1, finalVolume, finalColor));
                    ChannelAccessor.getDefault().setVolume(channel, finalVolume);
                }
            }

            if (unsign(data[i]) == 0x0F && unsign(data[i + 1]) == 0x01) {
                System.out.println("found hex: " + data[i] + "; " + data[i + 1] + ";");
                text = new String(data, i + 5, 7, StandardCharsets.US_ASCII);
                System.out.println("ttext = " + text + "; length: " + text.length());
                channelNum = unsign(data[i + 3]);
                System.out.println("channelNum = " + channelNum);
                int finalChannelNum = channelNum;
                String finalText = text;
                AbstractChannel channel = manager.getChannel(finalChannelNum);
//                Platform.runLater(() -> HelloApplication.controller.setChannelName(finalChannelNum, finalText, finalSet));
                switch (finalChannelNum) {
                    case 0:
                        channelNames[0] = text;
                        if (set == 0)
                            ChannelAccessor.getDefault().setName(channel, text);
                        break;
                    case 1:
                        channelNames[1] = text;
                        if (set == 0)
                            ChannelAccessor.getDefault().setName(channel, text);
                        break;
                    case 2:
                        channelNames[2] = text;
                        if (set == 0)
                            ChannelAccessor.getDefault().setName(channel, text);
                        break;
                    case 3:
                        channelNames[3] = text;
                        if (set == 0)
                            ChannelAccessor.getDefault().setName(channel, text);
                        break;
                    case -128:
                        channelNames[4] = text;
                        if (set == 1)
//                            manager.getChannel(0).setName(text);
                            ChannelAccessor.getDefault().setName(manager.getChannel(0), text);
                        break;
                    case -127:
                        channelNames[5] = text;
                        if (set == 1)
//                            manager.getChannel(1).setName(text);
                        ChannelAccessor.getDefault().setName(manager.getChannel(1), text);
                        break;
                    case -126:
                        channelNames[6] = text;
                        if (set == 1)
//                            manager.getChannel(2).setName(text);
                        ChannelAccessor.getDefault().setName(manager.getChannel(2), text);
                        break;
                    case -125:
                        channelNames[7] = text;
                        if (set == 1)
//                            manager.getChannel(3).setName(text);
                        ChannelAccessor.getDefault().setName(manager.getChannel(3), text);
                        break;
                }
            }
//            System.out.print(Integer.toHexString(Byte.toUnsignedInt((byte) (data[i] & 0xFF))) + ", ");
            System.out.print(Integer.toHexString(Byte.toUnsignedInt(data[i])) + ", ");
        }
        System.out.println("\n");
//        System.out.println(data[0]);
        System.err.println("didn't work?");
    }
}
