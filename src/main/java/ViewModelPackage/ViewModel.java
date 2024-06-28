package ViewModelPackage;

import Core.Channel.AbstractChannel;
import Core.Channel.ChannelEventSubscriber;
import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MuteStatus;
import Core.Channel.Events.AbstractChannelEvent;
import Core.Channel.Events.ChannelEventType;
import Core.Channel.NotOnScreenException;
import Core.SipManager;
import javafx.application.Platform;

import java.util.ArrayList;

public class ViewModel {
    private ViewContract viewContract;
    private SipManager manager;
    private ArrayList<ChannelEventSubscriber> subscribers;

    public void stop() {
        manager.stop();
    }

    private class ChannelSubscriberImp implements ChannelEventSubscriber{
        private int channelNum;
        public ChannelSubscriberImp(int channelNum){
            this.channelNum = channelNum;
        }
        @Override
        public void update(AbstractChannelEvent event) {
            if (event.getType() == ChannelEventType.VOLUME_CHANGE){
                Platform.runLater(() -> {
                    viewContract.showChannelVolume(channelNum, (Integer) event.getNewValue());
                } );
            }

            if (event.getType() == ChannelEventType.ACTIVE_CHANGE){
                if (event.getNewValue() == ActiveStatus.ACTIVE) {
                    Platform.runLater(() -> {
                        viewContract.showChannelActive(channelNum);
                    });
                }
                else{
                        Platform.runLater(() -> {
                            viewContract.showChannelInactive(channelNum);
                        });
                    }
            }

//            if (event.getType() == ChannelEventType.NAME_CHANGE) {
//                Platform.runLater(() -> {
//                    System.err.println("name_change + " + channelNum);
//                    viewContract.showChannelName(channelNum, (String) event.getNewValue());
//                });
//            }

            if (event.getType() == ChannelEventType.MUTE_CHANGE){
                if (event.getNewValue() == MuteStatus.MUTED) {
                    Platform.runLater(() -> {
                        viewContract.showChannelMute(channelNum, true);
                    });
                }
                else {
                        Platform.runLater(() -> {
                            viewContract.showChannelMute(channelNum, false);
                        });
                     }
            }

            if (event.getType() == ChannelEventType.ON_SCREEN_CHANGE){
                if ((boolean) event.getNewValue()) {
                    Platform.runLater(() -> {
                        System.err.println("on_screen_change + " + channelNum + " message: " + event.getMessage());
                        viewContract.showChannelName(channelNum, event.getMessage());
                    });
                }
            }

            if (event.getType() == ChannelEventType.MIC_CHANGE){
                //TODO: implement mic
                System.err.println("not implemented");
            }
        }
    }

    public void muteChannel(int channel){
        try {
            manager.getOnScreenChannel(channel).sendMuteSignal();
        } catch (NotOnScreenException e) {
            e.printStackTrace();
        }
    }

    public void raiseVolume(int channel){
        try {
            manager.getOnScreenChannel(channel).adjustVolumeUp();
        } catch (NotOnScreenException e) {
            e.printStackTrace();
        }
    }

    public void lowerVolume(int channel){
        try {
            manager.getOnScreenChannel(channel).adjustVolumeDown();
        } catch (NotOnScreenException e) {
            e.printStackTrace();
        }
    }

    public void switchMic(int channel){
        //TODO: implement mic
        System.err.println("not implemented");
    }

    public void setCommonVolume(int value){
        manager.setMainVolume(value);
    }

    public void shiftChannels() {
        manager.shiftChannels();
    }

    public ViewModel(ViewContract viewContract){
        this.viewContract = viewContract;
        manager = new SipManager();
        subscribers = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            subscribers.add(new ChannelSubscriberImp(i));
        }
        ArrayList<AbstractChannel> channels = manager.getChannels();
        for (int i = 0; i < 8; i++){
            if (i < 4)
                channels.get(i).subscribeToEvents(subscribers.get(i));
            else channels.get(i).subscribeToEvents(subscribers.get(i - 4));
        }

        manager.start();
//        manager.setMainVolume(100);
//        manager.registerThisThread();
    }

}
