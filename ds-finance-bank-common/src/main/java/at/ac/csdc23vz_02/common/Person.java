package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;


    /**
     * Unique identifier for the user
     */
    private Integer id;

    /**
     * User name for the user
     */
    private String userName;

    /**
     * First name of the user
     */
    private String firstName;

    /**
     * Last name of the user
     */
    private String lastName;

    /**
     * Password for the user's account
     */
    private String password;

    /**
     * Zip code of the user's address
     */
    private String zip;

    /**
     * Country of the user's address
     */
    private String country;

    /**
     * Street name of the user's address
     */
    private String street;

    /**
     * Street number of the user's address
     */
    private String number;

    /**
     * City of the user's address
     */
    private String city;


    /**
     * Constructor for creating a Person object without an id
     * @param firstName first name of the person
     * @param lastName last name of the person
     * @param userName username of the person
     * @param password password of the person
     */
    public Person(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
    }

    /**
     * Constructor for creating a Person object with an id
     * @param id unique identifier for the person
     * @param firstName first name of the person
     * @param lastName last name of the person
     * @param userName username of the person
     * @param password password of the person
     */
    public Person(int id, String firstName, String lastName, String userName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
    }
    /**
     * Constructor for creating a Person object with an id, username, first name, last name, password and address information
     * @param id unique identifier for the person
     * @param userName username of the person
     * @param firstName first name of the person
     * @param lastName last name of the person
     * @param password password of the person
     * @param zip zip code of the person's address
     * @param country country of the person's address
     * @param street street name of the person's address
     * @param number street number of the person's address
     * @param city city of the person's address
     */
    public Person(Integer id, String userName, String firstName, String lastName, String password, String zip, String country, String street, String number, String city) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.zip = zip;
        this.country = country;
        this.street = street;
        this.number = number;
        this.city = city;
    }

    /**
     * Constructor for creating a Person object with a username, first name, last name, password and address information
     * @param userName username of the person
     * @param firstName first name of the person
     * @param lastName last name of the person
     * @param password password of the person
     * @param zip zip code of the person's address
     * @param country country of the person's address
     * @param street street name of the person's address
     * @param number street number of the person's address
     * @param city city of the person's address
     */
    public Person(String userName, String firstName, String lastName, String password, String zip, String country, String street, String number, String city) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.zip = zip;
        this.country = country;
        this.street = street;
        this.number = number;
        this.city = city;
    }


    /**
     * Default constructor for the Person class
     */
    public Person(){
    }

    /**
     * Getter for the id field
     * @return the id of the person
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for the id field
     * @param id the new id for the person
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for the first name field
     * @return the first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name field
     * @param firstName the new first name for the person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the last name field
     * @return the last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the last name field
     * @param lastName the new last name for the person
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the password field
     * @return the password of the person
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password field
     * @param password the new password for the person
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the user name field
     * @return the user name of the person
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the user name field
     * @param userName the new user name for the person
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for the full name field
     * @return the full name of the person, concatenating the first name and last name fields
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Getter for the zip field
     * @return the zip code of the person
     */
    public String getZip() {
        return zip;
    }

    /**
     * Setter for the zip field
     * @param zip the new zip code for the person
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Getter for the country field
     * @return the country of the person
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for the country field
     * @param country the new country for the person
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for the street field
     * @return the street of the person
     */
    public String getStreet() {
        return street;
    }

    /**
     * Setter for the street field
     * @param street the new street for the person
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Getter for the number field
     * @return the number of the person
     */
    public String getNumber() {
        return number;
    }

    /**
     * Setter for the number field
     * @param number the new number for the person
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Getter for the city field
     * @return the city of the person
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for the city field
     * @param city the new city for the person
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person={\n" +
                "  firstName=" + this.firstName +
                "  \nlastName=" + this.lastName +
                "\n}";
    }
}
