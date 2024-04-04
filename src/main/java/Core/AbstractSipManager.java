package Core;

import Core.Channel.AbstractChannel;

import java.util.ArrayList;

//manages channels
public abstract class AbstractSipManager {
    public abstract void start();
    public abstract void stop();

    public abstract ArrayList<AbstractChannel> getChannels();
    public abstract AbstractChannel getChannel(int index);
    public abstract int getChannelCount();

    public abstract void shiftChannels();
    public abstract void setMainVolume();
    public abstract void getMainVolume();
}
