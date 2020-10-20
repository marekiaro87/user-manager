package com.rcrd.usermanager.api.converter;

import com.rcrd.usermanager.UserManagerApplication;
import com.rcrd.usermanager.api.model.UserDTO;
import com.rcrd.usermanager.model.UserBo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserManagerApplication.class)
public class UserDTOConverterTest {

    @Autowired
    private UserDTOConverter userDTOConverter;

    @Test
    public void shouldConvertToDTO() {
        UserBo user = new UserBo("User1", "password1", "Address 1", "email1@email.com");
        UserDTO userDTO = userDTOConverter.convertToDTO(user);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void shouldConvertToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Name");
        userDTO.setPassword("password");
        userDTO.setAddress("address");
        userDTO.setEmail("email");
        UserBo user = userDTOConverter.convertToEntity(userDTO);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getAddress(), user.getAddress());
        assertEquals(userDTO.getEmail(), user.getEmail());
    }
}
