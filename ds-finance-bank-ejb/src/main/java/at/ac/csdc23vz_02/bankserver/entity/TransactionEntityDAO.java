package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Transaction;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import at.ac.csdc23vz_02.common.exceptions.BankServerExceptionType;

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

        public boolean sellTransaction(int id, int sharesToSell) throws BankServerException {
            List<TransactionEntity> transactionEntities = entityManager.createQuery("select t from TransactionEntity t where t.id = :id", TransactionEntity.class)
                    .setParameter("id", id)
                    .getResultList();
            if(!transactionEntities.isEmpty()) {
                if(transactionEntities.get(0).getShareCount() > sharesToSell) {
                    int resultingShares = transactionEntities.get(0).getShareCount() - sharesToSell;
                    entityManager.createQuery("UPDATE TransactionEntity t set t.shareCount = :resultingShares where t.id = :id")
                            .setParameter("resultingShares", resultingShares)
                            .setParameter("id", id)
                            .executeUpdate();
                    return true;
                } else if(transactionEntities.get(0).getShareCount() == sharesToSell) {
                    TransactionEntity transactionEntity = entityManager.find(TransactionEntity.class, id);
                    entityManager.remove(transactionEntity);
                    entityManager.flush();
                    entityManager.clear();
                    return true;
                } else {
                    throw new BankServerException("Cannot Delete More Shares then existing in DB!", BankServerExceptionType.TRANSACTION_FAULT);
                }
            }
            return false;
        }


}
