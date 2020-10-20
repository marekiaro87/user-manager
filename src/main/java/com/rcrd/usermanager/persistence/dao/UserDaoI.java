package com.rcrd.usermanager.persistence.dao;

import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.model.UserBo;

import java.util.List;

public interface UserDaoI {

    UserBo getById(Long id) throws UserNotFoundException;

    UserBo save(UserBo userBo);

    List<UserBo> findByFirstName(String firstName);

    List<UserBo> findByAddressContaining(String address);

    UserBo getByEmail(String email);

    void deleteById(Long id);
}
