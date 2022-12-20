package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomerEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    public void persist(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
    }

    public List<CustomerEntity> findByUsername(String userName) {
        List<CustomerEntity> customers = entityManager.createQuery("SELECT p from CustomerEntity p where p.userName LIKE :userName", CustomerEntity.class)
                .setParameter("userName", userName).getResultList();
        return customers;
    }
}
