package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.bankserver.entity.*;
import at.ac.csdc23vz_02.bankserver.util.LoginType;
import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.*;
import at.ac.csdc23vz_02.trading.PublicStockQuote;
import at.ac.csdc23vz_02.trading.TradingWSException_Exception;
import at.ac.csdc23vz_02.trading.TradingWebService;
import at.ac.csdc23vz_02.trading.TradingWebServiceService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Stateless(name="BankServer")
@PermitAll
public class BankServerImpl implements BankServer {
    private static final Logger log = LoggerFactory.getLogger(BankServerImpl.class);
    private final WildflyAuthDBHelper wildflyAuthDBHelper = new WildflyAuthDBHelper();
    private final TradingWebService tradingWebService;
    private Person loggedInUser;

    @Inject CustomerEntityDAO customerEntityDAO;
    @Inject EmployeeEntityDAO employeeEntityDAO;
    @Inject TransactionEntityDAO transactionEntityDAO;
    @Resource private SessionContext sessionContext;

    public BankServerImpl() throws BankServerException {
        this.tradingWebService = initTradingService();
    }

    /**
     * Initialize Trading WebService upon Bean Creation for subsequent use in other functions
     *
     * @return initialized TradingWebService
     * @throws BankServerException if Initialization failed
     */
    private TradingWebService initTradingService() throws BankServerException {
        try {
            TradingWebServiceService tradingWebServiceService = new TradingWebServiceService();
            TradingWebService tradingWebService = tradingWebServiceService.getTradingWebServicePort();
            BindingProvider bindingprovider = (BindingProvider)tradingWebService;

            bindingprovider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "csdc23vz_02");
            bindingprovider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "DuTahkei2");

            return tradingWebService;
        } catch (Exception e) {
            throw new BankServerException("Failed initialize Trading Service!", BankServerExceptionType.WEBSERVICE_FAULT);
        }
    }


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

    /**
     * Persists Employee to Database and adds the user simultaneously to the Wildfly Auth Database
     * @param employee Employee Object to add
     * @throws BankServerException When User is already in Database or if Adding to wildfly Database did not work
     */
    @RolesAllowed({"employee"})
    public void createEmployee(Employee employee) throws BankServerException {
        EmployeeEntity employeeEntity = new EmployeeEntity(employee);
        employeeEntityDAO.persist(employeeEntity);
        try {
            wildflyAuthDBHelper.addUser(employee.getUserName(), employee.getPassword(), new String[]{"employee"});
        } catch (IOException ioException) {
            throw new BankServerException("Error when creating User at Wildfly Server" + ioException, BankServerExceptionType.SESSION_FAULT);
        }
    }

    /**
     * Login function. Checks if User is existent in DB --> Differentiates Employee and Customer
     * @param credentials Login Credentials
     * @return Response code --> LoginType (1 --> Customer Success, 2 --> Employee Success, 3 --> Login Failure
     * @see LoginType Enum Class for Login Types
     * @throws BankServerException User is not existent in DB
     */
    @RolesAllowed({"employee", "customer"})
    public int login(List<String> credentials) throws BankServerException {
        List<CustomerEntity> customerEntity = customerEntityDAO.findByUsername(credentials.get(0));
        List<EmployeeEntity> employeeEntities = employeeEntityDAO.findByUsername(credentials.get(0));
        if(customerEntity.isEmpty() && employeeEntities.isEmpty()){
            //User  does not exist in DB
            throw new BankServerException("No Such User!", BankServerExceptionType.SESSION_FAULT);
        } else if(customerEntity.isEmpty()) {
            //User is an en Employee
            for(EmployeeEntity employee : employeeEntities) {
                if(employee.getPwHash().equals(credentials.get(1))) {
                    return LoginType.EMPLOYEE_SUCCESS.getCode();
                }
                return LoginType.LOGIN_FAILURE.getCode();
            }
        } else if(employeeEntities.isEmpty()) {
            //User is a Customer
            for(CustomerEntity customer : customerEntity) {
                if(customer.getPwHash().equals(credentials.get(1))) {
                    return LoginType.CUSTOMER_SUCCESS.getCode();
                }
                return LoginType.LOGIN_FAILURE.getCode();
            }
        }
        return LoginType.LOGIN_FAILURE.getCode();
    }


    @RolesAllowed({"employee", "customer"})
    public List<Stock> listStock(String stockname)  throws BankServerException{
        List<Stock> stock = new ArrayList<>();
        try {
            List<PublicStockQuote> stockinfo = tradingWebService.findStockQuotesByCompanyName(stockname);
            for(PublicStockQuote var: stockinfo){
                stock.add(new Stock(
                        var.getCompanyName(),
                        var.getLastTradePrice().doubleValue(),
                        var.getLastTradeTime().toGregorianCalendar().getTime(),
                        var.getMarketCapitalization(),
                        var.getStockExchange(),
                        var.getSymbol()));
            }

        } catch (Exception e) {
            throw new BankServerException("Failed to read Stocks!", BankServerExceptionType.WEBSERVICE_FAULT);
        }
        return stock;
    }


    BigDecimal sell_stock(String share, int customer_id, int shares, int transactionID) throws BankServerException {
        try {
            BigDecimal a = tradingWebService.sell(share,shares);
            if(a.intValue() >= 0) {
                List<TransactionEntity> transactionEntities = transactionEntityDAO.getTransactionsByID(customer_id);
                if(!(transactionEntities.isEmpty())) {
                    if(transactionEntityDAO.sellTransaction(transactionID, shares)) {
                        return a;
                    }
                }
            }
            return a;

        } catch (Exception e) {
            throw new BankServerException(e.getMessage(), BankServerExceptionType.TRANSACTION_FAULT);
        }


    }

    BigDecimal buy_stock(String share, CustomerEntity customer, int shares) throws BankServerException {
        try {
            BigDecimal a = tradingWebService.buy(share,shares);
            if(a.intValue() >= 0) {
                List<Stock> stockList = findStockBySymbol(List.of(share));
                if(!stockList.isEmpty()) {
                    if(!(stockList.size() > 1)) {
                        TransactionEntity transactionEntity = new TransactionEntity(stockList.get(0), customer, shares);
                        transactionEntity.setBuyPrice(a);
                        transactionEntityDAO.persist(transactionEntity);
                    } else {
                        throw new BankServerException("Symbol of Stock is not unique!", BankServerExceptionType.TRANSACTION_FAULT);
                    }
                } else {
                    throw new BankServerException("No Stocks found!", BankServerExceptionType.TRANSACTION_FAULT);
                }
            }
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankServerException(e.getMessage(), BankServerExceptionType.WEBSERVICE_FAULT);
        }


    }

    @RolesAllowed({"customer"})
    public BigDecimal buy(String share, int shares) throws BankServerException {
        this.loggedInUser = getLoggedInUser();
        return buy_stock(share, new CustomerEntity(new Customer(this.loggedInUser)), shares);
    }

    @RolesAllowed({"customer"})
    public BigDecimal sell(String share, int shares, int transactionID) throws BankServerException{
        this.loggedInUser = getLoggedInUser();
        return sell_stock(share, loggedInUser.getId(), shares, transactionID);
    }

    @RolesAllowed({"customer"})
    public List<Transaction> listDepot() throws BankServerException {
        loggedInUser = getLoggedInUser();
        return listDepotInternal(loggedInUser.getId());
    }

    @RolesAllowed({"employee"})
    public List<Transaction> listDepot(int customer_id) {
        return listDepotInternal(customer_id);
    }

    private List<Transaction> listDepotInternal(int customerID) {
        List<TransactionEntity> transactionEntities = transactionEntityDAO.getTransactionsByID(customerID);
        List<Transaction> transactions = new ArrayList<>();
        for(TransactionEntity transactionEntity : transactionEntities) {
            transactions.add(new Transaction(
                    transactionEntity.getID(),
                    transactionEntity.getCustomerID(),
                    transactionEntity.getStockSymbol(),
                    transactionEntity.getCompanyName(),
                    transactionEntity.getShareCount(),
                    transactionEntity.getTradeTime(),
                    transactionEntity.getBuyPrice()
            ));
        }
        return transactions;
    }

    @RolesAllowed({"employee"})
    public BigDecimal buy_for_customer(String share, int customer_id, int shares) throws BankServerException {
        BigDecimal a;
        List<CustomerEntity> customerEntities = customerEntityDAO.findbyID(customer_id);
        if(!customerEntities.isEmpty()) {
            if(!(customerEntities.size() > 1)) {
                a = buy_stock(share, customerEntities.get(0), shares);
                return a;
            } else {
                throw new BankServerException("More than one User Existent in DB! Please Contact Sysadmin!", BankServerExceptionType.DATABASE_FAULT);
            }
        } else {
            throw new BankServerException("No User With given ID!", BankServerExceptionType.DATABASE_FAULT);
        }
    }

    @RolesAllowed({"employee"})
    public BigDecimal sell_for_customer(String share, int customer_id, int shares, int transactionID) throws BankServerException {
        return sell_stock(share,customer_id,shares, transactionID);
    }

    @RolesAllowed({"employee"})
    public Customer search_customer_with_id(int customer_id) {
        List<CustomerEntity> customerEntities = customerEntityDAO.findbyID(customer_id);
        if(customerEntities.isEmpty()) {
            Customer customer = new Customer();
            customer.setId(0);
            return customer;
        } else {
            return new Customer(
                    customerEntities.get(0).getID(),
                    customerEntities.get(0).getFirstName(),
                    customerEntities.get(0).getLastName(),
                    customerEntities.get(0).getUserName()
                    );
        }
    }

    @RolesAllowed({"employee"})
    public List<Customer> search_customer_with_name(String first_name, String last_name) {
        List<Customer> customers = new ArrayList<>();
        List<CustomerEntity> customerEntities = customerEntityDAO.findByName(first_name, last_name);
        if(customerEntities.isEmpty()) {
            Customer customer = new Customer();
            customer.setId(0);
            customers.add(customer);
        } else {
            for(CustomerEntity customer : customerEntities) {
                customers.add(new Customer(
                        customer.getID(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getUserName()
                ));
            }
        }
        return customers;
    }

    @RolesAllowed({"employee", "customer"})
    public Person getLoggedInUser() throws BankServerException {
        String username = sessionContext.getCallerPrincipal().getName();
        List<CustomerEntity> customerEntity = customerEntityDAO.findByUsername(username);
        List<EmployeeEntity> employeeEntities = employeeEntityDAO.findByUsername(username);
        if(customerEntity.isEmpty() && !employeeEntities.isEmpty()) {
            return new Person(
                    employeeEntities.get(0).getID(),
                    employeeEntities.get(0).getFirstName(),
                    employeeEntities.get(0).getLastName(),
                    employeeEntities.get(0).getUserName(),
                    employeeEntities.get(0).getPwHash());
        } else if(!customerEntity.isEmpty() && employeeEntities.isEmpty()) {
            return new Person(
                    customerEntity.get(0).getID(),
                    customerEntity.get(0).getFirstName(),
                    customerEntity.get(0).getLastName(),
                    customerEntity.get(0).getUserName(),
                    customerEntity.get(0).getPwHash());
        } else {
            throw new BankServerException("User which is logged in could not be found in Database!", BankServerExceptionType.SESSION_FAULT);
        }
    }

    @RolesAllowed({"employee", "customer"})
    public boolean updateUser(Person person) throws BankServerException {
        List<CustomerEntity> customerEntity = customerEntityDAO.findByUsername(person.getUserName());
        List<EmployeeEntity> employeeEntities = employeeEntityDAO.findByUsername(person.getUserName());
        if(customerEntity.isEmpty() && !employeeEntities.isEmpty()) {
            employeeEntityDAO.updateUserByUsername(person);
        } else if(!customerEntity.isEmpty() && employeeEntities.isEmpty()) {
            customerEntityDAO.updateUserByUsername(person);
        } else {
            throw new BankServerException("User which is logged in could not be found in Database!", BankServerExceptionType.SESSION_FAULT);
        }

        return true;
    }

    private List<Stock> findStockBySymbol(List<String> symbols) throws TradingWSException_Exception {
        List<PublicStockQuote> publicStockQuotes = tradingWebService.getStockQuotes(symbols);
        List<Stock> stocks = new ArrayList<>();
        for(PublicStockQuote publicStockQuote : publicStockQuotes) {
            stocks.add(new Stock(
                    publicStockQuote.getCompanyName(),
                    publicStockQuote.getLastTradePrice().doubleValue(),
                    publicStockQuote.getLastTradeTime().toGregorianCalendar().getTime(),
                    publicStockQuote.getMarketCapitalization(),
                    publicStockQuote.getStockExchange(),
                    publicStockQuote.getSymbol())
            );
        }
        return stocks;
    }

}
