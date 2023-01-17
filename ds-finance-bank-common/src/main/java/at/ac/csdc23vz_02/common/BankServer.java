package at.ac.csdc23vz_02.common;

import at.ac.csdc23vz_02.common.exceptions.BankServerException;

import javax.ejb.Remote;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * at.ac.csdc23vz_02.common.BankServer Method declaration Interface
 * Gets Implemented by the Bankserver
 *
 */
@Remote
public interface BankServer {

    /**
     * Used to create a new Customer
     * Implemented by the Bankserver
     */
    void createCustomer(Customer customer) throws BankServerException;

    /**
     * Used to create a new Employee
     * Implemented by the Bankserver
     */
    void createEmployee(Employee employee) throws BankServerException;

    /**
     * Used to login at the BankServer
     * Implemented by the Bankserver
     */
    int login(List<String> credentials) throws BankServerException;

    /**
     * Used to get the currently logged in User
     * Implemented by the Bankserver
     */
    Person getLoggedInUser() throws BankServerException;

    /**
     * Used to update an existing User
     * Implemented by the Bankserver
     */
    boolean updateUser(Person person, String confirmPassword) throws BankServerException;

    /**
     * Used to list an available Stock
     * Implemented by the Bankserver
     */
    List<Stock> listStock(String stockname) throws BankServerException;

    /**
     * Used to buy one or more stock shares
     * Implemented by the Bankserver
     */
    BigDecimal buy(String share, int shares) throws BankServerException;

    /**
     * Used to sell one or more stock shares
     * Implemented by the Bankserver
     */
    BigDecimal sell(String share, int shares, int transactionID) throws BankServerException;

    /**
     * Used to list a Depot
     * Implemented by the Bankserver
     */
    List<Transaction> listDepot() throws BankServerException;

    /**
     * Used to list a Depot of a Customer
     * Implemented by the Bankserver
     */
    List<Transaction> listDepot(int customer_id) throws BankServerException;

    /**
     * Used to buy one or more stock shares for a customer
     * Implemented by the Bankserver
     */
    BigDecimal buy_for_customer(String share, int customer_id, int shares) throws BankServerException;

    /**
     * Used to sell one or more stock shares for a customer
     * Implemented by the Bankserver
     */
    BigDecimal sell_for_customer(String share, int customer_id, int shares, int transactionID) throws BankServerException;

    /**
     * Used to search for a customer based on his ID
     * Implemented by the Bankserver
     */
    Customer search_customer_with_id(int customer_id);

    /**
     * Used to search for a customer based on his full name
     * Implemented by the Bankserver
     */
    List<Customer> search_customer_with_name(String first_name, String last_name);

    /**
     * Used to get the currenlty available budget of the bankserver at the "Börse"
     * Implemented by the Bankserver
     */
    Double getAvailableBudget();

    /**
     * Used to delete an existing customer or employee
     * Implemented by the Bankserver
     */
    boolean deleteUser(Person person) throws BankServerException;
}
