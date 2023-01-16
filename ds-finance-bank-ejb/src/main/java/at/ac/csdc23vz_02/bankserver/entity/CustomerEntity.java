package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Customer;
import at.ac.csdc23vz_02.common.Person;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class that creates the customer table in the database
 */
@Entity
@Table(name="Customer")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    /**
     * Creates ID Column
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer ID;

    /**
     * Creates first name column
     */
    @Column(name="firstName")
    private String firstName;

    /**
     * Creates username column
     */
    @Column(name="userName")
    private String userName;

    /**
     * Creates last name column
     */
    @Column(name="lastName")
    private String lastName;

    /**
     * Creates password hash column
     */
    @Column(name="pwHash")
    private String pwHash;

    /**
     * Creates a column that contains the salt for hashing
     */
    @Column(name = "salt")
    private String salt;

    /**
     * Creates ZIP Code column
     */
    @Column(name = "zip")
    private String zip;

    /**
     * Creates country column
     */
    @Column(name = "country")
    private String country;

    /**
     * Creates street column
     */
    @Column(name = "street")
    private String street;

    /**
     * Creates number column for the address
     */
    @Column(name = "number")
    private String number;

    /**
     * Creates city column
     */
    @Column(name = "city")
    private String city;

    /**
     * Constructor for CustomerEntity
     * @param customer customer to be created
     */
    public CustomerEntity(Customer customer){
        this.firstName = customer.getFirstName();
        this.userName = customer.getUserName();
        this.lastName = customer.getLastName();
        this.pwHash = customer.getPassword();
        this.zip = customer.getZip();
        this.country = customer.getCountry();
        this.city = customer.getCity();
        this.street = customer.getStreet();
        this.number = customer.getNumber();
    }
    /**
     * Constructor
     */
    public CustomerEntity() {

    }

    /**
     * Method to get the Salt used for password hashing
     * @return returns the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Method to set the salt for password hashing
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Method to get the ID from the table
     * @return returns the ID
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Method to set the ID
     * @param ID ID that is to be set
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Method to get the first name
     * @return returns the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method to set the first name
     * @param firstName firstname to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method to get the username
     * @return returns the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method to set the username
     * @param userName username to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method to get the last name
     * @return returns the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method to set the last name
     * @param lastName last name to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method to get the hashed password
     * @return returns the hashed password
     */
    public String getPwHash() {
        return pwHash;
    }

    /**
     * Method to set the hashed password
     * @param pwHash hashed password to be set
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    /**
     * Method to get the ZIP code
     * @return returns the ZIP code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Method to set the ZIP code
     * @param zip ZIP code to be set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Method to get the country
     * @return returns country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method to set the country
     * @param country Country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method to get the street
     * @return returns the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Method to set the street
     * @param street street to be set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Method to get the number
     * @return returns the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Method to set the number
     * @param number number to be set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Method to get the city
     * @return returns the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Method to set the city
     * @param city city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }
}
