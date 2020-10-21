package com.rcrd.usermanager.persistence.converter;

import com.rcrd.usermanager.model.UserBo;
import com.rcrd.usermanager.persistence.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserEntityConverter {

    private final ModelMapper modelMapper;

    public UserEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserBo convertToBo(User user) {
        if (user != null) {
            return modelMapper.map(user, UserBo.class);
        } else {
            return null;
        }
    }

    public User convertToEntity(UserBo userBo) {
        if (userBo != null) {
            return modelMapper.map(userBo, User.class);
        } else {
            return null;
        }
    }
}
