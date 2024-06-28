package Core.Channel;

public class NotOnScreenException extends Exception {
    public NotOnScreenException(int channel){
        super("This channel with panel index " + channel + "wasn't currently on screen of the virtual panel");
    }
}
