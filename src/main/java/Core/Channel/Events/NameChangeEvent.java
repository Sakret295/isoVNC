package Core.Channel.Events;

public class NameChangeEvent extends AbstractChannelEvent<String> {
    public NameChangeEvent(String message, String oldValue, String newValue) {
        super(message, oldValue, newValue);
    }

    @Override
    public ChannelEventType getType() {
        return ChannelEventType.NAME_CHANGE;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getOldValue() {
        return oldValue;
    }

    @Override
    public String getNewValue() {
        return newValue;
    }
}
