package SipInterface.SipJNI;//import com.example.demovcp.HelloApplication;

import Core.SipChannelInterface;
import Core.SipPanelInterface;

public class sipJNI implements SipChannelInterface, SipPanelInterface {
    static {
        System.loadLibrary("sipNative");
    }

    private static sipJNI instance;
    private SipInterface.SipJNI.CallbackClass CallbackClass;
//    private HelloApplication fxClass;

//    public SipInterface.SipJNI.sipJNI(HelloApplication fxClass) {
//        this.fxClass = fxClass;
//    }

//    public void start(){
//        instance.start(instance.CallbackClass);
//    }
    public static sipJNI getInstance(){
        if (instance == null) {
            instance = new sipJNI();
            instance.CallbackClass = new CallbackClass();
        }
        return instance;
    }

//    public static int registerThisThread(){
//        if (instance != null) {
//            instance.registerThread();
//            return 0;
//        }
//        else
//            return 1;
//    }

//    public static void endCall(){
//        instance.sendBye();
//    }

//    public static void muteChannel(int channel){
//        instance.mute(channel);
//    }
//
//    public static void shiftChannels(){
//        instance.shift();
//    }
//
//    public static void adjustVolumeUp(int channel){
//        instance.sendVolumeUp(channel);
//    }
//
//    public static void adjustVolumeDown(int channel){
//        instance.sendVolumeDown(channel);
//    }

    private native void start(CallbackClass instance);

    private native void registerThread();

    private native void sendBye();

    private native void mute(int channel);

    private native void shift();

    private native void sendVolumeUp(int channel);

    private native void sendVolumeDown(int channel);

    @Override
    public void channelAdjustVolumeUp(int channel) {
        sendVolumeUp(channel);
    }

    @Override
    public void channelAdjustVolumeDown(int channel) {
        sendVolumeDown(channel);
    }

    @Override
    public void channelSendMuteChannel(int channel) {
        mute(channel);
    }

    @Override
    public void channelSendMicSignal() {
        //TODO: implement microphone
    }

    @Override
    public void panelStart() {
        start(this.CallbackClass);
    }

    @Override
    public void panelRegisterThread() {
        registerThread();
    }

    @Override
    public void panelSendShift() {
        shift();
    }

    @Override
    public void panelSendEndCall() {
        sendBye();
    }
}
