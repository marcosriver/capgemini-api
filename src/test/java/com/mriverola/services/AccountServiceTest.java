package com.mriverola.services;

import com.mriverola.exceptions.DatabaseException;
import com.mriverola.exceptions.UserException;
import com.mriverola.jpa.entities.Account;
import com.mriverola.jpa.entities.User;
import com.mriverola.jpa.repositories.AccountRepository;
import com.mriverola.jpa.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    public AccountServiceTest(){

    }

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountService accountService;

    @Before
    public void setup() {

        doReturn(Optional.ofNullable(new User("Marcos","Riverola")))
                .when(this.userRepository).findById(1);

        doReturn(new Account(new User("Marcos","Riverola"),100.0))
                .when(this.accountRepository).save(any());

        doReturn(Optional.empty())
                .when(this.userRepository).findById(2);

    }


    @Test
    public void Open_account_is_correct_with_existing_user_and_inital_amount() throws UserException, DatabaseException {
        this.accountService.openAccount(1,100.0);
        verify(userRepository,times(1)).findById(any());
        verify(accountRepository, times(1)).save(any());
        verify(transactionService, times(1))
                .createTransactionWithoutUpdatingBalanceToAccount(any(), anyDouble());

    }

    @Test
    public void Open_account_is_correct_with_existing_user_and_inital_amount_is_0() throws UserException, DatabaseException {
        accountService.openAccount(1,0);
        verify(userRepository,times(1)).findById(anyInt());
        verify(accountRepository, times(1)).save(any());
        verify(transactionService, never())
                .createTransactionWithoutUpdatingBalanceToAccount(any(), anyDouble());

    }


    @Test
    public void Open_account_fails_when_user_not_found() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> accountService.openAccount(2,100.0));
        String expectedMessage = "The user does not exist";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }



}