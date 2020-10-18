package com.rcrd.usermanager.api.converter;

import com.rcrd.usermanager.api.model.UserDTO;
import com.rcrd.usermanager.persistence.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    private final ModelMapper modelMapper;

    public UserDTOConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
