package at.ac.csdc23vz_02.common;

import java.io.Serializable;

/**
 * This Class extends the Person class
 * It is used to store a Employee Object with all its attributes
 */
public class Employee extends Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;


    /**
     * Constructor
     * @param firstName Firstname of the Employee
     * @param lastName Lastname of the Employee
     * @param userName Username of the Employee
     * @param password Passwort of the Employee
     */
    public Employee(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    /**
     * Constructor
     * @param person the Person Object to define the Employee Object
     */
    public Employee(Person person) {
        super(person.getFirstName(), person.getLastName(), person.getUserName(), person.getPassword());
    }

    /**
     * Constructor
     * gets all the Information from the Person Constructor
     */
    public Employee() {
        super();
    }

    /**
     * Used to set the Employee Attributes based on a Person Object
     * @param person the Person Object to define the Employee Attributes
     */
    public void setPerson(Person person) {
        setUserName(person.getUserName());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setPassword(person.getPassword());
    }

}
