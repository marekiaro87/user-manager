package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.UserAlreadyExistingException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.persistence.dao.UserDao;
import com.rcrd.usermanager.persistence.model.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserServiceI {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User create(User user) throws UserAlreadyExistingException {
        User existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser == null) {
            return userDao.save(user);
        } else {
            String errorMsg =
                    String.format("A user with the same email already exists: %s", user.getEmail());
            throw new UserAlreadyExistingException(errorMsg);
        }
    }

    @Override
    public User getById(long id) {
        return userDao.getOne(id);
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByFirstName(name);
    }

    @Override
    public List<User> findByAddress(String address) {
        return userDao.findByAddressContaining(address);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional
    public User update(User user) throws UserNotFoundException {
        try {
            userDao.getOne(user.getId());
            return userDao.save(user);
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    @Override
    public void deleteById(long id) {
        userDao.deleteById(id);
    }
}
