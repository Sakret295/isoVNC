package SipInterface.SipJNI;

import Core.Channel.AbstractChannelCallback;

/* class to be called from the JNI native library */
public class CallbackClass {
    private static AbstractChannelCallback implementationClass;

    /* call this method after creating a manager class to set a class implementing the callback */
    public static void setImplementationClass(AbstractChannelCallback impClass){
        implementationClass = impClass;
    }

    public static void callbackWithInfo(byte[] data, int length) {
        if (implementationClass != null)
            implementationClass.processCallback(data, length);
    }
}
