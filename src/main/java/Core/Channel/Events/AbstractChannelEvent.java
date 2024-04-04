package Core.Channel.Events;

abstract public class AbstractChannelEvent<T> {
    protected String message;
    protected T oldValue;
    protected T newValue;

    public AbstractChannelEvent(String message, T oldValue, T newValue){
        this.message = message;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    public abstract ChannelEventType getType();
    public abstract String getMessage();
    public abstract T getOldValue();
    public abstract T getNewValue();
}
