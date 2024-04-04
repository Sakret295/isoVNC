package Core.Channel;

import Core.Channel.Enums.ActiveStatus;
import Core.Channel.Enums.MicStatus;

public abstract class AbstractChannelAccessor {
    private static volatile AbstractChannelAccessor defaultAccessor;
    public static AbstractChannelAccessor getDefault(){
       AbstractChannelAccessor a = defaultAccessor;
       if (a == null){
           throw new IllegalStateException("Channel Accessor was not set. Call setDefault(AbstractChannelAccessor) in the channel class.");
       }

       return a;
    }

    public static void setDefault(AbstractChannelAccessor accessor){
        if (defaultAccessor != null){
            throw new IllegalStateException("Channel Accessor already set. Multiple calls of setDefault(AbstractChannelAccessor) are prohibited.");
        }

        defaultAccessor = accessor;
    }

    public AbstractChannelAccessor(){}


    /* channel methods the accessor provides access for */
    public abstract void setName(AbstractChannel channel, String name);
    public abstract void setVolume(AbstractChannel channel, int level);
    public abstract void setActiveStatus(AbstractChannel channel, ActiveStatus status);
    public abstract void setMicStatus(AbstractChannel channel, MicStatus status);
}
