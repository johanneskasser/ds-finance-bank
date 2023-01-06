package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TransactionEntityDAO {
    @PersistenceContext private EntityManager entityManager;

        public void persist(TransactionEntity transactionEntity) {
            entityManager.merge(transactionEntity);
        }


}
