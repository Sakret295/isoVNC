package SipInterface;

public class AppConfig {
    public final String SIP_NAME;
    public final int SIP_PORT;
    public final int LOG_LEVEL;
    public final String USER_AGENT_NAME;
    public final String IP;

    public AppConfig(String sip_name, int sip_port, int log_level, String user_agent_name, String ip) {
        SIP_NAME = sip_name;
        SIP_PORT = sip_port;
        LOG_LEVEL = log_level;
        USER_AGENT_NAME = user_agent_name;
        IP = ip;
    }

}
