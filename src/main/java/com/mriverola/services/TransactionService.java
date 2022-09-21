package com.mriverola.services;

import com.mriverola.enums.TransactionType;
import com.mriverola.exceptions.DatabaseException;
import com.mriverola.jpa.entities.Account;
import com.mriverola.jpa.entities.Transaction;
import com.mriverola.jpa.repositories.AccountRepository;
import com.mriverola.jpa.repositories.TransactionRepository;
import com.mriverola.services.interfaces.TransactionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService implements TransactionServiceI {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    /**
     *
     * This methods allows to insert a new transaction into the database
     *
     * @param account The account that will contain the transaction
     * @param transactionType The transaction type
     * @param amount The amount to perform the operation with
     */
    private void createTransaction(Account account, TransactionType transactionType, double amount) throws DatabaseException {
        try {
            this.transactionRepository.save(new Transaction(account,transactionType,amount));

        } catch(Exception e){
            throw new DatabaseException(e.getMessage());
        }

    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = { DatabaseException.class })
    public void createTransactionAndUpdateBalanceToAccount(Account account, TransactionType transactionType, double amount) throws DatabaseException {
        createTransaction(account,transactionType,amount);
        updateBalance(account,transactionType,amount);
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public void createTransactionWithoutUpdatingBalanceToAccount(Account account, double amount) throws DatabaseException {
        createTransaction(account,TransactionType.ADD,amount);
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public void updateBalance(Account account, TransactionType transactionType, double amountToChange) throws DatabaseException {

        switch (transactionType){
            case ADD:
                account.addValueToBalance(amountToChange);
                break;
            case SUBSTRACT:
                account.substractValueToBalance(amountToChange);
        }

        try {
            this.accountRepository.save(account);
        } catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }


    }

}
