package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Customer extends Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    public Customer(){
        super();
    }

    public Customer(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    public Customer(Person person) {
        super(person.getFirstName(), person.getLastName(), person.getUserName(), person.getPassword());
    }

    public Customer(int ID, String firstName, String lastName, String userName) {
        super(firstName, lastName, userName, null);
        super.setId(ID);
    }


    public void setPerson(Person person) {
        setUserName(person.getUserName());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setPassword(person.getPassword());
    }
}
