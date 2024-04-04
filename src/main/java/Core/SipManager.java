package Core;

import Core.Channel.AbstractChannel;
import Core.Channel.Channel;
import SipInterface.SipJNI.sipJNI;

import java.util.ArrayList;

public class SipManager extends AbstractSipManager{
    private static final int CHANNEL_CAPACITY_COUNT = 4;

    private final SipPanelInterface instance;
    private final String sipName;
    private final ArrayList<AbstractChannel> channels;
//    private ArrayList<String> channelNamesSet1;
//    private ArrayList<String> channelNamesSet2;

    private final Thread sipThread;

    public SipManager(String sipName){
        instance = sipJNI.getInstance(); //TODO: swap for a new interface
//        CallbackClass.setImplementationClass(new OldChannelCallback(this));

        //fill in the config variables
        this.sipName = sipName;
        channels = new ArrayList<>();

        //create channels with null values
        for (int i = 0; i < CHANNEL_CAPACITY_COUNT; i++) {
            channels.add(new Channel(this, i, null));
        }

        sipThread = new Thread(instance::panelStart);
    }

    @Override
    public void start() {
        //call sipJNI main in a separate thread
        sipThread.start(); //TODO: implement sip name

        //register javaFX thread
        instance.panelRegisterThread();

        //TODO: setup ability to interrupt the process
    }

    @Override
    public void stop() {
        //end the SIP call with bye message
        instance.panelSendEndCall();

        //stop the execution of the native library
        sipThread.interrupt();

        //TODO: save config options, preferences, etc
    }

    @Override
    public ArrayList<AbstractChannel> getChannels() {
        return channels;
    }

    @Override
    public AbstractChannel getChannel(int index){
        return channels.get(index);
    }

    @Override
    public int getChannelCount() {
        return channels.size();
    }

    @Override
    public void shiftChannels() {
        instance.panelSendShift();

    }

    @Override
    public void setMainVolume() {
        //TODO: implement main volume
    }

    @Override
    public void getMainVolume() {
        //not implemented
    }

}
