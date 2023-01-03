package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.bankserver.entity.CustomerEntity;
import at.ac.csdc23vz_02.bankserver.entity.CustomerEntityDAO;
import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.*;
import net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import net.froihofer.util.jboss.WildflyAuthDBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Stateless(name="BankServer")
@PermitAll
public class BankServerImpl implements BankServer {
    private static final Logger log = LoggerFactory.getLogger(BankServerImpl.class);
    private WildflyAuthDBHelper wildflyAuthDBHelper;

    @Inject CustomerEntityDAO customerEntityDAO;
    @Resource private SessionContext sessionContext;

    TradingWebService tradingWebService;
    TradingWebServiceService tradingWebServiceService;
    BindingProvider bindingprovider;


    /**
     * Persists Customer to Database and adds the user simultaneously to the Wildfly Auth Database
     * @param customer Customer Object to add
     * @throws BankServerException When User is already in Database or if Adding to wildfly Database did not work
     */
    @RolesAllowed({"employee"})
    public void createCustomer(Customer customer) throws BankServerException {
        CustomerEntity customerEntity = new CustomerEntity(customer);
        customerEntityDAO.persist(customerEntity);
        try {
            wildflyAuthDBHelper.addUser(customer.getUserName(), customer.getPassword(), new String[]{"customer"});
        } catch (IOException ioException) {
            throw new BankServerException("Error when creating User at Wildfly Server" + ioException, BankServerExceptionType.SESSION_FAULT);
        }
    }

    @RolesAllowed({"employee"})
    public void createEmployee(Employee employee) throws BankServerException {
        //TODO
    }

    @RolesAllowed({"employee", "customer"})
    public boolean login(List<String> credentials) throws BankServerException {
        List<CustomerEntity> customerEntity = customerEntityDAO.findByUsername(credentials.get(0));
        if(customerEntity.isEmpty()){
            throw new BankServerException("No Such User!", BankServerExceptionType.SESSION_FAULT);
        }
        return customerEntity.get(0).getPwHash().equals(credentials.get(1));
    }


    @RolesAllowed({"employee", "customer"})
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


    Boolean buy_stock(String share, int customer_id, int shares) {
        return true;
    }

    @RolesAllowed({"customer"})
    public Boolean buy(String share, int shares) {
        int currentuserid = 5;
        buy_stock(share,currentuserid,shares);
        return null;
    }

    @RolesAllowed({"customer"})
    public Boolean sell(String share, int shares) {
        return null;
    }

    @RolesAllowed({"customer"})
    public String listDepot() {
        return null;
    }

    @RolesAllowed({"customer"})
    public String listDepot(int customer_id) {
        return null;
    }

    @RolesAllowed({"employee"})
    public Boolean buy_for_customer(String share, int customer_id, int shares) {

        buy_stock(share,customer_id,shares);
        return null;
    }

    @RolesAllowed({"employee"})
    public Boolean sell_for_customer(String share, int customer_id, int shares) {
        return null;
    }

    @RolesAllowed({"employee"})
    public Customer search_customer_with_id(int customer_id) {
        return null;
    }

    @RolesAllowed({"employee"})
    public Customer search_customer_with_name(String first_name, String last_name) {
        return null;
    }
}
