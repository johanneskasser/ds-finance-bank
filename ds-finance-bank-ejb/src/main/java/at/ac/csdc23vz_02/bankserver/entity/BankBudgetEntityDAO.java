package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BankBudgetEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    public void persist() {
        List<BankBudgetEntity> budgetfromBank = entityManager.createQuery("select b from BankBudgetEntity b where b.id = 1", BankBudgetEntity.class)
                .getResultList();
        if(budgetfromBank.isEmpty()) {
            entityManager.persist(new BankBudgetEntity(1, 1000000000.0));
        }
    }

    public void deductBudget(double value) {
        entityManager.createQuery("UPDATE BankBudgetEntity b set b.budget = (b.budget - :value) where b.id = 1")
                .setParameter("value", value)
                .executeUpdate();
    }

    public void addBudget(double value) {
        entityManager.createQuery("UPDATE BankBudgetEntity b set b.budget = (b.budget + :value) where b.id = 1")
                .setParameter("value", value)
                .executeUpdate();
    }

    public double getAvailableBudget() {
        List<BankBudgetEntity> budgetEntities = entityManager.createQuery("select a from BankBudgetEntity a where a.id = 1", BankBudgetEntity.class)
                .getResultList();
        return budgetEntities.get(0).getBudget();
    }
}
