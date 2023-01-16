package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Employee;
import at.ac.csdc23vz_02.common.Person;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class that creates the table for employees
 */
@Entity
@Table(name="Employee")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    /**
     * Column for ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer ID;

    /**
     * Column for first name
     */
    @Column(name="firstName")
    private String firstName;

    /**
     * Column for username
     */
    @Column(name="userName")
    private String userName;

    /**
     * Column for last name
     */
    @Column(name="lastName")
    private String lastName;

    /**
     * Column for hashed password
     */
    @Column(name="pwHash")
    private String pwHash;

    /**
     * Column for salt used for hashed password
     */
    @Column(name = "salt")
    private String salt;

    /**
     * Constructor
     */
    public EmployeeEntity() {
    }

    /**
     * Constructor for EmployeeEntity
     * @param employee employee to be created
     */
    public EmployeeEntity(Employee employee) {
        this.firstName = employee.getFirstName();
        this.userName = employee.getUserName();
        this.lastName = employee.getLastName();
        this.pwHash = employee.getPassword();
    }


    /**
     * Method to get salt
     * @return returns salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Method to set salt
     * @param salt salt to be set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Method to get ID
     * @return returns ID
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Method to set ID
     * @param ID ID to be set
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Method to get first name
     * @return returns first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method to set first name
     * @param firstName first name to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method to get username
     * @return returns username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method to set username
     * @param userName username to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method to get last name
     * @return returns last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method to set last name
     * @param lastName last name to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method to get hashed password
     * @return returns hashed password
     */
    public String getPwHash() {
        return pwHash;
    }

    /**
     * Method to set hashed password
     * @param pwHash hashed password to be set
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
}
