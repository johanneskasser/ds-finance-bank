package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Transaction;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import at.ac.csdc23vz_02.common.exceptions.BankServerExceptionType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class TransactionEntityDAO {
    @PersistenceContext private EntityManager entityManager;

        public void persist(TransactionEntity transactionEntity) {
            System.out.println("ID " + transactionEntity.getCustomerID());
            System.out.println(transactionEntity.getStockSymbol());
            List<TransactionEntity> transactionEntities = getTransactionsByStockSymbolAndID(transactionEntity.getStockSymbol(), transactionEntity.getCustomerID());
            if(transactionEntities.isEmpty()) {
                entityManager.merge(transactionEntity);
            } else {
                entityManager.createQuery("UPDATE TransactionEntity t set t.shareCount = :newShareCount where t.stockSymbol = :stockSymbol and t.customerID = :customerID")
                        .setParameter("newShareCount", transactionEntities.get(0).getShareCount() + transactionEntity.getShareCount())
                        .setParameter("stockSymbol", transactionEntity.getStockSymbol())
                        .setParameter("customerID", transactionEntity.getCustomerID())
                        .executeUpdate();
            }
        }

        public List<TransactionEntity> getTransactionsByID(int customerID) {
            return entityManager.createQuery("SELECT t from TransactionEntity t where t.customerID = :id", TransactionEntity.class)
                    .setParameter("id", customerID)
                    .getResultList();
        }

        public List<TransactionEntity> getTransactionsByStockSymbolAndID(String stockSymbol, int customer_id) {
            List<TransactionEntity> transactionEntities = new ArrayList<>();
            transactionEntities = entityManager.createQuery("SELECT t from TransactionEntity t where t.stockSymbol = :stockSymbol and t.customerID = :customerID", TransactionEntity.class)
                    .setParameter("stockSymbol", stockSymbol)
                    .setParameter("customerID", customer_id)
                    .getResultList();
            return transactionEntities;
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
