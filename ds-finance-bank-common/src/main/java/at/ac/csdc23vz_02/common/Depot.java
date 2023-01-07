package at.ac.csdc23vz_02.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Depot implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    private Integer DepotID;
    private Integer customerID;

    // 1 = Stock - 2 = Amount
    public HashMap<String,Integer> depot;


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
}
