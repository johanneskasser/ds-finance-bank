package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Stock;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="Transaction")
public class TransactionEntity implements Serializable {
    private static final long serialVersionUID = -558553967080513790L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer ID;

    @Column(name = "customerID")
    private Integer customerID;

    @Column(name = "StockSymbol")
    private String stockSymbol;

    @Column(name = "CompanyName")
    private String companyName;

    @Column(name = "ShareCount")
    private Integer shareCount;

    @Column(name = "TradeTime")
    private Date tradeTime;

    @Column(name = "BuyPrice")
    private BigDecimal buyPrice;

    public TransactionEntity(Integer customerID, String stockSymbol, String companyName, Integer shareCount, Date tradeTime, String tradeType) {
        this.customerID = customerID;
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.shareCount = shareCount;
        this.tradeTime = tradeTime;
    }

    public TransactionEntity() {

    }

    public TransactionEntity(Stock stock, CustomerEntity customer, int shareCount) {
        this.customerID = customer.getID();
        this.stockSymbol = stock.getSymbol();
        this.companyName = stock.getCompanyName();
        this.shareCount = shareCount;
        this.tradeTime = stock.getLastTradeTime();
    }

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
