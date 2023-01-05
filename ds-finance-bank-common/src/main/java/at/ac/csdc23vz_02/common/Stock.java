package at.ac.csdc23vz_02.common;


import java.io.Serializable;
import java.util.Date;

public class Stock implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    private String companyName;
    private Double lastTradePrice;
    private Date lastTradeTime;
    private Long marketCapitalization;
    private String stockExchange;
    private String symbol;

    public Stock(String companyName, Double lastTradePrice, Date lastTradeTime, Long marketCapitalization, String stockExchange, String symbol) {
        this.companyName = companyName;
        this.lastTradePrice = lastTradePrice;
        this.lastTradeTime = lastTradeTime;
        this.marketCapitalization = marketCapitalization;
        this.stockExchange = stockExchange;
        this.symbol = symbol;
    }

    public Stock() {
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getLastTradePrice() {
        return lastTradePrice;
    }

    public void setLastTradePrice(Double lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Date lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public Long getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(Long marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return "Company Name: " + this.getCompanyName() + "\n" +
                "Symbol: " + this.getSymbol() + "\n" +
                "Last Trade Price: " + this.getLastTradePrice().toString() + "\n" +
                "Last Trade Time: " + this.getLastTradeTime().toString() + "\n" +
                "Market Capitalization: " + this.getMarketCapitalization().toString();
    }
}