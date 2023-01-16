package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Customer;
import at.ac.csdc23vz_02.common.Stock;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


/**
 * Creates the table with the table name Transaction and the corresponding entitites contained in that table
 * It also specifies the columns for the table
 */
@Entity
@Table(name="Transaction")
public class TransactionEntity implements Serializable {
    private static final long serialVersionUID = -558553967080513790L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) /** makes the ID unique */
    /**
     * Column for ID
     */
    private Integer ID;

    /**
     * Column for costumer ID
     */
    @Column(name = "customerID")
    private Integer customerID;

    /**
     * Column for Stocksymbol
     */
    @Column(name = "StockSymbol")
    private String stockSymbol;
    /**
     * Column for CompanyName
     */
    @Column(name = "CompanyName")
    private String companyName;
    /**
     * Column for ShareCount
     */
    @Column(name = "ShareCount")
    private Integer shareCount;
    /**
     * Column for TradeTime
     */
    @Column(name = "TradeTime")
    private Date tradeTime;
    /**
     * Column for BuyPrice
     */
    @Column(name = "BuyPrice")
    private BigDecimal buyPrice;


    /**
     * Constructor for TransactionEntity
     * @param customerID the unique ID for the customer
     * @param stockSymbol unique stocksymbol
     * @param companyName name of the Company
     * @param shareCount the quantity of stockshare that the customer owns
     * @param tradeTime the trading time
     */
    public TransactionEntity(Integer customerID, String stockSymbol, String companyName, Integer shareCount, Date tradeTime, String tradeType) {
        this.customerID = customerID;
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.shareCount = shareCount;
        this.tradeTime = tradeTime;
    }

    /**
     * Constructor
     */
    public TransactionEntity() {

    }

    /**
     * Constructor for TransactionEntity
     * @param stock stock contained in transaction
     * @param customer customer for the customer
     * @param shareCount Number of shares bought
     */
    public TransactionEntity(Stock stock, Customer customer, int shareCount) {
        this.customerID = customer.getId();
        this.stockSymbol = stock.getSymbol();
        this.companyName = stock.getCompanyName();
        this.shareCount = shareCount;
        this.tradeTime = stock.getLastTradeTime();
    }
    /**
     * Method to get BuyPrice of a TransactionEntity
     * @return returns buyPrice from the database
     */
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }
    /**
     * Method to set BuyPrice of a TransactionEntity
     * @return returns buyPrice from the database
     */
    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }
    /**
     * Method to get ID of a TransactionEntity
     * @return returns ID from the database
     */
    public Integer getID() {
        return ID;
    }
    /**
     * Method to set ID of a TransactionEntity
     * @return returns ID from the database
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }
    /**
     * Method to get CostumerID of a TransactionEntity
     * @return returns costumerID from the database
     */
    public Integer getCustomerID() {
        return customerID;
    }
    /**
     * Method to set CostumerID of a TransactionEntity
     * @return returns costumerID from the database
     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
    /**
     * Method to get StockSymbol of a TransactionEntity
     * @return returns stocksymbol from the database
     */
    public String getStockSymbol() {
        return stockSymbol;
    }
    /**
     * Method to set StockSymbol of a TransactionEntity
     * @return returns stockSymbol from the database
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    /**
     * Method to get CompanyName of a TransactionEntity
     * @return returns companyName from the database
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * Method to set CompanyName of a TransactionEntity
     * @return returns companyName from the database
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * Method to get ShareCount of a TransactionEntity
     * @return returns shareCount from the database
     */
    public Integer getShareCount() {
        return shareCount;
    }
    /**
     * Method to set ShareCount of a TransactionEntity
     * @return returns shareCount from the database
     */
    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }
    /**
     * Method to get getTradeTime of a TransactionEntity
     * @return returns tradeTime from the database
     */
    public Date getTradeTime() {
        return tradeTime;
    }
    /**
     * Method to set TradeTime of a TransactionEntity
     * @return returns tradeTime from the database
     */
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

}
