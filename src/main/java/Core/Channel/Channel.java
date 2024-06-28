package Core.Channel;

import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MicStatus;
import Core.Channel.Enums.MuteStatus;
import Core.AbstractSipManager;
import Core.Channel.Events.*;
import SipInterface.SipChannelInterface;

import java.util.ArrayList;

public class Channel extends AbstractChannel{
    private static final int CHANNEL_MAX_VOLUME = 8;
    private ArrayList<ChannelEventSubscriber> subscribers;
    private String channelName;
    private int channelVolume;
    private ActiveStatus activeStatus;
    private MicStatus micStatus;
    private MuteStatus muteStatus;
    private final SipChannelInterface instance;

    public Channel(AbstractSipManager parent, int panelIndex, String initialName, SipChannelInterface sipImplementation) {
        super(parent, panelIndex, initialName);
        subscribers = new ArrayList<>();
        instance = sipImplementation;
    }

    @Override
    public void subscribeToEvents(ChannelEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribeFromEvents(ChannelEventSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void adjustVolumeUp() throws NotOnScreenException{
        if(onScreen)
            instance.channelAdjustVolumeUp(panelIndex);
        else
            throw new NotOnScreenException(panelIndex);
    }

    @Override
    public void adjustVolumeDown() throws NotOnScreenException{
        if(onScreen)
            instance.channelAdjustVolumeDown(panelIndex);
        else
            throw new NotOnScreenException(panelIndex);
    }

    @Override
    public void sendMuteSignal() throws NotOnScreenException {
        if(onScreen)
            instance.channelSendMuteChannel(panelIndex);
        else
            throw new NotOnScreenException(panelIndex);
    }

    @Override
    public void sendMicSignal() throws NotOnScreenException{
        //TODO: implement mic
    }

    @Override
    public int getVolume() {
        return channelVolume;
    }

    @Override
    public int getMaxVolume() {
        return CHANNEL_MAX_VOLUME;
    }

    @Override
    public String getName() {
        return channelName;
    }

    @Override
    public MicStatus getMicStatus() {
        return micStatus;
    }

    @Override
    public MuteStatus getMuteStatus() {
        if (channelVolume <= 0){
            return MuteStatus.MUTED;
        }
        else
            return MuteStatus.UNMUTED;
    }

    @Override
    public boolean getOnScreen() {
        return onScreen;
    }

    @Override
    protected void setName(String name) {
        String oldName = channelName;
        channelName = name;
        for (ChannelEventSubscriber subscriber:
             subscribers) {
            subscriber.update(new NameChangeEvent(null, oldName, name));
        }
    }

    @Override
    protected void setVolume(int level) {
        int oldVolume = channelVolume;
        channelVolume = level;
        for (ChannelEventSubscriber subscriber:
                subscribers) {
            subscriber.update(new VolumeChangeEvent(null, oldVolume, level));
        }
    }

    @Override
    protected void setActiveStatus(ActiveStatus status) {
        ActiveStatus oldStatus = activeStatus;
        activeStatus = status;
        for (ChannelEventSubscriber subscriber:
                subscribers) {
            subscriber.update(new ActiveChangeEvent(null, oldStatus, status));
        }
    }

    @Override
    protected void setMuteStatus(MuteStatus status) {
        MuteStatus oldMuteStatus = muteStatus;
        muteStatus = status;
        for (ChannelEventSubscriber subscriber:
                subscribers) {
            subscriber.update(new MuteChangeEvent(null, oldMuteStatus, status));
        }
    }

    @Override
    protected void setMicStatus(MicStatus status) {
        MicStatus oldStatus = micStatus;
        micStatus = status;
        for (ChannelEventSubscriber subscriber:
                subscribers) {
            subscriber.update(new MicChangeEvent(null, oldStatus, status));
        }
    }

    @Override
    protected void setOnScreen(boolean value) {
        boolean oldOnScreen = onScreen;
        this.onScreen = value;
        for (ChannelEventSubscriber subscriber:
                subscribers) {
            subscriber.update(new OnScreenChangeEvent(channelName, oldOnScreen, value));
        }
    }

//    @Override
//    void setMuteStatus(MuteStatus status) {
//        if (status == MuteStatus.MUTED)
//            channelVolume = 0;
//        subscriber.update(new MuteChangeEvent(null, oldStatus, status));
//    }
}
