package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.bankserver.entity.CustomerEntity;
import at.ac.csdc23vz_02.bankserver.entity.CustomerEntityDAO;
import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.WebService.net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import at.ac.csdc23vz_02.common.WebService.net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import at.ac.csdc23vz_02.common.WebService.net.froihofer.dsfinance.ws.trading.TradingWebService;
import at.ac.csdc23vz_02.common.WebService.net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import at.ac.csdc23vz_02.common.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Stateless(name="BankServer")
@PermitAll
public class BankServerImpl implements BankServer {
    private static final Logger log = LoggerFactory.getLogger(BankServerImpl.class);
    @Inject CustomerEntityDAO customerEntityDAO;

    TradingWebService tradingWebService;
    TradingWebServiceService tradingWebServiceService;
    BindingProvider bindingprovider;

    public void createCustomer(Customer customer) throws BankServerException {
        //TODO: Check Permissions of creating User!
        //TODO: Check if Username already exists!
        CustomerEntity customerEntity = new CustomerEntity(customer);
        customerEntityDAO.persist(customerEntity);


    }

    public boolean login(Customer customer) throws BankServerException {
        List<CustomerEntity> customerEntity = customerEntityDAO.findByUsername(customer.getUserName());
        if(customerEntity.isEmpty()){
            throw new BankServerException("No Such User!", BankServerExceptionType.SESSION_FAULT);
        }
        return customerEntity.get(0).getPwHash().equals(customer.getPassword());
    }

    public String listStock(String stockname)  throws BankServerException{
        String user = "csdc23vz_02";
        String password = "DuTahkei2";
        try{
            tradingWebServiceService = new TradingWebServiceService();
            tradingWebService = tradingWebServiceService.getTradingWebServicePort(); //Funktioniert nicht
            bindingprovider = (BindingProvider)tradingWebService;

            bindingprovider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
            bindingprovider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);

            List<String> stock = new ArrayList<>();

            List<PublicStockQuote> stockinfo = tradingWebService.findStockQuotesByCompanyName(stockname);
            for(PublicStockQuote var: stockinfo){
                stock.add(var.getSymbol());
            }

        } catch (Exception e) {
            return e.toString();
           // e.printStackTrace();
        }
        return"Teststock";
    }
}
