package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.bankserver.entity.*;
import at.ac.csdc23vz_02.bankserver.util.LoginType;
import at.ac.csdc23vz_02.bankserver.util.PWHash;
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
import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * BankServer Implementation, EJB Bean Declaration
 */
@Stateless(name="BankServer")
@PermitAll
public class BankServerImpl implements BankServer {
    private static final Logger log = LoggerFactory.getLogger(BankServerImpl.class);
    private final WildflyAuthDBHelper wildflyAuthDBHelper = new WildflyAuthDBHelper();
    private final TradingWebService tradingWebService;
    private Person loggedInUser;
    private final PWHash pwHash = new PWHash();

    /**
     * Injection of DataAccessObjects utilized during implementation
     */
    @Inject CustomerEntityDAO customerEntityDAO;
    @Inject EmployeeEntityDAO employeeEntityDAO;
    @Inject TransactionEntityDAO transactionEntityDAO;
    @Inject BankBudgetEntityDAO bankBudgetEntityDAO;
    @Resource private SessionContext sessionContext;

    /**
     * BankserverImpl Construtor, Trading WS gets initialized upon constructions as well as Password Hasher Class
     * @throws BankServerException
     */
    public BankServerImpl() throws BankServerException {
        this.tradingWebService = initTradingService();
        this.pwHash.init();
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
        List<EmployeeEntity> employeeEntities = employeeEntityDAO.findByUsername(customer.getUserName());
        if(employeeEntities.isEmpty()) {
            List<String> saltAndHashPassword = this.pwHash.createSaltAndHashPassword(customer.getPassword());
            customerEntity.setPwHash(saltAndHashPassword.get(1));
            customerEntity.setSalt(saltAndHashPassword.get(0));
            customerEntityDAO.persist(customerEntity);
            try {
                wildflyAuthDBHelper.addUser(customer.getUserName(), customer.getPassword(), new String[]{"customer"});
            } catch (IOException ioException) {
                throw new BankServerException("Error when creating User at Wildfly Server" + ioException, BankServerExceptionType.SESSION_FAULT);
            }
        } else {
            throw new BankServerException("User already exists!", BankServerExceptionType.DATABASE_FAULT);
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
        List<CustomerEntity> customerEntities = customerEntityDAO.findByUsername(employee.getUserName());
        if(customerEntities.isEmpty()) {
            List<String> saltAndHashedPassword = this.pwHash.createSaltAndHashPassword(employee.getPassword());
            employeeEntity.setPwHash(saltAndHashedPassword.get(1));
            employeeEntity.setSalt(saltAndHashedPassword.get(0));
            employeeEntityDAO.persist(employeeEntity);
            try {
                wildflyAuthDBHelper.addUser(employee.getUserName(), employee.getPassword(), new String[]{"employee"});
            } catch (IOException ioException) {
                throw new BankServerException("Error when creating User at Wildfly Server" + ioException, BankServerExceptionType.SESSION_FAULT);
            }
        } else {
            throw new BankServerException("User already exists!", BankServerExceptionType.DATABASE_FAULT);
        }
    }

    /**
     * Login function. Checks if User is existent in DB --> Differentiates Employee and Customer
     * @param credentials Login Credentials
     * @return Response code --> LoginType (1 --> Customer Success, 2 --> Employee Success, 3 --> Login Failure)
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
                if(pwHash.checkPassword(employee.getSalt(), employee.getPwHash(), credentials.get(1))) {
                    return LoginType.EMPLOYEE_SUCCESS.getCode();
                }
                return LoginType.LOGIN_FAILURE.getCode();
            }
        } else if(employeeEntities.isEmpty()) {
            //User is a Customer
            for(CustomerEntity customer : customerEntity) {
                if(pwHash.checkPassword(customer.getSalt(), customer.getPwHash(), credentials.get(1))) {
                    return LoginType.CUSTOMER_SUCCESS.getCode();
                }
                return LoginType.LOGIN_FAILURE.getCode();
            }
        }
        return LoginType.LOGIN_FAILURE.getCode();
    }


    /**
     * Method to retrieve Stocks by Stockname (Company Name)
     * Employees and Customers are allowed to execute Method
     * @param stockname String
     * @return List of Stocks
     * @throws BankServerException if WebService Fails
     */
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


    /**
     * Internal Method used by Sell Stock Methods to actually sell a stock
     * @param share stock to sell
     * @param customer_id id of customer which wants to sell stock
     * @param shares amount of shares to sell
     * @param transactionID unique identifier of transaction ID for DB
     * @return Big Decimal Sell Price
     * @throws BankServerException If Web Service fails of DB Fails
     */
    BigDecimal sell_stock(String share, int customer_id, int shares, int transactionID) throws BankServerException {
        bankBudgetEntityDAO.persist();
        try {
            BigDecimal a = tradingWebService.sell(share,shares);
            if(a.intValue() >= 0) {
                List<TransactionEntity> transactionEntities = transactionEntityDAO.getTransactionsByID(customer_id);
                if(!(transactionEntities.isEmpty())) {
                    if(transactionEntityDAO.sellTransaction(transactionID, shares)) {
                        double totalValue = a.doubleValue() * shares;
                        bankBudgetEntityDAO.addBudget(totalValue);
                        return a;
                    }
                }
            }
            return a;

        } catch (Exception e) {
            throw new BankServerException(e.getMessage(), BankServerExceptionType.TRANSACTION_FAULT);
        }


    }

    /**
     * Internal Method used by Buy Stock Methods to actually sell a stock
     * @param share Symbol of Stock to buy
     * @param customer Customer who wants to buy Stock
     * @param shares Amount of Shares the customer wants to buy
     * @return Big Decimal of BuyPrivce per Stock
     * @throws BankServerException If WS Failes or DB Fails --> Can be retrieved from the Error Message
     */
    BigDecimal buy_stock(String share, Customer customer, int shares) throws BankServerException {
        bankBudgetEntityDAO.persist();
        try {
            BigDecimal a = tradingWebService.buy(share,shares);
            if(a.intValue() >= 0) {
                List<Stock> stockList = findStockBySymbol(List.of(share));
                if(!stockList.isEmpty()) {
                    if(!(stockList.size() > 1)) {
                        TransactionEntity transactionEntity = new TransactionEntity(stockList.get(0), customer, shares);
                        transactionEntity.setBuyPrice(a);
                        double totalPrice = a.doubleValue() * transactionEntity.getShareCount().doubleValue();
                        transactionEntityDAO.persist(transactionEntity);
                        bankBudgetEntityDAO.deductBudget(totalPrice);
                    } else {
                        throw new BankServerException("Symbol of Stock is not unique!", BankServerExceptionType.TRANSACTION_FAULT);
                    }
                } else {
                    throw new BankServerException("No Stocks found!", BankServerExceptionType.TRANSACTION_FAULT);
                }
            }
            return a;
        } catch (Exception e) {
            throw new BankServerException("Failed to buy Stocks!" + e.getMessage(), BankServerExceptionType.WEBSERVICE_FAULT);
        }


    }

    /**
     * Buy Method which a customer calls, this means that the user logged in is automatically the one who buys shares --> so
     * no customer can buy stocks for other customer
     * @param share Share Customer wants to buy
     * @param shares Share Amount Customer wants to buy
     * @return Big Decimal Buy Price per Share
     * @throws BankServerException If Execption gets thrown by buy_stock
     */
    @RolesAllowed({"customer"})
    public BigDecimal buy(String share, int shares) throws BankServerException {
        this.loggedInUser = getLoggedInUser();
        return buy_stock(share, new Customer(this.loggedInUser), shares);
    }

    /**
     * Sell Method which a customer calls, this means that the user logged in is automatically the one who sells shares --> so
     * no customer can sell stocks for other customer
     * @param share share to sell
     * @param shares amount of shares to sell
     * @param transactionID Transaction ID of Transaction from Customer --> Used for DB
     * @return BigDecimal Sell Price
     * @throws BankServerException If sell_stock throws Exception
     */
    @RolesAllowed({"customer"})
    public BigDecimal sell(String share, int shares, int transactionID) throws BankServerException{
        this.loggedInUser = getLoggedInUser();
        return sell_stock(share, loggedInUser.getId(), shares, transactionID);
    }

    /**
     * List Depot for Customer
     * @return List of Transactions for specific Customer
     * @throws BankServerException if Error in DB Occurs
     */
    @RolesAllowed({"customer"})
    public List<Transaction> listDepot() throws BankServerException {
        loggedInUser = getLoggedInUser();
        return listDepotInternal(loggedInUser.getId());
    }

    /**
     * List Depot for Customer from Employee
     * @param customer_id ID of Customer whose Depot should be Shown
     * @return List of Transactions for specific Customer
     * @throws BankServerException if Error in DB Occurs
     */
    @RolesAllowed({"employee"})
    public List<Transaction> listDepot(int customer_id) throws BankServerException {
        return listDepotInternal(customer_id);
    }

    /**
     * Internal Method to List Depot called by the Employee or Customer Functions to perform the lookup
     * @param customerID ID Of customer which Depot should be looked up
     * @return List of Transactions for CustomerID
     * @throws BankServerException if Error in DB Occurs
     */
    private List<Transaction> listDepotInternal(int customerID) throws BankServerException {
        List<TransactionEntity> transactionEntities = transactionEntityDAO.getTransactionsByID(customerID);
        List<Transaction> transactions = new ArrayList<>();
        for(TransactionEntity transactionEntity : transactionEntities) {
            List<Stock> x = findStockBySymbol(Arrays.asList(transactionEntity.getStockSymbol()));
            transactions.add(new Transaction(
                    transactionEntity.getID(),
                    transactionEntity.getCustomerID(),
                    transactionEntity.getStockSymbol(),
                    transactionEntity.getCompanyName(),
                    transactionEntity.getShareCount(),
                    transactionEntity.getTradeTime(),
                    BigDecimal.valueOf(x.get(0).getLastTradePrice())
            ));
        }
        return transactions;
    }

    /**
     * Buy for customer which gets performed by Employee
     * @param share Share Customer wants to buy
     * @param customer_id Customer ID
     * @param shares Amount of Shares
     * @return Buy Price
     * @throws BankServerException if error in DB or WS occurs
     */
    @RolesAllowed({"employee"})
    public BigDecimal buy_for_customer(String share, int customer_id, int shares) throws BankServerException {
        BigDecimal a;
        List<CustomerEntity> customerEntities = customerEntityDAO.findbyID(customer_id);
        if(!customerEntities.isEmpty()) {
            if(!(customerEntities.size() > 1)) {
                a = buy_stock(share, new Customer(
                        customerEntities.get(0).getID(),
                        customerEntities.get(0).getFirstName(),
                        customerEntities.get(0).getLastName(),
                        customerEntities.get(0).getUserName(),
                        customerEntities.get(0).getZip(),
                        customerEntities.get(0).getCountry(),
                        customerEntities.get(0).getStreet(),
                        customerEntities.get(0).getNumber(),
                        customerEntities.get(0).getCity())
                        , shares);
                return a;
            } else {
                throw new BankServerException("More than one User Existent in DB! Please Contact Sysadmin!", BankServerExceptionType.DATABASE_FAULT);
            }
        } else {
            throw new BankServerException("No User With given ID!", BankServerExceptionType.DATABASE_FAULT);
        }
    }

    /**
     * Sell Share for Customer performed by Employee
     * @param share Share Customer wants to sell
     * @param customer_id ID of acting Customer
     * @param shares Amount of Shares Customer wants to sell
     * @param transactionID Transaction ID used for DB
     * @return Buy Price
     * @throws BankServerException If error in WS or DB Occurs
     */
    @RolesAllowed({"employee"})
    public BigDecimal sell_for_customer(String share, int customer_id, int shares, int transactionID) throws BankServerException {
        return sell_stock(share,customer_id,shares, transactionID);
    }

    /**
     * Search Customer by ID
     * @param customer_id ID of Customer to be searched
     * @return Customer (if no customer is found empty Customer is returned with ID of 0)
     */
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
                    customerEntities.get(0).getUserName(),
                    customerEntities.get(0).getPwHash(),
                    customerEntities.get(0).getZip(),
                    customerEntities.get(0).getCountry(),
                    customerEntities.get(0).getStreet(),
                    customerEntities.get(0).getNumber(),
                    customerEntities.get(0).getCity()
                    );
        }
    }

    /**
     * Search Customer with Name
     * @param first_name First Name
     * @param last_name Last Name
     * @return Customer (if no customer is found empty Customer is returned with ID of 0)
     */
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
                        customer.getUserName(),
                        customer.getZip(),
                        customer.getCountry(),
                        customer.getStreet(),
                        customer.getNumber(),
                        customer.getCity()
                ));
            }
        }
        return customers;
    }

    /**
     * Get Available Budget from Bank at Stock Exchange performed by Employee
     * @return Double --> Available Budget
     */
    @RolesAllowed({"employee"})
    public Double getAvailableBudget() {
        bankBudgetEntityDAO.persist();
        return bankBudgetEntityDAO.getAvailableBudget();
    }

    /**
     * Method to get the currently logged-in User
     * Can be performed by employees and costumers
     * @return returns a Person Object of the currently logged-in User
     * @throws BankServerException throws an exception if the user which is logged in could not be found in Database
     */
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
                    customerEntity.get(0).getUserName(),
                    customerEntity.get(0).getFirstName(),
                    customerEntity.get(0).getLastName(),
                    customerEntity.get(0).getPwHash(),
                    customerEntity.get(0).getZip(),
                    customerEntity.get(0).getCountry(),
                    customerEntity.get(0).getStreet(),
                    customerEntity.get(0).getNumber(),
                    customerEntity.get(0).getCity()
            );
        } else {
            throw new BankServerException("User which is logged in could not be found in Database!", BankServerExceptionType.SESSION_FAULT);
        }
    }

    /**
     * Method to update the User-Informations of a person
     * Can be performed by employees and costumers
     * Note: PW Will always get hashed newly, so a new salt will be generated and persisted every time the method is called
     * @param person Person Object with the updated information
     * @return returns a boolean: true=successfully updated / false=not updated
     * @throws BankServerException throws an exception if:
     *                                                  - inherited from createSaltAndHashPassword()
     *                                                  - failed to change User Information in Wildfly Auth DB
     *                                                  - User which is logged in could not be found in Database
     */
    @RolesAllowed({"employee", "customer"})
    public boolean updateUser(Person person, String confirmPassword) throws BankServerException {
        if(!(login(Arrays.asList(person.getUserName(), confirmPassword)) == LoginType.LOGIN_FAILURE.getCode())) {
            //User is correctly authenticated
            List<CustomerEntity> customerEntity = customerEntityDAO.findByUsername(person.getUserName());
            List<EmployeeEntity> employeeEntities = employeeEntityDAO.findByUsername(person.getUserName());
            List<String> pwAndSalt = pwHash.createSaltAndHashPassword(person.getPassword());
            String plainTextPassword = person.getPassword();
            person.setPassword(pwAndSalt.get(1));
            if(!employeeEntities.isEmpty() || !customerEntity.isEmpty()) {
                try {
                    wildflyAuthDBHelper.changePassword(person.getUserName(), plainTextPassword);
                } catch (IOException ioException) {
                    throw new BankServerException("Failed to change User Information in Wildfly Auth DB!" + ioException.getMessage(), BankServerExceptionType.WEBSERVER_FAULT);
                }
            }
            if(customerEntity.isEmpty() && !employeeEntities.isEmpty()) {
                employeeEntityDAO.updateUserByUsername(person, pwAndSalt.get(0));
            } else if(!customerEntity.isEmpty() && employeeEntities.isEmpty()) {
                customerEntityDAO.updateUserByUsername(person, pwAndSalt.get(0));
            } else {
                throw new BankServerException("User which is logged in could not be found in Database!", BankServerExceptionType.SESSION_FAULT);
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * Method to get all Stock Information for the input stock symbols
     * @param symbols the stock symbols to search for
     * @return returns a List of Stocks based on the input symbols
     * @throws BankServerException throws an exception if the tradingWebService returns an exception
     */
    private List<Stock> findStockBySymbol(List<String> symbols) throws BankServerException{
        try {
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
        } catch (TradingWSException_Exception webServiceException) {
            throw new BankServerException(webServiceException.getMessage(), BankServerExceptionType.WEBSERVICE_FAULT);
        }
    }

    /**
     * Method to delete a User from the Database and the Wildfly-Server
     * Can be performed by employees
     * @param person Person Object to be deleted
     * @return returns a boolean: true=user successfully deleted / false=user not deleted
     * @throws BankServerException Throws an exception:
     *                                                 - if the own user should be deleted
     *                                                 - if there is an error while trying to delete user from Wildfly DB
     */
    @RolesAllowed({"employee"})
    public boolean deleteUser(Person person) throws BankServerException {
        this.loggedInUser = getLoggedInUser();
        if(person.getUserName().equals(this.loggedInUser.getUserName())) {
            throw new BankServerException("Cannot delete own User.", BankServerExceptionType.SESSION_FAULT);
        }

        List<TransactionEntity> transactionEntities = transactionEntityDAO.getTransactionsByID(person.getId());
        if(transactionEntities.isEmpty()) {
            try {
                wildflyAuthDBHelper.removeUser(person.getUserName());
            } catch (IOException ioException) {
                throw new BankServerException("Error while trying to delete user from Wildfly DB " + ioException.getMessage(), BankServerExceptionType.WEBSERVER_FAULT);
            }
            customerEntityDAO.removeUserByID(person.getId());
            employeeEntityDAO.removeUserByID(person.getId());
            return true;
        }
        return false;
    }

}
