package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomerEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    public void persist(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
    }

    public CustomerEntity findByUsername(String userName) {
        return entityManager.find(CustomerEntity.class, userName);
    }
}
