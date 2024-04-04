package Core.Channel.Events;

import Core.Channel.Enums.MicStatus;

public class MicChangeEvent extends AbstractChannelEvent<MicStatus> {
    public MicChangeEvent(String message, MicStatus oldValue, MicStatus newValue) {
        super(message, oldValue, newValue);
    }

    @Override
    public ChannelEventType getType() {
        return ChannelEventType.MIC_CHANGE;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public MicStatus getOldValue() {
        return null;
    }

    @Override
    public MicStatus getNewValue() {
        return null;
    }
}
