package com.rcrd.usermanager.persistence.dao;

import com.rcrd.usermanager.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
