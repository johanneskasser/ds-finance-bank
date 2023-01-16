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
     * Method to get the price where the stock share is bought
     * @return returns BuyPrice from the database
     */
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

}
