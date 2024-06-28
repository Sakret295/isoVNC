package SipInterface.PjSwigInterface;

import org.pjsip.pjsua2.*;

public class MediaManager {
    private Endpoint endpoint;

    public MediaManager(Endpoint endpoint){
        this.endpoint = endpoint;
    }

    //start transmission
    public void setupMediaTransmission(Call call) {
        CallInfo ci = null;
        try {
            ci = call.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CallMediaInfoVector cmiv = ci.getMedia();

        for (int i = 0; i < cmiv.size(); i++) {
            CallMediaInfo cmi = cmiv.get(i);
            if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO &&
                    (cmi.getStatus() ==
                            pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE ||
                            cmi.getStatus() ==
                                    pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD)) {
                // connect ports
                try {
                    AudioMedia am = call.getAudioMedia(i);
                    endpoint.audDevManager().setSndDevMode(1);
                    //TODO: implement mic
//                    endpoint.audDevManager().getCaptureDevMedia().
//                            startTransmit(am);
                    am.startTransmit(endpoint.audDevManager().
                            getPlaybackDevMedia());
                } catch (Exception e) {
                    System.out.println("Failed connecting media ports" +
                            e.getMessage());
                }
            }
        }
    }

    //change conf bridge speaker volume
    public void setOutputVolume(int volume){
            try {
                endpoint.audDevManager().setOutputVolume(volume);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    //change conf bridge mic volume
    public void setInputVolume(int volume){
        //TODO: implement mic
    }
}
