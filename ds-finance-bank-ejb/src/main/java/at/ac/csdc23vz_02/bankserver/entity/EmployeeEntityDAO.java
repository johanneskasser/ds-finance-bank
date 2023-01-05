package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    public void persist(EmployeeEntity employeeEntity) {
        entityManager.persist(employeeEntity);
    }

    public List<EmployeeEntity> findByUsername(String userName) {
        return entityManager.createQuery("SELECT p from EmployeeEntity p where p.userName like :userName", EmployeeEntity.class)
                .setParameter("userName", userName).getResultList();
    }

    public void updateUserByUsername(Person person) {
        EmployeeEntity employee = new EmployeeEntity(person);
        entityManager.merge(employee);
    }


}
