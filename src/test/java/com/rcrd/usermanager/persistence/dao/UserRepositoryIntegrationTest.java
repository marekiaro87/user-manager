package com.rcrd.usermanager.persistence.dao;

import com.rcrd.usermanager.UserManagerApplication;
import com.rcrd.usermanager.persistence.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserManagerApplication.class)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanDB(){
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveAUserById(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        User insertedUser = userRepository.save(user1);
        assertNotNull(insertedUser.getId());
        User retrievedUser = userRepository.getOne(insertedUser.getId());
        assertEquals(insertedUser, retrievedUser);
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveAUserByFirstName(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        userRepository.save(user1);
        List<User> retrievedUsers = userRepository.findByFirstName(user1.getFirstName());
        assertEquals(1, retrievedUsers.size());
        assertEquals(user1, retrievedUsers.get(0));
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveUsersByAddress(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        User user2 = new User("User2", "password2", "Address 2","email2@email.com");
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> retrievedUsers = userRepository.findByAddressContaining(user2.getAddress());
        assertEquals(1, retrievedUsers.size());
        retrievedUsers = userRepository.findByAddressContaining("Address");
        assertEquals(2, retrievedUsers.size());
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveAUserByEmail(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        userRepository.save(user1);
        User retrievedUser = userRepository.getByEmail(user1.getEmail());
        assertEquals(user1, retrievedUser);
    }
}
