package com.mriverola.jpa.repositories;

import com.mriverola.jpa.entities.Account;
import com.mriverola.jpa.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,Integer> {
}
