package at.ac.csdc23vz_02.bankserver.entity;

import at.ac.csdc23vz_02.common.Customer;

import javax.persistence.*;

@Entity
@Table(name="Customer")
public class CustomerEntity {
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

    public CustomerEntity(Customer customer){
        this.ID = customer.getId();
        this.firstName = customer.getFirstName();
        this.userName = customer.getUserName();
        this.lastName = customer.getLastName();
        this.pwHash = customer.getPassword();
    }

    public CustomerEntity() {

    }
}
