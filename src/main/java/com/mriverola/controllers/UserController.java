package com.mriverola.controllers;


import com.mriverola.exceptions.DatabaseException;
import com.mriverola.jpa.entities.User;
import com.mriverola.services.UserService;
import com.mriverola.services.interfaces.UserServiceI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceI userService;

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable int id) {
        Optional<User> user;
        try {
            user = userService.getUser(id);
        } catch (DatabaseException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllUsers() {
        List<User> users;
        try {
            users = userService.getAllUsers();
        } catch (DatabaseException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < users.size(); ++i) {

            try {
                JSONObject jsonObject = userService.mapUserToJSON(users.get(i));
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                return ResponseEntity.internalServerError().build();
            }

        }

        return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody User user) {
        JSONObject jsonObject = null;

        try {
            User newUser = userService.createUser(user.getUserName(), user.getSurname());
            jsonObject = userService.mapUserToJSON(newUser);
        } catch (DatabaseException | JSONException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }





}
