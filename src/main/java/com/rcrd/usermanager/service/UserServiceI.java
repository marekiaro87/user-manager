package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.UserAlreadyExistingException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.persistence.model.User;
import java.util.List;

/**
 * Interface providing basics CRUD operation on a {@link User}
 */
public interface UserServiceI {

    User create(User user) throws UserAlreadyExistingException;

    User getById(long id);

    List<User> findByName(String name);

    List<User> findByAddress(String address);

    User getByEmail(String email);

    User update(User user) throws UserNotFoundException;

    void deleteById(long id);

}
