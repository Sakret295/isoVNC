package Core.Channel.Events;

import Core.Channel.Enums.ActiveStatus;

public class ActiveChangeEvent extends AbstractChannelEvent<ActiveStatus> {
    public ActiveChangeEvent(String message, ActiveStatus oldValue, ActiveStatus newValue) {
        super(message, oldValue, newValue);
    }

    @Override
    public ChannelEventType getType() {
        return ChannelEventType.ACTIVE_CHANGE;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ActiveStatus getOldValue() {
        return oldValue;
    }

    @Override
    public ActiveStatus getNewValue() {
        return newValue;
    }
}
