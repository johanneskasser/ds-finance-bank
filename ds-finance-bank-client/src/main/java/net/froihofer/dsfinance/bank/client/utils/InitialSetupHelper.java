package net.froihofer.dsfinance.bank.client.utils;


import at.ac.csdc23vz_02.common.BankServer;
import at.ac.csdc23vz_02.common.Customer;
import at.ac.csdc23vz_02.common.Employee;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import net.froihofer.dsfinance.bank.client.BankClient;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


/**
 * Helper Class to Setup local Environment for Development!
 * All Created Users should be deleted after initial Setup!
 * Creates:
 *      1x Customer user --> Username = Customer, Password = Customer
 *      1x Employee user --> Username = Employee, Password = Employee
 *
 * Please Create an Application-User 'admin' with Password 'admin' with the role 'employee' using the add user Script first!
 */
public class InitialSetupHelper {
    private static Logger log = LoggerFactory.getLogger(InitialSetupHelper.class);
    private Customer customer = new Customer("Customer", "Customer", "Customer", "Customer", "1150", "Austria", "Herklotzgasse", "10", "Vienna");
    private Employee employee = new Employee("Employee", "Employee", "Employee", "Employee");
    private BankServer bankServer;


    public InitialSetupHelper(BankServer bankServer) {
        this.bankServer = bankServer;
    }

    public InitialSetupHelper() {
    }

    public BankServer getBankServer() {
        return bankServer;
    }

    public void setBankServer(BankServer bankServer) {
        this.bankServer = bankServer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean createUsers() {
        try {
            bankServer.createEmployee(employee);
            bankServer.createCustomer(customer);
            return true;
        } catch (BankServerException bankServerException) {
            return false;
        }
    }

    public boolean getRmiProxy(String userName, String userPassword) {
        AuthCallbackHandler.setUsername(userName);
        AuthCallbackHandler.setPassword(userPassword);
        Properties props = new Properties();
        props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
        props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
        try {
            WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
            bankServer = jndiHelper.lookup("BankServer", BankServer.class);
            return true;
        }
        catch (NamingException e) {
            //This line is not used since the Naming Exception gets thrown when the user is not logged in correctly!
            //log.error("Failed to initialize InitialContext.",e);
            return false;
        }
    }

    public void destroy() {
        this.bankServer = null;
        this.customer = null;
        this.employee = null;
    }

    public static void main(String[] args) {
        InitialSetupHelper initialSetupHelper = new InitialSetupHelper();
        if(!initialSetupHelper.getRmiProxy("admin", "admin")) {
            System.out.println("Please Create an Application-User 'admin' with Password 'admin' with the role 'employee' using the add user Script first!");
        } else {
            if(initialSetupHelper.createUsers()) {
                System.out.println("Users created Successfully!");
            } else {
                System.out.println("Users could not be created!");
            }
        }
        initialSetupHelper.destroy();
    }
}
