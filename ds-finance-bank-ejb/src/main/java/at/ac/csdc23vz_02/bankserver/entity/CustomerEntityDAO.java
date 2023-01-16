package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Customer;
import at.ac.csdc23vz_02.common.Person;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import at.ac.csdc23vz_02.common.exceptions.BankServerExceptionType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Class that contains the methods needed to communicate with the database
 */
public class CustomerEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    /**
     * Method to make the customerentity persistent
     * @param customerEntity customer entity that is made persistent
     * @throws BankServerException is thrown when the user already exists
     */
    public void persist(CustomerEntity customerEntity) throws BankServerException {
        List<CustomerEntity> customerEntities = findByUsername(customerEntity.getUserName());
        if(customerEntities.isEmpty()) {
            entityManager.persist(customerEntity);
        } else {
            throw new BankServerException("User already exists!", BankServerExceptionType.DATABASE_FAULT);
        }
    }

    /**
     * Method to find a user by username
     * @param userName username to be looked after
     * @return returns the customer with that username
     */
    public List<CustomerEntity> findByUsername(String userName) {
        return entityManager.createQuery("SELECT p from CustomerEntity p where p.userName LIKE :userName", CustomerEntity.class)
                .setParameter("userName", userName).getResultList();
    }

    /**
     * method to update a customers information
     * @param person person that needs to be updated
     * @param salt salt that is used for the password
     */
    public void updateUserByUsername(Person person, String salt) {
        entityManager.createQuery("UPDATE CustomerEntity c SET c.firstName = :firstname, c.lastName = :lastname, c.salt = :salt, c.pwHash = :pwHash where c.userName like :username")
                .setParameter("firstname", person.getFirstName())
                .setParameter("lastname", person.getLastName())
                .setParameter("username", person.getUserName())
                .setParameter("pwHash", person.getPassword())
                .setParameter("salt", salt)
                .executeUpdate();
    }

    /**
     * Method to find a customer by ID
     * @param id id to be looked for
     * @return returns the customer with that ID
     */
    public List<CustomerEntity> findbyID(int id) {
        return entityManager.createQuery("select p from CustomerEntity p where p.ID = :id", CustomerEntity.class)
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * Method to find a customer by name
     * @param firstname First name to be looked for
     * @param lastname Last name to be looked for
     * @return returns the customer with that name
     */
    public List<CustomerEntity> findByName(String firstname, String lastname) {
        return entityManager.createQuery("SELECT p from CustomerEntity p where p.firstName LIKE :firstname AND p.lastName LIKE :lastname", CustomerEntity.class)
                .setParameter("firstname", firstname)
                .setParameter("lastname", lastname)
                .getResultList();
    }

    /**
     * Method to remove a user by ID
     * @param id id of a customer to be removed
     */
    public void removeUserByID(int id) {
        List<CustomerEntity> customerEntities = findbyID(id);
        if(!customerEntities.isEmpty()) {
            entityManager.createQuery("DELETE from CustomerEntity c where c.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }
}
