package com.mriverola.services;

import com.mriverola.enums.TransactionType;
import com.mriverola.exceptions.DatabaseException;
import com.mriverola.jpa.entities.Account;
import com.mriverola.jpa.repositories.AccountRepository;
import com.mriverola.jpa.repositories.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {


    public TransactionServiceTest() {
    }

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;


    @Mock
    private Account account1;



    @Test
    public void Correct_transaction_and_without_updating_balance_with_correct_data() throws DatabaseException {
        this.transactionService.createTransactionWithoutUpdatingBalanceToAccount(this.account1,100.0);
        verify(this.transactionRepository,times(1))
                .save(any());

    }

    @Test
    public void Correct_transaction_and_updating_balance_with_correct_data() throws DatabaseException {
        this.transactionService.createTransactionAndUpdateBalanceToAccount(this.account1,TransactionType.ADD,100.0);
        verify(this.transactionRepository,times(1))
                .save(any());
        verify(this.accountRepository,times(1))
                .save(any());
    }


    @Test
    public void Correct_transaction_add_amount_to_balance_with_correct_data() throws DatabaseException {
        this.transactionService.createTransactionAndUpdateBalanceToAccount(this.account1,TransactionType.ADD,100.0);
        verify(this.account1,times(1))
                .addValueToBalance(100.0);
    }

    @Test
    public void Correct_transaction_subtract_amount_from_balance_with_correct_data() throws DatabaseException {
        this.transactionService.createTransactionAndUpdateBalanceToAccount(this.account1,TransactionType.SUBSTRACT,100.0);
        verify(this.account1,times(1))
                .substractValueToBalance(100.0);
    }


}