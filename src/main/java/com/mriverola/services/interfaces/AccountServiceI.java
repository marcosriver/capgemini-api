package com.mriverola.services.interfaces;

import com.mriverola.enums.TransactionType;
import com.mriverola.exceptions.DatabaseException;
import com.mriverola.exceptions.UserException;
import com.mriverola.jpa.entities.Account;
import org.springframework.transaction.annotation.Transactional;

public interface AccountServiceI {

    /**
     *
     * This methods allows to create a new account with an initial balance
     *
     * @param userId The userId that the account will be related to
     * @param amount The inital amount of the account
     * @throws UserException
     */
    @Transactional(rollbackFor={UserException.class})
    void openAccount(int userId, double amount) throws UserException, DatabaseException;


}
