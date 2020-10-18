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
public class UserDaoIntegrationTest {

    @Autowired
    private UserDao userDao;

    @AfterEach
    public void cleanDB(){
        userDao.deleteAll();
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveAUserById(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        User insertedUser = userDao.save(user1);
        assertNotNull(insertedUser.getId());
        User retrievedUser = userDao.getOne(insertedUser.getId());
        assertEquals(insertedUser, retrievedUser);
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveAUserByFirstName(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        userDao.save(user1);
        List<User> retrievedUsers = userDao.findByFirstName(user1.getFirstName());
        assertEquals(1, retrievedUsers.size());
        assertEquals(user1, retrievedUsers.get(0));
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveUsersByAddress(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        User user2 = new User("User2", "password2", "Address 2","email2@email.com");
        userDao.save(user1);
        userDao.save(user2);
        List<User> retrievedUsers = userDao.findByAddressContaining(user2.getAddress());
        assertEquals(1, retrievedUsers.size());
        retrievedUsers = userDao.findByAddressContaining("Address");
        assertEquals(2, retrievedUsers.size());
    }

    @Test
    @Transactional
    public void shouldInsertAndRetrieveAUserByEmail(){
        User user1 = new User("User1", "password1", "Address 1","email1@email.com");
        userDao.save(user1);
        User retrievedUser = userDao.getByEmail(user1.getEmail());
        assertEquals(user1, retrievedUser);
    }
}
