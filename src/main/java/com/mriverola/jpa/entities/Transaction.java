package com.mriverola.jpa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mriverola.enums.TransactionType;
import com.sun.istack.NotNull;
import net.bytebuddy.utility.nullability.MaybeNull;

import javax.persistence.*;

@Entity(name="transaction")
public class Transaction {

    @JsonIgnore
    @Id
    @GeneratedValue
    private int transactionId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @Column(columnDefinition = "ENUM('ADD', 'SUBSTRACT')")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    private double amount;

    protected Transaction(){

    }

    public Transaction(Account account, TransactionType transactionType, double amount) {
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
