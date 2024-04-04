package Core.Channel;

import Core.Channel.Events.AbstractChannelEvent;

public interface ChannelEventSubscriber {
    void update(AbstractChannelEvent event);
}
