package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Employee;
import at.ac.csdc23vz_02.common.Person;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Employee")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer ID;

    @Column(name="firstName")
    private String firstName;

    @Column(name="userName")
    private String userName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="pwHash")
    private String pwHash;

    @Column(name = "salt")
    private String salt;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Employee employee) {
        this.firstName = employee.getFirstName();
        this.userName = employee.getUserName();
        this.lastName = employee.getLastName();
        this.pwHash = employee.getPassword();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
}
