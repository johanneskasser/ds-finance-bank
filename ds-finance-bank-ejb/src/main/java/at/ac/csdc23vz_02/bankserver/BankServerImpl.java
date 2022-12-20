package at.ac.csdc23vz_02.bankserver;

import at.ac.csdc23vz_02.bankserver.entity.CustomerEntity;
import at.ac.csdc23vz_02.bankserver.entity.CustomerEntityDAO;
import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Stateless(name="BankServer")
@PermitAll
public class BankServerImpl implements BankServer {
    private static final Logger log = LoggerFactory.getLogger(BankServerImpl.class);
    @Inject CustomerEntityDAO customerEntityDAO;

    public void createCustomer(Customer customer) throws BankServerException {
        //TODO: Check Permissions of creating User!
        //TODO: Check if Username already exists!
        CustomerEntity customerEntity = new CustomerEntity(customer);
        customerEntityDAO.persist(customerEntity);


    }

    public boolean login(Customer customer) throws BankServerException {
        List<CustomerEntity> customerEntity = customerEntityDAO.findByUsername(customer.getUserName());
        if(customerEntity.get(0).getUserName().isEmpty()){
            throw new BankServerException("No Such User!", BankServerExceptionType.SESSION_FAULT);
        }
        return customerEntity.get(0).getPwHash().equals(customer.getPassword());
    }
}
