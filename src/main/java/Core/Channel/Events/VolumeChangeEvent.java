package Core.Channel.Events;

public class VolumeChangeEvent extends AbstractChannelEvent<Integer> {
    public VolumeChangeEvent(String message, Integer oldValue, Integer newValue) {
        super(message, oldValue, newValue);
    }

    @Override
    public ChannelEventType getType() {
        return ChannelEventType.VOLUME_CHANGE;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getOldValue() {
        return oldValue;
    }

    @Override
    public Integer getNewValue() {
        return newValue;
    }
}
