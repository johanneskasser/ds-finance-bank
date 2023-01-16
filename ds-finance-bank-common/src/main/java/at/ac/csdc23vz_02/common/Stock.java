package at.ac.csdc23vz_02.common;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** The class Stock represents a stock object. It stores private fields
 *  for storing information about the stock, such as the company name.
 *  The constructor that takes in these values and assigns them to the
 *  corresponding fields of the class. The class also has a toString() method
 *  which returns a human-readable string representation of the stock object.
 */
public class Stock implements Serializable {

    /** serialVersionUID is used for versioning during deserialization*/
    private static final long serialVersionUID = -558553967080513790L;

    /** Private fields are created here to passed them to the constructor*/
    private String companyName;
    private Double lastTradePrice;
    private Date lastTradeTime;
    private Long marketCapitalization;
    private String stockExchange;
    private String symbol;

    /**
    The constructor for the Stock class. It assigns the parameters to the corresponding fields
    of the class using the 'this' keyword. The constructor is called when a new Stock object is created and
    it is used to initialize the fields of the object with the provided values.
     */

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

    /**getter method of companyName. It retrieves the current value of the companyName field.*/
    public String getCompanyName() {
        return companyName;
    }

    /**setter method of companyName. Updates the value of the companyName field.*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

     /**getter method of lastTradePrice. It retrieves the current value of the lastTradePrice field.*/
    public Double getLastTradePrice() {
        return lastTradePrice;
    }

     /**setter method of lastTradePrice. Updates the value of the lastTradePrice field.*/
    public void setLastTradePrice(Double lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    /**getter method of lastTradeTime. It retrieves the current value of the lastTradeTime field.*/
    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    /**setter method of lastTradeTime. Updates the value of the lastTradeTime field.*/
    public void setLastTradeTime(Date lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    /**getter method of marketCapitalization. It retrieves the current value of the marketCapitalization field.*/
    public Long getMarketCapitalization() {
        return marketCapitalization;
    }

    /**setter method of marketCapitalization. Updates the value of the marketCapitalization field.*/
    public void setMarketCapitalization(Long marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    /**getter method of stockExchange. It retrieves the current value of the stockExchange field.*/
    public String getStockExchange() {
        return stockExchange;
    }

    /**setter method of stockExchange. Updates the value of the stockExchange field.*/
    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    /**getter method of symbol. It retrieves the current value of the symbol field.*/
    public String getSymbol() {
        return symbol;
    }

    /**setter method of symbol. Updates the value of the symbol field.*/
    public void setSymbol(String symbol) { this.symbol = symbol; }

    /**
    With the getter methods from above, which get the values of the fields, the toString() method
    returns a String. It displays the contents of the objects in a human-readable format.
     */
    public String toString() {
        return "Company Name: " + this.getCompanyName() + "\n" +
                "Symbol: " + this.getSymbol() + "\n" +
                "Last Trade Price: €" + this.getLastTradePrice().toString() + "\n" +
                "Last Trade Time: " + this.getLastTradeTime().toString() + "\n" +
                "Market Capitalization: " + this.getMarketCapitalization().toString();
    }
}