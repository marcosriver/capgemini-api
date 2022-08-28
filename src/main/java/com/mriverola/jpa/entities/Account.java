package com.mriverola.jpa.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity(name="account")
public class Account {

    @JsonIgnore
    @Id
    @GeneratedValue
    private int accountId;

    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @NotNull
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
