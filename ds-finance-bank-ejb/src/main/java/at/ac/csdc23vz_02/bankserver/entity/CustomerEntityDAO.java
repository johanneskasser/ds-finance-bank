package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Customer;
import at.ac.csdc23vz_02.common.Person;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import at.ac.csdc23vz_02.common.exceptions.BankServerExceptionType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomerEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    public void persist(CustomerEntity customerEntity) throws BankServerException {
        List<CustomerEntity> customerEntities = findByUsername(customerEntity.getUserName());
        if(customerEntities.isEmpty()) {
            entityManager.persist(customerEntity);
        } else {
            throw new BankServerException("User already exists!", BankServerExceptionType.DATABASE_FAULT);
        }
    }

    public List<CustomerEntity> findByUsername(String userName) {
        return entityManager.createQuery("SELECT p from CustomerEntity p where p.userName LIKE :userName", CustomerEntity.class)
                .setParameter("userName", userName).getResultList();
    }

    public void updateUserByUsername(Person person) {
        entityManager.createQuery("UPDATE CustomerEntity c SET c.firstName = :firstname, c.lastName = :lastname where c.userName like :username")
                .setParameter("firstname", person.getFirstName())
                .setParameter("lastname", person.getLastName())
                .setParameter("username", person.getUserName())
                .executeUpdate();
    }

    public List<CustomerEntity> findbyID(int id) {
        return entityManager.createQuery("select p from CustomerEntity p where p.ID = :id", CustomerEntity.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<CustomerEntity> findByName(String firstname, String lastname) {
        return entityManager.createQuery("SELECT p from CustomerEntity p where p.firstName LIKE :firstname AND p.lastName LIKE :lastname", CustomerEntity.class)
                .setParameter("firstname", firstname)
                .setParameter("lastname", lastname)
                .getResultList();
    }
}
