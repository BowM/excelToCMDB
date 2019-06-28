package com.hp.schemas;

import org.apache.axis2.AxisFault;
import org.apache.axis2.java.security.TrustAllTrustManager;
import org.apache.axis2.transport.http.*;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl;
import com.hp.ucmdb.generated.services.UcmdbServiceStub;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 大老吴
 */
abstract class Demo {

    private static UcmdbServiceStub stub;
    static UcmdbServiceStub.CmdbContext context;
    private static final String PROTOCOL = "https";
    private static final String HOST_NAME = "100.16.4.41";
    private static final int PORT = 8443;
    private static final String FILE = "/axis2/services/UcmdbService";

    static {
        try {
            setStub(createUcmdbServiceStub("admin", "UISysadmin_1234"));
            setContext();
        } catch (Exception e) {
            // handle exception
        }
    }

    static UcmdbServiceStub getStub() {
        return stub;
    }

    private static void setStub(UcmdbServiceStub ucmdbStub) {
        stub = ucmdbStub;
    }

    private static void setContext() {
        UcmdbServiceStub.CmdbContext ctx = new UcmdbServiceStub.CmdbContext();
        ctx.setCallerApplication("demo");
        context = ctx;
    }




    private static UcmdbServiceStub createUcmdbServiceStub(String username, String password) throws Exception {
        URL url;
        UcmdbServiceStub serviceStub;
        try {
            url = new URL(Demo.PROTOCOL, Demo.HOST_NAME, Demo.PORT, Demo.FILE);
            serviceStub = new UcmdbServiceStub(url.toString());
            HttpTransportPropertiesImpl.Authenticator auth = new HttpTransportPropertiesImpl.Authenticator();
            auth.setUsername(username);
            auth.setPassword(password);
            //跳证书
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,new TrustManager[]{new TrustAllTrustManager()},null);
            serviceStub._getServiceClient().getOptions().setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER,new Protocol("https",(ProtocolSocketFactory) new org.apache.axis2.transport.http.security.SSLProtocolSocketFactory(sslContext),443));
            serviceStub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
        } catch (AxisFault | MalformedURLException axisFault) {
            throw new Exception("Failed to create SOAP adapter for " + Demo.HOST_NAME, axisFault);
        }
        return serviceStub;
    }
}
