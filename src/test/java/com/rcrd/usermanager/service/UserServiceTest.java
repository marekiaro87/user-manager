package com.rcrd.usermanager.service;

import com.rcrd.usermanager.exception.UserCreationException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.persistence.dao.UserDao;
import com.rcrd.usermanager.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private User userMock;

    @Mock
    private UserLocationServiceI userLocationService;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateAUser() throws UserCreationException {
        String ipAddress = "SwissIpAddress";
        when(userDao.getByEmail(userMock.getEmail())).thenReturn(null);
        when(userDao.save(userMock)).thenReturn(userMock);
        when(userLocationService.getCountryByIp(ipAddress)).thenReturn("CH");
        userService.create(userMock, ipAddress);
        verify(userDao, times(1)).getByEmail(userMock.getEmail());
        verify(userDao, times(1)).save(userMock);
    }

    @Test
    public void shouldFailToCreateAUserBecauseNotInSwiss() {
        String ipAddress = "NOTSwissIpAddress";
        when(userDao.getByEmail(userMock.getEmail())).thenReturn(null);
        when(userLocationService.getCountryByIp(ipAddress)).thenReturn("IT");
        assertThrows(UserCreationException.class, () -> userService.create(userMock, ipAddress));
        verify(userDao, times(1)).getByEmail(userMock.getEmail());
        verify(userDao, never()).save(userMock);
    }

    @Test
    public void shouldFailToCreateAUserBecauseAlreadyExisting() {
        when(userDao.getByEmail(userMock.getEmail())).thenReturn(mock(User.class));
        assertThrows(UserCreationException.class, () -> userService.create(userMock, "8.8.8.8"));
        verify(userDao, times(1)).getByEmail(userMock.getEmail());
        verify(userDao, never()).save(userMock);
    }

    @Test
    public void shouldRetrieveAUserById() throws UserNotFoundException {
        when(userDao.findById(userMock.getId())).thenReturn(java.util.Optional.of(userMock));
        userService.getById(userMock.getId());
        verify(userDao, times(1)).findById(userMock.getId());
    }

    @Test
    public void shouldRetrieveUsersByName() throws UserNotFoundException {
        when(userDao.findByFirstName(userMock.getFirstName()))
                .thenReturn(Collections.singletonList(userMock));
        userService.findByName(userMock.getFirstName());
        verify(userDao, times(1)).findByFirstName(userMock.getFirstName());
    }

    @Test
    public void shouldRetrieveUsersByAddress() throws UserNotFoundException {
        String testAddress = "Test address";
        when(userDao.findByAddressContaining(testAddress))
                .thenReturn(Collections.singletonList(userMock));
        userService.findByAddress(testAddress);
        verify(userDao, times(1)).findByAddressContaining(testAddress);
    }

    @Test
    public void shouldRetrieveAUserByEmail() throws UserNotFoundException {
        String testEmail = "email@test.com";
        when(userDao.getByEmail(testEmail)).thenReturn(userMock);
        userService.getByEmail(testEmail);
        verify(userDao, times(1)).getByEmail(testEmail);
    }

    @Test
    public void shouldUpdateAUser() throws UserNotFoundException {
        User userToUpdate = mock(User.class);
        when(userDao.findById(userToUpdate.getId())).thenReturn(Optional.of(userMock));
        when(userDao.save(userToUpdate)).thenReturn(userMock);
        userService.update(userToUpdate);
        verify(userDao, times(1)).findById(userToUpdate.getId());
        verify(userDao, times(1)).save(userToUpdate);
    }

    @Test
    public void shouldFailToUpdate() {
        User userToUpdate = mock(User.class);
        when(userDao.findById(userToUpdate.getId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update(userToUpdate));
        verify(userDao, times(1)).findById(userToUpdate.getId());
        verify(userDao, never()).save(userToUpdate);
    }

    @Test
    public void shouldDeleteAUser() throws UserNotFoundException {
        Long idToDelete = 1L;
        when(userDao.findById(idToDelete)).thenReturn(Optional.of(mock(User.class)));
        doNothing().when(userDao).deleteById(idToDelete);
        userService.deleteById(idToDelete);
        verify(userDao, times(1)).deleteById(idToDelete);
    }
}
