package at.ac.csdc23vz_02.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Transaction implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    private Integer ID; /** the unique ID for the Transaction */

    private Integer customerID; /** the unique ID for the customer */

    private String stocksymbol; /** unique stocksymbol */

    private String companyName; /** name of the Company */
    private Integer shareCount; /** the quantity of stockshare that the customer owns */
    private Date tradeTime; /** the trading time */
    private BigDecimal buyPrice; /** the price where the stock share is bought */


    /**
     * Constructor
     * @param ID the unique ID for the Transaction
     * @param customerID the unique ID for the customer
     * @param stocksymbol unique stocksymbol
     * @param companyName name of the Company
     * @param shareCount the quantity of stockshare that the customer owns
     * @param tradeTime the trading time
     * @param buyPrice the price where the stock share is bought
     */
    public Transaction(Integer ID, Integer customerID, String stocksymbol, String companyName, Integer shareCount, Date tradeTime, BigDecimal buyPrice) {
        this.ID = ID;
        this.customerID = customerID;
        this.stocksymbol = stocksymbol;
        this.companyName = companyName;
        this.shareCount = shareCount;
        this.tradeTime = tradeTime;
        this.buyPrice = buyPrice;
    }


    /**
     * Used to get the unique ID for the Transaction
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Used to set the unique ID for the Transaction
     */

    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Used to get the unique ID for the customer
     */

    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * Used to set the unique ID for the customer
     */

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    /**
     * Used to get the unique stocksymbol
     */

    public String getStocksymbol() {
        return stocksymbol;
    }

    /**
     * Used to set the unique stocksymbol
     */

    public void setStocksymbol(String stocksymbol) {
        this.stocksymbol = stocksymbol;
    }

    /**
     * Used to get the name of the Company
     */

    public String getCompanyName() {
        return companyName;
    }

    /**
     * Used to set name of the Company
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Used to get the quantity of stockshare that the customer owns
     */

    public Integer getShareCount() {
        return shareCount;
    }

    /**
     * Used to set the quantity of stockshare that the customer owns
     */

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    /**
     * Used to get the trading time
     */


    public Date getTradeTime() {
        return tradeTime;
    }

    /**
     * Used to set the trading time
     */


    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * Used to get the price where the stock share is bought
     */


    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    /**
     * Used to set the price where the stock share is bought
     */


    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }
}
