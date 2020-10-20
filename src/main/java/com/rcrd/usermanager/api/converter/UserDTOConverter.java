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
        return modelMapper.map(user, UserDTO.class);
    }

    public UserBo convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserBo.class);
    }
}
