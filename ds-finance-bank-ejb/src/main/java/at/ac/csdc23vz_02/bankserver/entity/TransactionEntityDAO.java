package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TransactionEntityDAO {
    @PersistenceContext private EntityManager entityManager;

        public void persist(TransactionEntity transactionEntity) {
            entityManager.merge(transactionEntity);
        }

        public List<TransactionEntity> getTransactionsByID(int customerID) {
            return entityManager.createQuery("SELECT t from TransactionEntity t where t.customerID = :id", TransactionEntity.class)
                    .setParameter("id", customerID)
                    .getResultList();
        }


}
