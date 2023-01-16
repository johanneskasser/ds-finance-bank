package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Class that contains the methods needed to communicate with the database
 */
public class BankBudgetEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    /**
     * Method to make a created BankBudgetEntity persistent
     */
    public void persist() {
        List<BankBudgetEntity> budgetfromBank = entityManager.createQuery("select b from BankBudgetEntity b where b.id = 1", BankBudgetEntity.class)
                .getResultList();
        if(budgetfromBank.isEmpty()) {
            entityManager.persist(new BankBudgetEntity(1, 1000000000.0));
        }
    }

    /**
     * Method to deduct from the column budget when something is bought
     * @param value value to be deducted
     */
    public void deductBudget(double value) {
        entityManager.createQuery("UPDATE BankBudgetEntity b set b.budget = (b.budget - :value) where b.id = 1")
                .setParameter("value", value)
                .executeUpdate();
    }

    /**
     * Method to add to the column budget when something is sold
     * @param value value to be added
     */
    public void addBudget(double value) {
        entityManager.createQuery("UPDATE BankBudgetEntity b set b.budget = (b.budget + :value) where b.id = 1")
                .setParameter("value", value)
                .executeUpdate();
    }

    /**
     * Method to get the available budget
     * @return returns the available budget
     */
    public double getAvailableBudget() {
        List<BankBudgetEntity> budgetEntities = entityManager.createQuery("select a from BankBudgetEntity a where a.id = 1", BankBudgetEntity.class)
                .getResultList();
        return budgetEntities.get(0).getBudget();
    }
}
