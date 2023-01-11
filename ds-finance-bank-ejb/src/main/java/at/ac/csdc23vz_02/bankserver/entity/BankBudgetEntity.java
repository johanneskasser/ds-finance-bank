package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BankBudget")
public class BankBudgetEntity implements Serializable {

    private static final long serialVersionUID = -558553967080513790L;

    @Id
    private Integer id;

    @Column(name = "Budget")
    private double budget;

    public BankBudgetEntity(int id, double budget) {
        this.id = id;
        this.budget = budget;
    }

    public BankBudgetEntity() {

    }

    public double getBudget() {
        return this.budget;
    }


}
