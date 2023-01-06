package at.ac.csdc23vz_02.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Transaction implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    private Integer ID;
    private Integer customerID;
    private String stocksymbol;
    private String companyName;
    private Integer shareCount;
    private Date tradeTime;
    private BigDecimal buyPrice;

    public Transaction(Integer ID, Integer customerID, String stocksymbol, String companyName, Integer shareCount, Date tradeTime, BigDecimal buyPrice) {
        this.ID = ID;
        this.customerID = customerID;
        this.stocksymbol = stocksymbol;
        this.companyName = companyName;
        this.shareCount = shareCount;
        this.tradeTime = tradeTime;
        this.buyPrice = buyPrice;
    }


    public Transaction() {
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

    public String getStocksymbol() {
        return stocksymbol;
    }

    public void setStocksymbol(String stocksymbol) {
        this.stocksymbol = stocksymbol;
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

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }
}
