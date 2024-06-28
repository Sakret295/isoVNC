package Core.Channel.Events;

import Core.Channel.Enums.MuteStatus;

public class MuteChangeEvent extends AbstractChannelEvent<MuteStatus> {
    public MuteChangeEvent(String message, MuteStatus oldValue, MuteStatus newValue) {
        super(message, oldValue, newValue);
    }

    @Override
    public ChannelEventType getType() {
        return ChannelEventType.MUTE_CHANGE;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public MuteStatus getOldValue() {
        return oldValue;
    }

    @Override
    public MuteStatus getNewValue() {
        return newValue;
    }
}
