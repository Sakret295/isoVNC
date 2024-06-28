package Core.Channel;

import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MicStatus;
import Core.Channel.Enums.MuteStatus;
import Core.AbstractSipManager;

public abstract class AbstractChannel {
   /* subscribers will be notified on info display changes */
    public abstract void subscribeToEvents(ChannelEventSubscriber subscriber);
    public abstract void unsubscribeFromEvents(ChannelEventSubscriber subscriber);

   /* virtual panel controls */
    public abstract void adjustVolumeUp() throws NotOnScreenException;
    public abstract void adjustVolumeDown() throws NotOnScreenException;
    public abstract void sendMuteSignal() throws NotOnScreenException;
    public abstract void sendMicSignal() throws NotOnScreenException;
//    public abstract void adjustVolume(int level);

   /* getters for virtual panel info displays */
    public abstract int getVolume();
    public abstract int getMaxVolume();
    public abstract String getName(); /* returns null if the channel app has not yet connected */
    public abstract MicStatus getMicStatus();
    public abstract MuteStatus getMuteStatus();
    public abstract boolean getOnScreen();

    protected int panelIndex; //index of the channel on the panel
    protected String name;
    protected AbstractSipManager parent;
    protected boolean onScreen = false;

    public AbstractChannel(AbstractSipManager parent, int panelIndex, String initialName){
        this.parent = parent;
        this.panelIndex = panelIndex;
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
    protected abstract void setMuteStatus(MuteStatus status);
    protected abstract void setMicStatus(MicStatus status);
    protected abstract void setOnScreen(boolean value);
}
