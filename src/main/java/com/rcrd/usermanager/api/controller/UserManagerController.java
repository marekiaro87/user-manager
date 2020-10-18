package com.rcrd.usermanager.api.controller;

import com.rcrd.usermanager.api.converter.UserDTOConverter;
import com.rcrd.usermanager.api.model.UserDTO;
import com.rcrd.usermanager.exception.UserAlreadyExistingException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.service.UserServiceI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserManagerController {

    private final UserServiceI userService;

    private final UserDTOConverter userDTOConverter;

    public UserManagerController(UserServiceI userService, UserDTOConverter userDTOConverter) {
        this.userService = userService;
        this.userDTOConverter = userDTOConverter;
    }

    @PostMapping("/")
    public UserDTO createUser(@RequestBody UserDTO userDTO) throws UserAlreadyExistingException {
        return userDTOConverter.convertToDTO(userService.create(userDTOConverter.convertToEntity(userDTO)));
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userDTOConverter.convertToDTO(userService.getById(id));
    }

    @GetMapping("/firstname/{firstname}")
    public List<UserDTO> getUserByFirstName(@PathVariable String firstName) {
        return userService.findByName(firstName).stream().map(userDTOConverter::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/address/{address}")
    public List<UserDTO> getUserByAddress(@PathVariable String address) {
        return userService.findByAddress(address).stream().map(userDTOConverter::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userDTOConverter.convertToDTO(userService.getByEmail(email));
    }

    @PutMapping("/{id}")
    public UserDTO updateUserById(@RequestBody UserDTO userDTO, @PathVariable Long id) throws UserNotFoundException {
        userDTO.setId(id);
        return userDTOConverter.convertToDTO(userService.update(userDTOConverter.convertToEntity(userDTO)));
    }
}
