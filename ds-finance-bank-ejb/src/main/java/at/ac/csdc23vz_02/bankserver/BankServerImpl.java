package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.*;

import javax.ejb.Stateless;
import java.util.Objects;

@Stateless(name="BankServer")
public class BankServerImpl implements BankServer {

    public void createCustomer(Customer customer) throws BankServerException {
        if(Objects.equals(customer.getFirstName(), "Johannes")){
            throw new BankServerException("Johannes du Pisser", BankServerExceptionType.SESSION_FAULT);
        }
    }
}
