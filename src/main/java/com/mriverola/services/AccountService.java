package com.mriverola.services;


import com.mriverola.enums.TransactionType;
import com.mriverola.exceptions.DatabaseException;
import com.mriverola.exceptions.UserException;
import com.mriverola.jpa.entities.Account;
import com.mriverola.jpa.entities.User;
import com.mriverola.jpa.repositories.AccountRepository;
import com.mriverola.jpa.repositories.UserRepository;
import com.mriverola.services.interfaces.AccountServiceI;
import com.mriverola.services.interfaces.TransactionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService implements AccountServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionServiceI transactionService;

    /**
     *
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor={DatabaseException.class})
    public void openAccount(int userId, double amount) throws UserException, DatabaseException {

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Account newAccount = null;

            try {
                newAccount = accountRepository.save(new Account(user.get(), amount));
            } catch (Exception e) {
                throw new DatabaseException(e.getMessage());
            }

            if(newAccount != null && amount != 0){
                transactionService.createTransactionWithoutUpdatingBalanceToAccount(newAccount,amount);
            }
        } else {
            throw new UserException("The user does not exist");
        }

    }



}
