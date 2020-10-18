package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.UserAlreadyExistingException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.persistence.dao.UserDao;
import com.rcrd.usermanager.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private User userMock;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateAUser() throws UserAlreadyExistingException {
        when(userDao.findByEmail(userMock.getEmail())).thenReturn(null);
        when(userDao.save(userMock)).thenReturn(userMock);
        userService.create(userMock);
        verify(userDao, times(1)).findByEmail(userMock.getEmail());
        verify(userDao, times(1)).save(userMock);
    }

    @Test
    public void shouldFailToCreateAUser() {
        when(userDao.findByEmail(userMock.getEmail())).thenReturn(mock(User.class));
        assertThrows(UserAlreadyExistingException.class, () -> userService.create(userMock));
        verify(userDao, times(1)).findByEmail(userMock.getEmail());
        verify(userDao, never()).save(userMock);
    }

    @Test
    public void shouldRetrieveAUserById() {
        when(userDao.getOne(userMock.getId())).thenReturn(userMock);
        userService.getById(userMock.getId());
        verify(userDao, times(1)).getOne(userMock.getId());
    }

    @Test
    public void shouldRetrieveUsersByName() {
        when(userDao.findByFirstName(userMock.getFirstName()))
                .thenReturn(Collections.singletonList(userMock));
        userService.findByName(userMock.getFirstName());
        verify(userDao, times(1)).findByFirstName(userMock.getFirstName());
    }

    @Test
    public void shouldRetrieveUsersByAddress() {
        String testAddress = "Test address";
        when(userDao.findByAddressContaining(testAddress))
                .thenReturn(Collections.singletonList(userMock));
        userService.findByAddress(testAddress);
        verify(userDao, times(1)).findByAddressContaining(testAddress);
    }

    @Test
    public void shouldRetrieveAUserByEmail() {
        String testEmail = "email@test.com";
        when(userDao.findByEmail(testEmail))
                .thenReturn(userMock);
        userService.getByEmail(testEmail);
        verify(userDao, times(1)).findByEmail(testEmail);
    }

    @Test
    public void shouldUpdateAUserButNotPassword() throws UserNotFoundException {
        User userToUpdate = new User("User1", "password1", "Address 1", "email1@email.com");
        when(userDao.getOne(userToUpdate.getId())).thenReturn(userMock);
        when(userDao.save(userToUpdate)).thenReturn(userMock);
        User updatedUser = userService.update(userToUpdate);
        assertNull(updatedUser.getPassword());
        verify(userDao, times(1)).getOne(userToUpdate.getId());
        verify(userDao, times(1)).save(userToUpdate);
    }

    @Test
    public void shouldFailToUpdate() {
        User userToUpdate = mock(User.class);
        when(userDao.getOne(userToUpdate.getId())).thenThrow(EntityNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> userService.update(userToUpdate));
        verify(userDao, times(1)).getOne(userToUpdate.getId());
        verify(userDao, never()).save(userToUpdate);
    }

    @Test
    public void shouldDeleteAUser() {
        Long idToDelete = 1L;
        doNothing().when(userDao).deleteById(idToDelete);
        userService.deleteById(idToDelete);
        verify(userDao, times(1)).deleteById(idToDelete);
    }
}
