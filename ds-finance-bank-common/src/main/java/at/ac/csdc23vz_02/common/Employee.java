package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Employee extends Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;


    public Employee(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    public Employee(Person person) {
        super(person.getFirstName(), person.getLastName(), person.getUserName(), person.getPassword());
    }

    public Employee() {
        super();
    }

    public void setPerson(Person person) {
        setUserName(person.getUserName());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setPassword(person.getPassword());
    }

}
