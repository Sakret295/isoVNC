package Core.Channel;

import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MicStatus;
import Core.Channel.Enums.MuteStatus;
import Core.AbstractSipManager;
import Core.Channel.Events.ActiveChangeEvent;
import Core.Channel.Events.MicChangeEvent;
import Core.Channel.Events.NameChangeEvent;
import Core.Channel.Events.VolumeChangeEvent;
import Core.SipChannelInterface;
import SipInterface.SipJNI.sipJNI;

public class Channel extends AbstractChannel{
    private static final int CHANNEL_MAX_VOLUME = 8;
    private ChannelEventSubscriber subscriber;
    private String channelName;
    private int channelVolume;
    private ActiveStatus activeStatus;
    private MicStatus micStatus;
    private final SipChannelInterface instance;

    public Channel(AbstractSipManager parent, int index, String initialName) {
        super(parent, index, initialName);
        instance = sipJNI.getInstance();
    }

    @Override
    public void subscribeToEvents(ChannelEventSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void adjustVolumeUp() {
        instance.channelAdjustVolumeUp(index);
    }

    @Override
    public void adjustVolumeDown() {
        instance.channelAdjustVolumeDown(index);
    }

    @Override
    public void sendMuteSignal() {
        instance.channelSendMuteChannel(index);
    }

    @Override
    public void sendMicSignal() {
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
    protected void setName(String name) {
        String oldName = channelName;
        channelName = name;
        subscriber.update(new NameChangeEvent(null, oldName, name));
    }

    @Override
    protected void setVolume(int level) {
        int oldVolume = channelVolume;
        channelVolume = level;
        subscriber.update(new VolumeChangeEvent(null, oldVolume, level));
    }

    @Override
    protected void setActiveStatus(ActiveStatus status) {
        ActiveStatus oldStatus = activeStatus;
        activeStatus = status;
        subscriber.update(new ActiveChangeEvent(null, oldStatus, status));
    }

    @Override
    protected void setMicStatus(MicStatus status) {
        MicStatus oldStatus = micStatus;
        micStatus = status;
        subscriber.update(new MicChangeEvent(null, oldStatus, status));
    }

//    @Override
//    void setMuteStatus(MuteStatus status) {
//        if (status == MuteStatus.MUTED)
//            channelVolume = 0;
//        subscriber.update(new MuteChangeEvent(null, oldStatus, status));
//    }
}
