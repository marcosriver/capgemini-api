package com.mriverola.jpa.repositories;

import com.mriverola.jpa.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,Integer> {


}
