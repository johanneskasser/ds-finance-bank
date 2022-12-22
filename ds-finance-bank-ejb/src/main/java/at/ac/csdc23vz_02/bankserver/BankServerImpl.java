package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.bankserver.entity.CustomerEntity;
import at.ac.csdc23vz_02.bankserver.entity.CustomerEntityDAO;
import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.*;
import net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import java.util.ArrayList;
import java.util.List;


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


    public List<Stock> listStock(String stockname)  throws BankServerException{
        String user = "csdc23vz_02";
        String password = "DuTahkei2";
        List<Stock> stock = new ArrayList<>();
        try {
            tradingWebServiceService = new TradingWebServiceService();
            tradingWebService = tradingWebServiceService.getTradingWebServicePort();
            bindingprovider = (BindingProvider)tradingWebService;

            bindingprovider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
            bindingprovider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);

            List<PublicStockQuote> stockinfo = tradingWebService.findStockQuotesByCompanyName(stockname);
            for(PublicStockQuote var: stockinfo){
                stock.add(new Stock(var));
            }

        } catch (Exception e) {
            throw new BankServerException("Failed to read Stocks!", BankServerExceptionType.WEBSERVICE_FAULT);
        }
        return stock;
    }

    @Override
    public Boolean buy(String share, int shares) {
        return null;
    }

    @Override
    public Boolean sell(String share, int shares) {
        return null;
    }

    @Override
    public String listDepot() {
        return null;
    }

    @Override
    public String listDepot(int customer_id) {
        return null;
    }

    @Override
    public Boolean buy_for_customer(String share, int customer_id, int shares) {
        return null;
    }

    @Override
    public Boolean sell_for_customer(String share, int customer_id, int shares) {
        return null;
    }

    @Override
    public Customer search_customer_with_id(int customer_id) {
        return null;
    }

    @Override
    public Customer search_customer_with_name(String first_name, String last_name) {
        return null;
    }
}
