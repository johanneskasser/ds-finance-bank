
package at.ac.csdc23vz_02.common.WebService.net.froihofer.dsfinance.ws.trading;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.5.1
 * 2022-12-21T18:09:48.238+01:00
 * Generated source version: 3.5.1
 */

@WebFault(name = "TradingWSException", targetNamespace = "http://trading.ws.dsfinance.froihofer.net/")
public class TradingWSException_Exception extends Exception {

    private TradingWSException faultInfo;

    public TradingWSException_Exception() {
        super();
    }

    public TradingWSException_Exception(String message) {
        super(message);
    }

    public TradingWSException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public TradingWSException_Exception(String message, net.froihofer.dsfinance.ws.trading.TradingWSException tradingWSException) {
        super(message);
        this.faultInfo = tradingWSException;
    }

    public TradingWSException_Exception(String message, net.froihofer.dsfinance.ws.trading.TradingWSException tradingWSException, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = tradingWSException;
    }

    public net.froihofer.dsfinance.ws.trading.TradingWSException getFaultInfo() {
        return this.faultInfo;
    }
}
