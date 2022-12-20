package at.ac.csdc23vz_02.common;

import at.ac.csdc23vz_02.common.exceptions.BankServerException;

import javax.ejb.Remote;

/**
 * at.ac.csdc23vz_02.common.BankServer Method declaration Interface
 * Gets Implemented by the Bankserver
 *
 */
@Remote
public interface BankServer {

    String createCustomer(Customer customer) throws BankServerException;
}
