package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.UserCreationException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.persistence.model.User;
import java.util.List;

/**
 * Interface providing basics CRUD operation on a {@link User}
 */
public interface UserServiceI {

    User create(User user, String ipAddress) throws UserCreationException;

    User getById(long id) throws UserNotFoundException;

    List<User> findByName(String name);

    List<User> findByAddress(String address);

    User getByEmail(String email);

    User update(User user) throws UserNotFoundException;

    void deleteById(long id) throws UserNotFoundException;

}
