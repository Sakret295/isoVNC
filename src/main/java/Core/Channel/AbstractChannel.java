package Core.Channel;

import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MicStatus;
import Core.Channel.Enums.MuteStatus;
import Core.AbstractSipManager;

public abstract class AbstractChannel {
   /* subscribers will be notified on info display changes */
    public abstract void subscribeToEvents(ChannelEventSubscriber subscriber);

   /* virtual panel controls */
    public abstract void adjustVolumeUp();
    public abstract void adjustVolumeDown();
    public abstract void sendMuteSignal();
    public abstract void sendMicSignal();
//    public abstract void adjustVolume(int level);

   /* getters for virtual panel info displays */
    public abstract int getVolume();
    public abstract int getMaxVolume();
    public abstract String getName(); /* returns null if the channel app has not yet connected */
    public abstract MicStatus getMicStatus();
    public abstract MuteStatus getMuteStatus();

    protected int index;
    protected String name;
    protected AbstractSipManager parent;

    public AbstractChannel(AbstractSipManager parent, int index, String initialName){
        this.parent = parent;
        this.index = index;
        this.name = initialName;
    }


   /* internal setters for updating info display elements and sending out events to subscribers
    accessible only through AbstractChannelAccessor*/
    static {
        AbstractChannelAccessor.setDefault(new ChannelAccessor());
    }
    protected abstract void setName(String name);
    protected abstract void setVolume(int level);
    protected abstract void setActiveStatus(ActiveStatus status); //TODO: more detailed documentation for this method
    protected abstract void setMicStatus(MicStatus status);
//    abstract void setMuteStatus(MuteStatus status);
}
