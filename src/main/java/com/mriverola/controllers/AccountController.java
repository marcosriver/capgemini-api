package com.mriverola.controllers;

import com.mriverola.exceptions.DatabaseException;
import com.mriverola.exceptions.UserException;
import com.mriverola.jpa.entities.Account;
import com.mriverola.jpa.entities.User;
import com.mriverola.services.AccountService;
import com.mriverola.services.UserService;
import com.mriverola.services.interfaces.AccountServiceI;
import com.mriverola.services.interfaces.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	@Autowired
	UserServiceI userService;

	@Autowired
	AccountServiceI accountService;

	@PostMapping
	public ResponseEntity save(@Valid @RequestBody Account account)  {

		try {
			accountService.openAccount(account.getUser().getUserId(),account.getAmount());
		} catch (UserException e) {
			return ResponseEntity.notFound().build();
		} catch (DatabaseException e) {
			return ResponseEntity.internalServerError().build();
		}

		return ResponseEntity.ok().build();
	}

}
