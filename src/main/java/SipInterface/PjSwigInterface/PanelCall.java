package SipInterface.PjSwigInterface;

import SipInterface.SipJNI.CallbackClass;
import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.OnCallTsxStateParam;
import org.pjsip.pjsua2.*;

public class PanelCall extends Call {
    private static final String METHOD_INFO_NAME = "INFO";
    private final PanelCore parentPanel;

    public PanelCall(PanelCore parentPanel, Account acc, int call_id) {
        super(acc, call_id);
        this.parentPanel = parentPanel;
    }

    @Override
    public void onCallState(OnCallStateParam prm) {
        //notify the observer

    }

    @Override
    public void onCallMediaState(OnCallMediaStateParam prm) {
        //notify the observer
        //which will pass it to the media manager
        parentPanel.notifyMediaState(this);
    }

    @Override
    public void onCallTsxState(OnCallTsxStateParam prm) {
        int eventType = prm.getE().getType();
        int tsxState = prm.getE().getBody().getTsxState().getType();
        String methodName = prm.getE().getBody().getTsxState().getTsx().getMethod();
        if ( methodName.equals(METHOD_INFO_NAME) ){
            System.out.println("received info message");
            //this is an info message and if it has content it should be forwarded to a class responsible for dealing with info messages
            if (prm.getE().getBody().getTsxState().getSrc().getRdata().getMsgType().equals("application")) {
                byte[] msgData = prm.getE().getBody().getTsxState().getSrc().getRdata().getData();
                CallbackClass.callbackWithInfo(msgData, msgData.length); //TODO: extract the data from the info message
            }
        }
    }

    @Override
    public void onCallSdpCreated(OnCallSdpCreatedParam prm) {
        SdpSession sdp = prm.getSdp();
        sdp.setWholeSdp(sdp.getWholeSdp() +
                "s=" + parentPanel.getAppConfig().USER_AGENT_NAME + "\n" +
                "a=rtpmap:0 pcmu/8000\n" +
                "a=ptime:20\n" +
                "a=maxptime:20\n" +
                "a=riedel-artist-audio-packet-size:0\n" +
                "a=riedel-artist-receive-buffer-size:1\n" +
                "a=riedel-artist-voice-activity-detection:1\n" +
                "a=riedel-artist-diffserv:0\n" +
                "a=riedel-artist-channel-mode:1\n" +
                "a=riedel-artist-version:2.21\n" +
                "a=riedel-voip-protocol-version:2\n" +
                "a=sendrecv\n");
        prm.setSdp(sdp);
    }

    public void sendInfoRequest(byte[] data, long length) throws Exception {
        CallSendRequestParam requestParam = new CallSendRequestParam();
        requestParam.setMethod("INFO");
        SipTxOption txOption = new SipTxOption();
        txOption.setContentType("application/artist-panel-rx");
        txOption.setMsgBodyRaw(data, length);
        requestParam.setTxOption(txOption);
        sendRequest(requestParam);
    }
}
