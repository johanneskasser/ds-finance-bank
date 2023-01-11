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

    void createCustomer(Customer customer) throws BankServerException;
    void createEmployee(Employee employee) throws BankServerException;
    int login(List<String> credentials) throws BankServerException;
    Person getLoggedInUser() throws BankServerException;
    boolean updateUser(Person person) throws BankServerException;

    List<Stock> listStock(String stockname) throws BankServerException;

    BigDecimal buy(String share, int shares) throws BankServerException;

    BigDecimal sell(String share, int shares, int transactionID) throws BankServerException;

    List<Transaction> listDepot() throws BankServerException;

    List<Transaction> listDepot(int customer_id) throws BankServerException;

    BigDecimal buy_for_customer(String share, int customer_id, int shares) throws BankServerException;

    BigDecimal sell_for_customer(String share, int customer_id, int shares, int transactionID) throws BankServerException;

    Customer search_customer_with_id(int customer_id);

    List<Customer> search_customer_with_name(String first_name, String last_name);

    Double getAvailableBudget();
}
