package Core.Channel.Events;

public class OnScreenChangeEvent extends AbstractChannelEvent<Boolean> {
    public OnScreenChangeEvent(String message, boolean oldValue, boolean newValue) {
        super(message, oldValue, newValue);
    }

    @Override
    public ChannelEventType getType() {
        return ChannelEventType.ON_SCREEN_CHANGE;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Boolean getOldValue() {
        return oldValue;
    }

    @Override
    public Boolean getNewValue() {
        return newValue;
    }
}
