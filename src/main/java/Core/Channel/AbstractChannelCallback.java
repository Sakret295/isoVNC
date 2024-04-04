package Core.Channel;

import Core.AbstractSipManager;

abstract public class AbstractChannelCallback {
    protected AbstractSipManager manager;

    public AbstractChannelCallback(AbstractSipManager manager){
        this.manager = manager;
    }

    public abstract void processCallback(byte[] data, int length);
}
