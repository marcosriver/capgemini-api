package com.mriverola.services.interfaces;

import com.mriverola.enums.TransactionType;
import com.mriverola.exceptions.DatabaseException;
import com.mriverola.jpa.entities.Account;

public interface TransactionServiceI {

    /**
     *
     * This method allows to insert a new transaction into the database and update the balance of the related account
     * depending on the type of transaction
     *
     *  @param account The account that will contain the transaction
     * @param transactionType The transaction type
     * @param amount The amount to perform the operation with
     */
    void createTransactionAndUpdateBalanceToAccount(Account account, TransactionType transactionType, double amount) throws DatabaseException;

    /**
     *
     * This method allows to insert a new transaction into the database without updating the balance of the related account
     *
     *  @param account The account that will contain the transaction
     * @param amount The amount to perform the operation with
     */
    void createTransactionWithoutUpdatingBalanceToAccount(Account account, double amount) throws DatabaseException;

    /**
     *
     * This method allows to update the balance of the account depending on the transaction type
     *
     * @param account The account that needs its balance updated
     * @param transactionType The transaction type operation to perform
     * @param amountToChange The amount to change depending on the transaction type
     */
    void updateBalance(Account account, TransactionType transactionType, double amountToChange) throws DatabaseException;
}
