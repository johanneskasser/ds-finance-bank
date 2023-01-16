package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import at.ac.csdc23vz_02.common.exceptions.BankServerExceptionType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Methods that are needed to communicate with the database
 */
public class TransactionEntityDAO {
    @PersistenceContext private EntityManager entityManager;

    /**
     * Method to make the TransactionEntity persistent
     * @param transactionEntity entity to be made persistent
     */
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

    /**
     * Method to get the transaction by ID
     * @param customerID ID to be searched for
     * @return returns the transaction containing this ID
     */
    public List<TransactionEntity> getTransactionsByID(int customerID) {
            return entityManager.createQuery("SELECT t from TransactionEntity t where t.customerID = :id", TransactionEntity.class)
                    .setParameter("id", customerID)
                    .getResultList();
        }

    /**
     * Method to get the transaction by the symbol and id
     * @param stockSymbol stock symbol to be searched for
     * @param customer_id customer id to be searched for
     * @return returns the transaction containing this id and symbol
     */
        public List<TransactionEntity> getTransactionsByStockSymbolAndID(String stockSymbol, int customer_id) {
            List<TransactionEntity> transactionEntities = new ArrayList<>();
            transactionEntities = entityManager.createQuery("SELECT t from TransactionEntity t where t.stockSymbol = :stockSymbol and t.customerID = :customerID", TransactionEntity.class)
                    .setParameter("stockSymbol", stockSymbol)
                    .setParameter("customerID", customer_id)
                    .getResultList();
            return transactionEntities;
        }

    /**
     * Method to sell a bought share
     * @param id id of the transaction
     * @param sharesToSell Number of shares to be sold
     * @return returns either true or false. Worked return true, didnt work return false
     * @throws BankServerException is thrown if there are is an invalid number of shares sold
     */
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
