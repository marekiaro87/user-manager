package com.rcrd.usermanager.api.converter;

import com.rcrd.usermanager.api.model.UserDTO;
import com.rcrd.usermanager.model.UserBo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    private final ModelMapper modelMapper;

    public UserDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDTO(UserBo user) {
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            return null;
        }
    }

    public UserBo convertToEntity(UserDTO userDTO) {
        if (userDTO != null) {
            return modelMapper.map(userDTO, UserBo.class);
        } else {
            return null;
        }
    }
}
