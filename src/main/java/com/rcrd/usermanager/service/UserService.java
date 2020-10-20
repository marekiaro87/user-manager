package com.rcrd.usermanager.service;

import com.rcrd.usermanager.event.sender.UserEventSenderI;
import com.rcrd.usermanager.exception.ExceptionMessages;
import com.rcrd.usermanager.exception.UserCreationException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.model.UserBo;
import com.rcrd.usermanager.persistence.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserServiceI {

    private final UserDao userDao;

    private final UserLocationServiceI userLocationService;

    private final UserEventSenderI userEventSender;

    private static final String SWISS_COUNTRY_CODE = "CH";

    public UserService(UserDao userDao, UserLocationServiceI userLocationService, UserEventSenderI userEventSenderI) {
        this.userDao = userDao;
        this.userLocationService = userLocationService;
        this.userEventSender = userEventSenderI;
    }

    @Override
    @Transactional
    public UserBo create(UserBo user, String ipAddress) throws UserCreationException {
        UserBo existingUser = userDao.getByEmail(user.getEmail());
        if (existingUser == null) {
            String userCountry = userLocationService.getCountryByIp(ipAddress);
            if (userCountry.equals(SWISS_COUNTRY_CODE)) {
                UserBo createdUser = userDao.save(user);
                userEventSender.userCreated(createdUser);
                return createdUser;
            } else {
                throw new UserCreationException(String.format(ExceptionMessages.CREATION_NOT_ALLOWED, user.getEmail()));
            }
        } else {
            throw new UserCreationException(String.format(ExceptionMessages.EMAIL_ALREADY_EXISTS, user.getEmail()));
        }
    }

    @Override
    public UserBo getById(long id) throws UserNotFoundException {
        return userDao.getById(id);
    }

    @Override
    public List<UserBo> findByName(String name) {
        return userDao.findByFirstName(name);
    }

    @Override
    public List<UserBo> findByAddress(String address) {
        return userDao.findByAddressContaining(address);
    }

    @Override
    public UserBo getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional
    public UserBo update(UserBo user) throws UserNotFoundException {
        UserBo retrievedUser = userDao.getById(user.getId());
        UserBo updatedUser = userDao.save(user);
        userEventSender.userUpdated(retrievedUser, updatedUser);
        return updatedUser;
    }

    @Override
    public void deleteById(long id) throws UserNotFoundException {
        UserBo userToDelete = userDao.getById(id);
        userDao.deleteById(id);
        userEventSender.userDeleted(userToDelete);
    }
}
