package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Customer extends Person implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;

    public Customer(){
        super();
    }

    public Customer(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
    }
}
