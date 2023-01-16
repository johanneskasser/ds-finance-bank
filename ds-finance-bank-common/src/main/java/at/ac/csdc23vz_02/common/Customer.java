package at.ac.csdc23vz_02.common;

import java.io.Serializable;

/**
 * This Class extends the Person class
 * It is used to store a Customer Object with all its attributes
 */
public class Customer extends Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    /**
     * Constructor
     * gets all the Information from the Person Constructor
     */
    public Customer(){
        super();
    }

    /**
     * Constructor
     * @param firstName Firstname of the Customer
     * @param lastName Lastname of the Customer
     * @param userName Username of the Customer
     * @param password Passwort of the Customer
     */
    public Customer(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    /**
     * Constructor
     * @param person the Person Object to define the Customer Object
     */
    public Customer(Person person) {
        super(person.getId(), person.getFirstName(), person.getLastName(), person.getUserName(), person.getPassword());
    }

    /**
     * Constructor
     * @param ID ID of the Customer
     * @param firstName Firstname of the Customer
     * @param lastName Lastname of the Customer
     * @param userName Username of the Customer
     */
    public Customer(int ID, String firstName, String lastName, String userName) {
        super(firstName, lastName, userName, null);
        super.setId(ID);
    }

    /**
     * Constructor
     * @param ID ID of the Customer
     * @param firstName Firstname of the Customer
     * @param lastName Lastname of the Customer
     * @param userName Username of the Customer
     * @param password Passwort of the Customer
     */
    public Customer(int ID, String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
        super.setId(ID);
    }

    /**
     * Used to set the Customer Attributes based on a Person Object
     * @param person the Person Object to define the Customer Attributes
     */
    public void setPerson(Person person) {
        setUserName(person.getUserName());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setPassword(person.getPassword());
    }
}
