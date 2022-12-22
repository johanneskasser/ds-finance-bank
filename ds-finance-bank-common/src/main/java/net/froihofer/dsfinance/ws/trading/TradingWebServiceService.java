package net.froihofer.dsfinance.ws.trading;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.5.1
 * 2022-12-22T10:34:56.873+01:00
 * Generated source version: 3.5.1
 *
 */
@WebServiceClient(name = "TradingWebServiceService",
                  wsdlLocation = "https://edu.dedisys.org/ds-finance/ws/TradingService?wsdl",
                  targetNamespace = "http://trading.ws.dsfinance.froihofer.net/")
public class TradingWebServiceService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://trading.ws.dsfinance.froihofer.net/", "TradingWebServiceService");
    public final static QName TradingWebServicePort = new QName("http://trading.ws.dsfinance.froihofer.net/", "TradingWebServicePort");
    static {
        URL url = null;
        try {
            url = new URL("https://edu.dedisys.org/ds-finance/ws/TradingService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TradingWebServiceService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "https://edu.dedisys.org/ds-finance/ws/TradingService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public TradingWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TradingWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TradingWebServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public TradingWebServiceService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public TradingWebServiceService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public TradingWebServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns TradingWebService
     */
    @WebEndpoint(name = "TradingWebServicePort")
    public TradingWebService getTradingWebServicePort() {
        return super.getPort(TradingWebServicePort, TradingWebService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TradingWebService
     */
    @WebEndpoint(name = "TradingWebServicePort")
    public TradingWebService getTradingWebServicePort(WebServiceFeature... features) {
        return super.getPort(TradingWebServicePort, TradingWebService.class, features);
    }

}
