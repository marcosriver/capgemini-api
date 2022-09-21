package com.mriverola.services.interfaces;

import com.mriverola.exceptions.DatabaseException;
import com.mriverola.jpa.entities.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserServiceI {

    /**
     *
     * This method allows to get the user from the database depending on the Id
     *
     * @param id The id of the user
     * @return Optional object containing the user from the DB
     */
    Optional<User> getUser(int id) throws DatabaseException;

    /**
     * This method allows to gather all the users from the database
     *
     * @return
     * @throws DatabaseException
     */
    List<User> getAllUsers() throws DatabaseException;

    /**
     *
     * This method allows to create a new user with name and surname
     *
     * @param name The name of the new user
     * @param surname The surname of the new user
     * @return The new created user
     * @throws DatabaseException
     */
    User createUser(String name, String surname) throws DatabaseException;

    JSONObject mapUserToJSON(User user) throws JSONException;
}
