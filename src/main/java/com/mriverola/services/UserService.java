package com.mriverola.services;

import com.mriverola.exceptions.DatabaseException;
import com.mriverola.jpa.entities.User;
import com.mriverola.jpa.repositories.UserRepository;
import com.mriverola.services.interfaces.UserServiceI;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserServiceI {



    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public Optional<User> getUser(int id) throws DatabaseException {

        Optional<User> user = null;
        try {
            user = userRepository.findById(id);
        } catch(Exception e){
            throw new DatabaseException(e.getMessage());
        }
        return user;
    }


    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public List<User> getAllUsers() throws DatabaseException {
        List<User> userList = new ArrayList<>();
        try {
            Iterable<User> allUsers = userRepository.findAll();
            userList = StreamSupport
                    .stream(allUsers.spliterator(), false)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
        return userList;
    }


    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public User createUser(String name, String surname) throws DatabaseException {

        User user;
        try {
            user = userRepository.save(new User(name,surname));
        } catch(Exception e){
            throw new DatabaseException(e.getMessage());
        }

        return user;

    }


    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public JSONObject mapUserToJSON(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getUserId());
        jsonObject.put("userName", user.getUserName());
        jsonObject.put("surname", user.getSurname());

        return jsonObject;

    }

}
