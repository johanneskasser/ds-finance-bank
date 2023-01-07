package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;

@Entity
@Table(name="DepotEntry")
public class DepotEntryEntity implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    @Id
    @Column(name = "DepotID")
    private Integer DepotID;

    @Id
    @Column(name = "customerID")
    private Integer customerID;

    @Column(name = "stocksymbol")
    private String stocksymbol;

    @Column(name = "amount")
    private Integer amount;

    public DepotEntryEntity(Integer depotID, Integer customerID, String stocksymbol, Integer amount) {
        DepotID = depotID;
        this.customerID = customerID;
        this.stocksymbol = stocksymbol;
        this.amount = amount;
    }

    public DepotEntryEntity() {

    }




    public Integer getDepotID() {
        return DepotID;
    }

    public void setDepotID(Integer depotID) {
        DepotID = depotID;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
