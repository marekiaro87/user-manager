package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.UserCreationException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.model.UserBo;

import java.util.List;

/**
 * Interface providing basics CRUD operation on a {@link UserBo}
 */
public interface UserServiceI {

    UserBo create(UserBo user, String ipAddress) throws UserCreationException;

    UserBo getById(long id) throws UserNotFoundException;

    List<UserBo> findByName(String name);

    List<UserBo> findByAddress(String address);

    UserBo getByEmail(String email);

    UserBo update(UserBo user) throws UserNotFoundException;

    void deleteById(long id) throws UserNotFoundException;

}
