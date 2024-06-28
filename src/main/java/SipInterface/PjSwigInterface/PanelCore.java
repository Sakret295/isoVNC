package SipInterface.PjSwigInterface;

import SipInterface.AbstractSipImplementation;
import SipInterface.AppConfig;
import SipInterface.SipChannelInterface;
import SipInterface.SipJNI.CallbackClass;
import SipInterface.SipJNI.sipJNI;
import SipInterface.SipPanelInterface;
import org.pjsip.pjsua2.*;

public class PanelCore extends pjsua2 implements SipPanelInterface, SipChannelInterface{
    private static PanelCore instance;

    public static Endpoint endpoint = new Endpoint();
    private MediaManager mediaManager;
    private PanelAccount panelAccount;
    private AccountConfig accountConfig;
    private AppConfig appConfig;
    private PanelCall currentCall;
    private EpConfig epConfig;
    private TransportConfig transportConfig;
    private boolean del_call_scheduled;
    private LogWriter logWriter;
    private boolean registered;
//    private PanelEndpoint panelEndpoint;

    public static PanelCore getInstance() {
        if (instance == null) {
            instance = new PanelCore();
        }
        return instance;
    }

    protected void init() {
        epConfig = new EpConfig();
        transportConfig = new TransportConfig();
        /* Create endpoint */
        try {
            endpoint.libCreate();
        } catch (Exception e) {
            return;
        }

        transportConfig.setPort(appConfig.SIP_PORT);

        epConfig.getLogConfig().setLevel(appConfig.LOG_LEVEL);
        epConfig.getLogConfig().setConsoleLevel(appConfig.LOG_LEVEL);

        LogConfig log_cfg = epConfig.getLogConfig();
        logWriter = new LogWriter() {
            @Override
            public void write(LogEntry entry){
                System.out.println(entry.getMsg());
            }
        };
        log_cfg.setWriter(logWriter);
        log_cfg.setDecor(log_cfg.getDecor() &
                ~(pj_log_decoration.PJ_LOG_HAS_CR |
                        pj_log_decoration.PJ_LOG_HAS_NEWLINE));

        /* Write log to file (just uncomment whenever needed) */
        //String log_path = android.os.Environment.getExternalStorageDirectory().toString();
        //log_cfg.setFilename(log_path + "/pjsip.log");

        /* Set ua config. */
        UaConfig ua_cfg = epConfig.getUaConfig();
        ua_cfg.setUserAgent(appConfig.USER_AGENT_NAME);

        /* using Endpoint::libHandleEvents() so mainThreadOnly should be set to true */
        ua_cfg.setThreadCnt(0);
        ua_cfg.setMainThreadOnly(true);

        try {
            endpoint.libInit(epConfig);
        } catch (Exception e) {
            return;
        }

        /* Create transports. */
        try {
            endpoint.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_UDP,
                    transportConfig);
        } catch (Exception e) {
            System.out.println(e);
        }


        try {
            endpoint.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_TCP,
                    transportConfig);
        } catch (Exception e) {
            System.out.println(e);
        }

        mediaManager = new MediaManager(endpoint);

        /* Create account */
        accountConfig = new AccountConfig();
        accountConfig.setIdUri("sip:" + appConfig.SIP_NAME + "@" + appConfig.IP);
        panelAccount = new PanelAccount(this);
        try {
            panelAccount.create(accountConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Start. */
        try {
            endpoint.libStart();
        } catch (Exception e) {
            return;
        }

    }

    @Override
    public void panelStart(AppConfig appConfig) {
        this.appConfig = appConfig;
        //runWorker from siptest


        //init
        //set up account config

        //have to use Account.modify()

        //start loop

        try {
            init();
        } catch (Exception e) {
            System.out.println(e);
            deinit();
            System.exit(-1);
        }

        while (!Thread.currentThread().isInterrupted()) {
            // Handle events
            endpoint.libHandleEvents(10);

            // Check if any call instance need to be deleted
            check_call_deletion();

            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException ie) {
                break;
            }
        }
        deinit();
    }

    private void deinit(){
        //TODO: possible save configs and stuff to preferences here

        /* Try force GC to avoid late destroy of PJ objects as they should be
         * deleted before lib is destroyed.
         */
        Runtime.getRuntime().gc();

        /* Shutdown pjsua. Note that Endpoint destructor will also invoke
         * libDestroy(), so this will be a test of double libDestroy().
         */
        try {
            endpoint.libDestroy();
        } catch (Exception e) {}

        /* Force delete Endpoint here, to avoid deletion from a non-
         * registered thread (by GC?).
         */
        endpoint.delete();
        endpoint = null;
    }

    private void check_call_deletion()
    {
        if (del_call_scheduled && currentCall != null) {
            currentCall.delete();
            currentCall = null;
            del_call_scheduled = false;
        }
    }

    @Override
    public void panelSendShift() {
        //TODO: bytes shit stuff and sendRequest
        byte[] bytes = {
                (byte) 0xf1, 0x07, 0x01, 0x28, 0x02, 0x00, (byte) 0xb1, 0x3f,
                (byte) 0xf2
        };

        sendInfo(bytes, bytes.length);


        byte[] bytes2 = {
                (byte) 0xf1, 0x07, 0x01, 0x29, 0x02, 0x00, (byte) 0x81, 0x08,
                (byte) 0xf2
        };

        sendInfo(bytes2, bytes2.length);
    }

    @Override
    public void panelSendEndCall() {

    }

    @Override
    public void panelSetVolume(int volume) {
        mediaManager.setOutputVolume(volume);
    }

    @Override
    public boolean panelRegisterThisThread() {
        if (endpoint.libIsThreadRegistered())
            return true;
        else {
            try {
                endpoint.libRegisterThread("");
                return endpoint.libIsThreadRegistered();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public void channelAdjustVolumeUp(int channel) {
        //TODO: bytes shit stuff and sendRequest
        if (channel == 0) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x00, 0x00, 0x01, (byte) 0x99, (byte) 0x96,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
        else if (channel == 1) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x01, 0x00, 0x01, (byte) 0xa9, (byte) 0xa1,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
        else if (channel == 2) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x02, 0x00, 0x01, (byte) 0xf9, (byte) 0xf8,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
        else if (channel == 3) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x03, 0x00, 0x01, (byte) 0xc9, (byte) 0xcf,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
    }

    @Override
    public void channelAdjustVolumeDown(int channel) {
        //TODO: bytes shit stuff and sendRequest
        if (channel == 0) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x00, 0x00, (byte) 0xff, 0x48, (byte) 0x98,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
        else if (channel == 1) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x01, 0x00, (byte) 0xff, 0x78, (byte) 0xaf,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
        else if (channel == 2) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x02, 0x00, (byte) 0xff, 0x28, (byte) 0xf6,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
        else if (channel == 3) {
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x08, 0x01, 0x30, 0x03, 0x00, (byte) 0xff, 0x18, (byte) 0xc1,
                    (byte) 0xf2
            };
            sendInfo(packet_bytes, 10);
        }
    }

    @Override
    public void channelSendMuteChannel(int channel) {
        //TODO: bytes shit stuff and sendRequest
        if (channel == 0){
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x24, 0x00, 0x00, (byte) 0xb2, 0x2c,
                    (byte) 0xf2
            };

            sendInfo(packet_bytes, 9);

            byte packet_bytes2[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x25, 0x00, 0x00, (byte) 0x82, 0x1b,
                    (byte) 0xf2
            };

            sendInfo(packet_bytes2, 9);
        }
        else if (channel == 1){
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x24, 0x01, 0x00, (byte) 0x83, 0x1f,
                    (byte) 0xf2
            };


            sendInfo(packet_bytes, 9);

            byte packet_bytes2[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x25, 0x01, 0x00, (byte) 0xb3, 0x28,
                    (byte) 0xf2
            };

            sendInfo(packet_bytes2, 9);
        }
        else if (channel == 2){
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x24, 0x02, 0x00, (byte) 0xd0, 0x4a,
                    (byte) 0xf2
            };


            sendInfo(packet_bytes, 9);

            byte packet_bytes2[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x25, 0x02, 0x00, (byte) 0xe0, 0x7d,
                    (byte) 0xf2
            };

            sendInfo(packet_bytes2, 9);
        }
        else if (channel == 3){
            byte packet_bytes[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x24, 0x03, 0x00, (byte) 0xe1, 0x79,
                    (byte) 0xf2
            };


            sendInfo(packet_bytes, 9);

            byte packet_bytes2[] = {
                    (byte) 0xf1, 0x07, 0x01, 0x25, 0x03, 0x00, (byte) 0xd1, 0x4e,
                    (byte) 0xf2
            };

            sendInfo(packet_bytes2, 9);
        }
    }

    @Override
    public void channelSendMicSignal(int channel) {
        //TODO: implement mic
    }

    protected AppConfig getAppConfig(){
        return this.appConfig;
    }

    public void sendInfo(byte[] data, long length){
        try {
            currentCall.sendInfoRequest(data, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void processIncomingCall(PanelCall call) //called from account class
    {
        /* Auto answer. */
        CallOpParam call_param = new CallOpParam();
        SipHeader userAgentHeader = new SipHeader();
        userAgentHeader.setHName("User-Agent");
        userAgentHeader.setHValue(appConfig.USER_AGENT_NAME);
        call_param.getTxOption().getHeaders().add(userAgentHeader);
        try {
            currentCall = call;
            call_param.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
            currentCall.answer(call_param);
            call_param.setStatusCode(pjsip_status_code.PJSIP_SC_OK);
            currentCall.answer(call_param);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    protected void notifyCallState(PanelCall call) {
        if (currentCall == null || call.getId() != currentCall.getId())
            return;

        CallInfo ci;
        try {
            ci = call.getInfo();
        } catch (Exception e) {
            ci = null;
        }
        if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
            // Should not delete call instance in this context,
            // so let's just schedule it, the call will be deleted
            // in our main worker thread context.
            del_call_scheduled = true;
        }

    }

    public void notifyMediaState(PanelCall panelCall) {
        mediaManager.setupMediaTransmission(panelCall);
    }
}
