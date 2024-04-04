package Core;

public interface SipChannelInterface {
    void channelAdjustVolumeUp(int channel);
    void channelAdjustVolumeDown(int channel);
    void channelSendMuteChannel(int channel);
    void channelSendMicSignal();
}
