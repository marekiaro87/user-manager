package com.rcrd.usermanager.persistence.dao;

import com.rcrd.usermanager.exception.ExceptionMessages;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.model.UserBo;
import com.rcrd.usermanager.persistence.converter.UserEntityConverter;
import com.rcrd.usermanager.persistence.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDao implements UserDaoI {

    private final UserEntityConverter userEntityConverter;

    private final UserRepository userRepository;

    public UserDao(UserEntityConverter userEntityConverter, UserRepository userRepository) {
        this.userEntityConverter = userEntityConverter;
        this.userRepository = userRepository;
    }

    @Override
    public UserBo getById(Long id) throws UserNotFoundException {
        User retrievedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_EXISTING));
        return userEntityConverter.convertToBo(retrievedUser);
    }

    @Override
    public UserBo save(UserBo userBo) {
        User toSave = userEntityConverter.convertToEntity(userBo);
        toSave = userRepository.save(toSave);
        return userEntityConverter.convertToBo(toSave);
    }

    @Override
    public List<UserBo> findByFirstName(String firstName) {
        List<User> userList = userRepository.findByFirstName(firstName);
        return userList.stream().map(userEntityConverter::convertToBo).collect(Collectors.toList());
    }

    @Override
    public List<UserBo> findByAddressContaining(String address) {
        List<User> userList = userRepository.findByAddressContaining(address);
        return userList.stream().map(userEntityConverter::convertToBo).collect(Collectors.toList());
    }

    @Override
    public UserBo getByEmail(String email) {
        User user = userRepository.getByEmail(email);
        return userEntityConverter.convertToBo(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
