package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.persistence.dao.UserDao;
import com.rcrd.usermanager.persistence.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceI {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(User user) {
        return userDao.save(user);
    }

    @Override
    public User getById(long id) {
        return userDao.getOne(id);
    }

    @Override
    public List<User> getByName(String name) {
        return userDao.findByFirstName(name);
    }

    @Override
    public List<User> getByAddress(String address) {
        return userDao.findByAddressContaining(address);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        User existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser != null) {
            user.setId(existingUser.getId());
            user.setPassword(existingUser.getPassword());
            return userDao.save(user);
        } else {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    @Override
    public void deleteById(long id) {
        userDao.deleteById(id);
    }
}
