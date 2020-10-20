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
        return modelMapper.map(user, UserBo.class);
    }

    public User convertToEntity(UserBo userBo) {
        return modelMapper.map(userBo, User.class);
    }
}
