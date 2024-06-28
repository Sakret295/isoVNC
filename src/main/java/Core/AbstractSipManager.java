package Core;

import Core.Channel.AbstractChannel;

import java.util.ArrayList;

//manages channels
public abstract class AbstractSipManager {
    public abstract void start();
    public abstract void stop();

    public abstract ArrayList<AbstractChannel> getChannels();
    public abstract AbstractChannel getChannel(int index) throws ArrayIndexOutOfBoundsException;
    public abstract AbstractChannel getOnScreenChannel(int panelIndex) throws ArrayIndexOutOfBoundsException;
    public abstract int getChannelCount();

    public abstract void shiftChannels();
    public abstract void setMainVolume(int volume);

    public abstract int getMainVolume();

    public abstract void setCurrentChannelSet(int i);

    public abstract boolean registerThisThread();
}
