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
                .setParameter("userName", userName)
                .getResultList();
    }

    public void updateUserByUsername(Person person) {
        entityManager.createQuery("UPDATE EmployeeEntity e SET e.firstName = :firstname, e.lastName = :lastname, e.pwHash = :pwhash  where e.userName like :username")
                .setParameter("firstname", person.getFirstName())
                .setParameter("lastname", person.getLastName())
                .setParameter("username", person.getUserName())
                .setParameter("pwhash", person.getPassword())
                .executeUpdate();
    }


}
