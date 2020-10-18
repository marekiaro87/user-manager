package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.ExceptionMessages;
import com.rcrd.usermanager.exception.UserCreationException;
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

    private final UserLocationServiceI userLocationService;

    private static final String SWISS_COUNTRY_CODE = "CH";

    public UserService(UserDao userDao, UserLocationServiceI userLocationService) {
        this.userDao = userDao;
        this.userLocationService = userLocationService;
    }

    @Override
    @Transactional
    public User create(User user, String ipAddress) throws UserCreationException {
        User existingUser = userDao.getByEmail(user.getEmail());
        if (existingUser == null) {
            String userCountry = userLocationService.getCountryByIp(ipAddress);
            if (userCountry.equals(SWISS_COUNTRY_CODE)) {
                return userDao.save(user);
            } else {
                throw new UserCreationException(String.format(ExceptionMessages.CREATION_NOT_ALLOWED, user.getEmail()));
            }
        } else {
            throw new UserCreationException(String.format(ExceptionMessages.EMAIL_ALREADY_EXISTS, user.getEmail()));
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
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional
    public User update(User user) throws UserNotFoundException {
        try {
            userDao.getOne(user.getId());
            return userDao.save(user);
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException(ExceptionMessages.USER_NOT_EXISTING);
        }
    }

    @Override
    public void deleteById(long id) {
        userDao.deleteById(id);
    }
}
