package at.ac.csdc23vz_02.common;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;


    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;

    public Person(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
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

    @Override
    public String toString() {
        return "Person={\n" +
                "  firstName=" + this.firstName +
                "  \nlastName=" + this.lastName +
                "\n}";
    }
}
