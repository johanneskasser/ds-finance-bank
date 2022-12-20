package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Customer extends Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    public Customer(){
        super();
    }

    public Customer(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
    }
}
