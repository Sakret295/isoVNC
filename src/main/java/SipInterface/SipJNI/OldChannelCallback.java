package SipInterface.SipJNI;

import Core.AbstractSipManager;
import Core.Channel.AbstractChannel;
import Core.Channel.AbstractChannelCallback;
import Core.Channel.Channel;
import Core.Channel.ChannelAccessor;
import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MuteStatus;
import Core.SipManager;
import SipInterface.PjSwigInterface.PanelCore;
import javafx.application.Platform;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OldChannelCallback extends AbstractChannelCallback {
    private boolean isInitialSet = true;
    private boolean isInitialSetGotten = false;
    private int initialSet;

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
        int set;
        int volume = -1;
        if(data.length == 1 && unsign(data[0]) == 0x01){
            byte[] bytes = {
                    (byte) 0xf1, 0x05, 0x01, 0x50, (byte) 0xc4, 0x69, (byte) 0xf2
            };


            PanelCore.getInstance().sendInfo(bytes, bytes.length);


            //TODO:REMOVE PRINT
            System.out.println("\n first response bytes sending out: ");
            for (byte datum : bytes) {
                System.out.print(Integer.toHexString(Byte.toUnsignedInt(datum)) + ", ");
            }
            System.out.println("\n");
            //remove print
        } else
            if(data.length == 7
                    && unsign(data[0]) == 0xf1
                    && unsign(data[1]) == 0x05
                    && unsign(data[2]) == 0x01
                    && unsign(data[3]) == 0x7f
                    && unsign(data[4]) == 0x49
                    && unsign(data[5]) == 0xbc
                    && unsign(data[6]) == 0xf2
            ){
                byte[] bytes = {
                        (byte) 0xf1, 0x05, 0x01, 0x10, 0x00, 0x21, (byte) 0xf2
                };

                PanelCore.getInstance().sendInfo(bytes, bytes.length);


                //TODO:REMOVE PRINT
                System.out.println("\n second response bytes sending out: ");
                for (byte datum : bytes) {
                    System.out.print(Integer.toHexString(Byte.toUnsignedInt(datum)) + ", ");
                }
                System.out.println("\n");
                //remove print


                byte[] bytes2 = {
                        (byte) 0xf1, 0x12, 0x01, 0x60, 0x01, 0x02, 0x0c, 0x00,
                        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, (byte) 0xcc, (byte) 0xa8, (byte) 0xf2
                };

                PanelCore.getInstance().sendInfo(bytes2, bytes2.length);
            }
//        /*
        for (int i = 0; i < data.length; i++) {
            if (unsign(data[i]) == 0xF1 && unsign(data[i + 1]) == 0x09 && unsign(data[i + 2]) == 0x01 && unsign(data[i + 3]) == 0x03){
                if (unsign(data[i + 4]) == 0x7f) {
                    if (unsign(data[i + 6]) == 0x01 && unsign(data[i + 7]) == 0x01) {
                        set = 1;
                        System.err.println("channel_set: " + set);
                    } else {
                        set = 0;
                        System.err.println("channel_set: " + set);
                    }
                    if(isInitialSet) {
                        initialSet = set;
                        isInitialSetGotten = true;
                    }
                    else {
                        manager.setCurrentChannelSet(set);
                        System.err.println("setCurrentChannelSet + " + set);
                    }
                }

                if (unsign(data[i + 4]) >= 0x00 && unsign(data[i+4]) <= 0x03){
                    volume = Integer.numberOfTrailingZeros(unsign(data[i + 7]));
                    channelNum = unsign(data[i+4]);
                    System.out.println("volume = " + volume + " channel = " + unsign(data[i+4]));

                    int finalChannelNum1 = channelNum;
                    int finalVolume = volume;
                    int color = 0;
                    try {
                        AbstractChannel channel = manager.getOnScreenChannel(finalChannelNum1);
                        if (unsign(data[i + 6]) == 0xFF) {
                            color = 1;
                            ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.ACTIVE);
                            ChannelAccessor.getDefault().setVolume(channel, finalVolume);
                            ChannelAccessor.getDefault().setMuteStatus(channel, MuteStatus.UNMUTED);
                        } else if (unsign(data[i + 6]) == 0x00) {
                            color = -1;
                            ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.INACTIVE);
                            ChannelAccessor.getDefault().setVolume(channel, finalVolume);
                            ChannelAccessor.getDefault().setMuteStatus(channel, MuteStatus.MUTED);
                        } else if (unsign(data[i + 6]) == 0xFE) {
                            color = 2;
                            ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.ACTIVE);
                            ChannelAccessor.getDefault().setVolume(channel, finalVolume);
                            ChannelAccessor.getDefault().setMuteStatus(channel, MuteStatus.MUTED);
                        } else {
                            ChannelAccessor.getDefault().setActiveStatus(channel, ActiveStatus.INACTIVE);
                            ChannelAccessor.getDefault().setVolume(channel, finalVolume);
                            ChannelAccessor.getDefault().setMuteStatus(channel, MuteStatus.UNMUTED);
                        }
                        int finalColor = color;
//                    Platform.runLater(() -> HelloApplication.controller.setChannelVolume(finalChannelNum1, finalVolume, finalColor));
//                    ChannelAccessor.getDefault().setVolume(channel, finalVolume);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            if (unsign(data[i]) == 0x0F && unsign(data[i + 1]) == 0x01) {
//                System.out.println("found hex: " + data[i] + "; " + data[i + 1] + ";");
                text = new String(data, i + 5, 7, StandardCharsets.US_ASCII);
//                System.out.println("ttext = " + text + "; length: " + text.length());
                channelNum = unsign(data[i + 3]);
//                System.out.println("channelNum = " + channelNum);
                int finalChannelNum = channelNum;
                String finalText = text;
//                Platform.runLater(() -> HelloApplication.controller.setChannelName(finalChannelNum, finalText, finalSet));
                try {
                    switch (finalChannelNum) {
                        case 0:
                            channelNames[0] = text;
//                            if (set == 0)
                                ChannelAccessor.getDefault().setName(manager.getChannel(0), text);
                            break;
                        case 1:
                            channelNames[1] = text;
//                            if (set == 0)
                                ChannelAccessor.getDefault().setName(manager.getChannel(1), text);
                            break;
                        case 2:
                            channelNames[2] = text;
//                            if (set == 0)
                                ChannelAccessor.getDefault().setName(manager.getChannel(2), text);
                            break;
                        case 3:
                            channelNames[3] = text;
//                            if (set == 0)
                                ChannelAccessor.getDefault().setName(manager.getChannel(3), text);
                            break;
                        case 128:
                            channelNames[4] = text;
//                            if (set == 1)
//                            manager.getChannel(0).setName(text);
                                ChannelAccessor.getDefault().setName(manager.getChannel(4), text);
                            break;
                        case 129:
                            channelNames[5] = text;
//                            if (set == 1)
//                            manager.getChannel(1).setName(text);
                                ChannelAccessor.getDefault().setName(manager.getChannel(5), text);
                            break;
                        case 130:
                            channelNames[6] = text;
//                            if (set == 1)
//                            manager.getChannel(2).setName(text);
                                ChannelAccessor.getDefault().setName(manager.getChannel(6), text);
                            break;
                        case 131:
                            channelNames[7] = text;
//                            if (set == 1)
//                            manager.getChannel(3).setName(text);
                                ChannelAccessor.getDefault().setName(manager.getChannel(7), text);
                            break;
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
//            System.out.print(Integer.toHexString(Byte.toUnsignedInt((byte) (data[i] & 0xFF))) + ", ");
            System.out.print(Integer.toHexString(Byte.toUnsignedInt(data[i])) + ", ");
        }

        if (isInitialSetGotten && isInitialSet){
            isInitialSet = false;
            manager.setCurrentChannelSet(initialSet);
            System.err.println("setCurrentChannelSet + " + initialSet);
        }

//         */
        System.out.println("\n");
//        System.out.println(data[0]);
        System.err.println("didn't work?");
    }
}
