package com.mriverola.jpa.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private double amount;

    @JsonManagedReference
    @OneToMany
    @JoinColumn(name="account_id")
    private List<Transaction> transactions;

    protected Account(){

    }
    public Account(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addValueToBalance(double amountToAdd){
        this.amount += amount;
    }

    public void substractValueToBalance(double valueToSubstract){
        this.amount -= valueToSubstract;
    }
}
