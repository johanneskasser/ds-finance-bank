package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import java.util.Objects;

@Stateless(name="BankServer")
@PermitAll
public class BankServerImpl implements BankServer {
    private static final Logger log = LoggerFactory.getLogger(BankServerImpl.class);

    public void createCustomer(Customer customer) throws BankServerException {
        if(Objects.equals(customer.getFirstName(), "Johannes")){
            throw new BankServerException("Johannes du Pisser", BankServerExceptionType.SESSION_FAULT);
        }
    }
}
