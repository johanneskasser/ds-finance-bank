package at.ac.csdc23vz_02.common;

public class Customer extends Person {
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
