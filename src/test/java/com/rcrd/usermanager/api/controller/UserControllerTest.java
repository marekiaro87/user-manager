package com.rcrd.usermanager.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcrd.usermanager.api.converter.UserDTOConverter;
import com.rcrd.usermanager.api.model.UserDTO;
import com.rcrd.usermanager.persistence.model.User;
import com.rcrd.usermanager.service.UserServiceI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDTOConverter userDTOConverter;

    @MockBean
    private UserServiceI userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldCreateAUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userDTOConverter.convertToEntity(userDTO)).thenReturn(user);
        when(userService.create(user, "8.8.8.8")).thenReturn(user);
        when(userDTOConverter.convertToDTO(user)).thenReturn(userDTO);
        mvc.perform(MockMvcRequestBuilders.post("/user")
                .content(objectMapper.writeValueAsBytes(userDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRetrieveAUser() throws Exception {
        UserDTO retrievedUserDTO = new UserDTO();
        User retrievedUser = new User();
        when(userService.getById(1)).thenReturn(retrievedUser);
        when(userDTOConverter.convertToDTO(retrievedUser)).thenReturn(retrievedUserDTO);
        mvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
