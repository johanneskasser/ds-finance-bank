package at.ac.csdc23vz_02.common;

import at.ac.csdc23vz_02.common.exceptions.BankServerException;

import javax.ejb.Remote;
import java.util.List;

/**
 * at.ac.csdc23vz_02.common.BankServer Method declaration Interface
 * Gets Implemented by the Bankserver
 *
 */
@Remote
public interface BankServer {

    void createCustomer(Customer customer) throws BankServerException;
    void createEmployee(Employee employee) throws BankServerException;
    boolean login(List<String> credentials) throws BankServerException;

    List<Stock> listStock(String stockname) throws BankServerException;

    Boolean buy(String share, int shares);

    Boolean sell(String share, int shares);

    String listDepot();

    String listDepot(int customer_id);

    Boolean buy_for_customer(String share, int customer_id, int shares);

    Boolean sell_for_customer(String share, int customer_id, int shares);

    Customer search_customer_with_id(int customer_id);

    Customer search_customer_with_name(String first_name, String last_name);
}
