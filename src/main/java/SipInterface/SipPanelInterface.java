package SipInterface;

public interface SipPanelInterface {
    void panelStart(AppConfig appConfig);
    void panelSendShift();
    void panelSendEndCall();
    void panelSetVolume(int volume); // in percent
    boolean panelRegisterThisThread();
}
