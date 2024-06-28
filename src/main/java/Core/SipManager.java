package Core;

import Core.Channel.AbstractChannel;
import Core.Channel.Channel;
import Core.Channel.ChannelAccessor;
import SipInterface.AbstractConfigLoader;
import SipInterface.AppConfig;
import SipInterface.PjSwigInterface.PanelCore;
import SipInterface.SipJNI.CallbackClass;
import SipInterface.SipJNI.OldChannelCallback;
import SipInterface.SipPanelInterface;

import java.util.ArrayList;

public class SipManager extends AbstractSipManager{
    private static final int CHANNEL_CAPACITY_COUNT = 8;
    private static final int MAXIMUM_ON_SCREEN_CHANNELS = 4;
    private static final int CHANNEL_SETS_COUNT = 2;

    private final SipPanelInterface instance;
    private final AbstractConfigLoader configLoader;
    private final AppConfig appConfig;
    private final ArrayList<AbstractChannel> channels;
    private int volumeLevel = 5;
    private int currentChannelSet = 0;
//    private ArrayList<String> channelNamesSet1;
//    private ArrayList<String> channelNamesSet2;

    private final Thread sipThread;

    public SipManager(){
        instance = PanelCore.getInstance(); //TODO: swap for a new interface
        configLoader = new AbstractConfigLoader() {
            @Override
            public AppConfig getConfig() {
                return new AppConfig("506222", 5060, 1, "Riedel Artist", "192.168.11.89");
            }
        };
        appConfig = configLoader.getConfig();
        CallbackClass.setImplementationClass(new OldChannelCallback(this));

        channels = new ArrayList<>();

        //create channels with null values
        for (int i = 0; i < CHANNEL_SETS_COUNT; i++) {
            for (int j = 0; j < MAXIMUM_ON_SCREEN_CHANNELS; j++) {
                channels.add(new Channel(this, j, null, PanelCore.getInstance()));
            }
        }

        sipThread = new Thread(() -> instance.panelStart(appConfig));
    }

    @Override
    public void start() {
        //call sipJNI main in a separate thread
        sipThread.start();

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
    public AbstractChannel getChannel(int index) throws ArrayIndexOutOfBoundsException{
        if (index > getChannelCount() || index < 0) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("getChannel called with index: " + index);
            throw e;
        }
        return channels.get(index);
    }

    @Override
    public AbstractChannel getOnScreenChannel(int panelIndex) throws ArrayIndexOutOfBoundsException {
        if (panelIndex > MAXIMUM_ON_SCREEN_CHANNELS || panelIndex < 0) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("getOnScreenChannel called with panel index: " + panelIndex);
            throw e;
        }
        System.out.println("panelIndex = " + panelIndex);
        System.out.println("MAXIMUM_ON_SCREEN_CHANNELS = " + MAXIMUM_ON_SCREEN_CHANNELS);
        System.out.println("currentChannelSet = " + currentChannelSet);
        int calculatedIndex = panelIndex + MAXIMUM_ON_SCREEN_CHANNELS * currentChannelSet;
        return getChannel(calculatedIndex);
    }

    @Override
    public int getChannelCount() {
        return channels.size();
    }

    @Override
    public void shiftChannels() {
        instance.panelSendShift();
        if(currentChannelSet == 0)
            setCurrentChannelSet(1);
        else if (currentChannelSet == 1)
            setCurrentChannelSet(0);
    }

    @Override
    public void setMainVolume(int volume) {
        if (volume >=0 && volume <= 100) {
            instance.panelSetVolume(volume);
            volumeLevel = volume;
        }
        //TODO: implement stored in preferences
    }

    @Override
    public int getMainVolume() {
        //not implemented
        return volumeLevel;
    }

    @Override
    public void setCurrentChannelSet(int set) {
        this.currentChannelSet = set;
        int setStartIndex = set * MAXIMUM_ON_SCREEN_CHANNELS;
        int setEndIndex = setStartIndex + MAXIMUM_ON_SCREEN_CHANNELS;
        for (int i = 0; i < CHANNEL_CAPACITY_COUNT; i++){
            if(i >= setStartIndex && i < setEndIndex) {
                ChannelAccessor.getDefault().setOnScreen(getChannel(i), true);
                System.out.println("setOnScreen index: " + i);
            }
            else {
                ChannelAccessor.getDefault().setOnScreen(getChannel(i), false);
            }
        }
    }

    @Override
    public boolean registerThisThread() {
        return instance.panelRegisterThisThread();
    }

}
