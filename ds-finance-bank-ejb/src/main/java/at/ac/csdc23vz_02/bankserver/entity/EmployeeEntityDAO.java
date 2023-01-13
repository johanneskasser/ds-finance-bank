package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Person;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import at.ac.csdc23vz_02.common.exceptions.BankServerExceptionType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    public void persist(EmployeeEntity employeeEntity) throws BankServerException {
        List<EmployeeEntity> employeeEntities = findByUsername(employeeEntity.getUserName());
        if(employeeEntities.isEmpty()) {
            entityManager.persist(employeeEntity);
        } else {
            throw new BankServerException("User already exists!", BankServerExceptionType.DATABASE_FAULT);
        }
    }

    public List<EmployeeEntity> findByUsername(String userName) {
        return entityManager.createQuery("SELECT p from EmployeeEntity p where p.userName like :userName", EmployeeEntity.class)
                .setParameter("userName", userName)
                .getResultList();
    }

    public void updateUserByUsername(Person person, String salt) {
        entityManager.createQuery("UPDATE EmployeeEntity e SET e.firstName = :firstname, e.lastName = :lastname, e.pwHash = :pwHash, e.salt = :salt where e.userName like :username")
                .setParameter("firstname", person.getFirstName())
                .setParameter("lastname", person.getLastName())
                .setParameter("username", person.getUserName())
                .setParameter("pwHash", person.getPassword())
                .setParameter("salt", salt)
                .executeUpdate();
    }

    public List<EmployeeEntity> findbyID(int id) {
        return entityManager.createQuery("select p from EmployeeEntity p where p.ID = :id", EmployeeEntity.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void removeUserByID(int id) {
        List<EmployeeEntity> employeeEntities = findbyID(id);
        if(!employeeEntities.isEmpty()) {
            entityManager.createQuery("DELETE from EmployeeEntity c where c.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }


}
