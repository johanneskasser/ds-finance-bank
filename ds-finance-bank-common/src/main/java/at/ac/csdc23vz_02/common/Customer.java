package at.ac.csdc23vz_02.common;

import java.io.Serializable;

/**
 * Class that contains the constructors for Customers
 */
public class Customer extends Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;


    /**
     * Constructor for Customer that gets the information from the person class
     */
    public Customer(){
        super();
    }

    /**
     * Constructor for Customer that calls a constructor from the person class
     * @param firstName first name of customer
     * @param lastName last name of customer
     * @param userName username of customer
     * @param password password of customer
     * @param zip ZIP code of customer
     * @param country country of customer
     * @param street street of customer
     * @param number street number of customer
     * @param city city of customer
     */
    public Customer(String firstName, String lastName, String userName, String password,  String zip, String country, String street, String number, String city) {
        super(firstName, lastName, userName, password, zip, country, street, number, city);
    }

    /**
     * Constructor of a customer with the same information as the given person
     * @param person person that the information is from
     */
    public Customer(Person person) {
        super(person.getId(), person.getFirstName(), person.getLastName(), person.getUserName(), person.getPassword(), person.getZip(), person.getCountry(), person.getStreet(), person.getNumber(), person.getCity());
    }

    /**
     * Constructor for Customer that calls a constructor from the person class
     * @param ID
     * @param firstName
     * @param lastName
     * @param userName
     * @param zip
     * @param country
     * @param street
     * @param number
     * @param city
     */
    public Customer(int ID, String firstName, String lastName, String userName, String zip, String country, String street, String number, String city) {
        super(userName, firstName, lastName, null, zip, country, street, number, city);
        super.setId(ID);
    }

    /**
     * Constructor for customer that calls a constructor from the person class
     * @param ID
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param zip
     * @param country
     * @param street
     * @param number
     * @param city
     */
    public Customer(int ID, String firstName, String lastName, String userName, String password, String zip, String country, String street, String number, String city) {
        super(userName, firstName, lastName, password, zip, country, street, number, city);
        super.setId(ID);
    }

    /**
     * Method to set the information of a person
     * @param person sets the attributes according to this value
     */
    public void setPerson(Person person) {
        setUserName(person.getUserName());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setPassword(person.getPassword());
        setZip(person.getZip());
        setCity(person.getCity());
        setNumber(person.getNumber());
        setStreet(person.getStreet());
        setCountry(person.getCountry());
    }
}
