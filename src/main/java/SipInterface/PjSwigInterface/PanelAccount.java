package SipInterface.PjSwigInterface;

import SipInterface.AppConfig;
import org.pjsip.pjsua2.*;

public class PanelAccount extends Account {
    private final PanelCore parentPanel;

    public PanelAccount(PanelCore parentPanel){
        super();
        this.parentPanel = parentPanel;
    }

    @Override
    public void onIncomingCall(OnIncomingCallParam prm) {
        PanelCall call = new PanelCall(parentPanel, this, prm.getCallId());
        parentPanel.processIncomingCall(call);
    }
}
