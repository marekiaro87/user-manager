package com.rcrd.usermanager.api.controller;

import com.rcrd.usermanager.api.converter.UserDTOConverter;
import com.rcrd.usermanager.api.model.UserDTO;
import com.rcrd.usermanager.exception.UserCreationException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.service.UserServiceI;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceI userService;

    private final UserDTOConverter userDTOConverter;

    public UserController(UserServiceI userService, UserDTOConverter userDTOConverter) {
        this.userService = userService;
        this.userDTOConverter = userDTOConverter;
    }

    @PostMapping()
    public UserDTO createUser(@RequestBody UserDTO userDTO, HttpServletRequest request) throws UserCreationException {
        String incomingIp = request.getHeader("X-FORWARDED-FOR") != null
                ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
        return userDTOConverter.convertToDTO(userService.create(userDTOConverter.convertToEntity(userDTO), incomingIp));
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userDTOConverter.convertToDTO(userService.getById(id));
    }

    @GetMapping("/findByName")
    public List<UserDTO> getUserByFirstName(@RequestParam String firstName) {
        return userService.findByName(firstName).stream().map(userDTOConverter::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/findByAddress")
    public List<UserDTO> getUserByAddress(@RequestParam String address) {
        return userService.findByAddress(address).stream().map(userDTOConverter::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/findByEmail")
    public UserDTO getUserByEmail(@RequestParam String email) {
        return userDTOConverter.convertToDTO(userService.getByEmail(email));
    }

    @PutMapping("/{id}")
    public UserDTO updateUserById(@RequestBody UserDTO userDTO, @PathVariable Long id) throws UserNotFoundException {
        userDTO.setId(id);
        return userDTOConverter.convertToDTO(userService.update(userDTOConverter.convertToEntity(userDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteById(id);
    }

}
