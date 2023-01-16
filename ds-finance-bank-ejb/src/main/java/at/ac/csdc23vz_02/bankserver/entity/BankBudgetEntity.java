package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Creates the table with the table name BankBudget and the corresponding entitites contained in that table
 * It also specifies the columns for the table
 */
@Entity
@Table(name = "BankBudget")
public class BankBudgetEntity implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    /**
     * Column for ID
     */
    @Id
    private Integer id;

    /**
     * Column for Budget
     */
    @Column(name = "Budget")
    private double budget;

    /**
     * Constructor for BankBudgetEntity
     * @param id id for the entity
     * @param budget budget for the entity
     */
    public BankBudgetEntity(int id, double budget) {
        this.id = id;
        this.budget = budget;
    }

    /**
     * Constructor
     */
    public BankBudgetEntity() {

    }

    /**
     * Method to get the budget of a BankBudgetEntity
     * @return returns the budget from the database
     */
    public double getBudget() {
        return this.budget;
    }


}
