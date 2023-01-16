package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;


    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;

    private String zip;
    private String country;
    private String street;
    private String number;
    private String city;

    public Person(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
    }

    public Person(int id, String firstName, String lastName, String userName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
    }

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

    public Person(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

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
