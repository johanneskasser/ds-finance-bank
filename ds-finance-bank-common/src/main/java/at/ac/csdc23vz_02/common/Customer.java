package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Customer extends Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;


    public Customer(){
        super();
    }

    public Customer(String firstName, String lastName, String userName, String password,  String zip, String country, String street, String number, String city) {
        super(firstName, lastName, userName, password, zip, country, street, number, city);
    }

    public Customer(Person person) {
        super(person.getId(), person.getFirstName(), person.getLastName(), person.getUserName(), person.getPassword(), person.getZip(), person.getCountry(), person.getStreet(), person.getNumber(), person.getCity());
    }

    public Customer(int ID, String firstName, String lastName, String userName, String zip, String country, String street, String number, String city) {
        super(firstName, lastName, userName, null, zip, country, street, number, city);
        super.setId(ID);
    }

    public Customer(int ID, String firstName, String lastName, String userName, String password, String zip, String country, String street, String number, String city) {
        super(firstName, lastName, userName, password, zip, country, street, number, city);
        super.setId(ID);
    }

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
