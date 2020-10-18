package com.rcrd.usermanager.persistence.dao;

import com.rcrd.usermanager.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    List<User> findByFirstName(String firstName);

    List<User> findByAddressContaining(String address);

    User getByEmail(String email);

}
