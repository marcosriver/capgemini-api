package com.mriverola.services;

import com.mriverola.exceptions.DatabaseException;
import com.mriverola.jpa.entities.User;
import com.mriverola.jpa.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    public UserServiceTest() {

    }

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public static String USER_NAME_1 = "Marcos";
    public static String USER_SURNAME_1 = "Riverola";

    @Before
    public void setUp() {

        doReturn(Optional.ofNullable(new User(USER_NAME_1,USER_SURNAME_1)))
                .when(this.userRepository).findById(1);
        doReturn(Optional.empty())
                .when(this.userRepository).findById(2);
    }


    @Test
    public void User_retrieve_with_correct_user_is_retrieved_correctly() throws DatabaseException {
        Optional<User> user = userService.getUser(1);

        assertAll("Retrurns user information correctly",
                () -> assertEquals(USER_NAME_1,user.get().getUserName()),
                () -> assertEquals(USER_SURNAME_1, user.get().getSurname())

        );


    }

    @Test
    public void User_retrieve_with_user_not_found_returns_null() throws DatabaseException {
        Optional<User> user = userService.getUser(2);

        assertFalse(user.isPresent());
    }


}