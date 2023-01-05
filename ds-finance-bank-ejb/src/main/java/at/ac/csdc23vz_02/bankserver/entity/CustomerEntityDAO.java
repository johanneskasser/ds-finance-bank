package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomerEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    public void persist(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
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
}
