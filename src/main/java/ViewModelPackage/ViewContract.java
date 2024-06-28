package ViewModelPackage;

public interface ViewContract {
    void showChannelName(int channel, String name);
    void showChannelActive(int channel);
    void showChannelInactive(int channel);
    void showChannelVolume(int channel, int level);
    void showChannelMute(int channel, boolean muted);
}
