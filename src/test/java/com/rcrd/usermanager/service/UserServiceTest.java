package com.rcrd.usermanager.service;

import com.rcrd.usermanager.event.sender.UserEventSenderI;
import com.rcrd.usermanager.exception.UserCreationException;
import com.rcrd.usermanager.exception.UserNotFoundException;
import com.rcrd.usermanager.model.UserBo;
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
    private UserBo userMock;

    @Mock
    private UserLocationServiceI userLocationService;

    @Mock
    private UserEventSenderI userEventSender;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateAUser() throws UserCreationException {
        String ipAddress = "SwissIpAddress";
        when(userDao.getByEmail(userMock.getEmail())).thenReturn(null);
        when(userDao.save(userMock)).thenReturn(userMock);
        when(userLocationService.getCountryByIp(ipAddress)).thenReturn("CH");
        doNothing().when(userEventSender).userCreated(any(UserBo.class));
        userService.create(userMock, ipAddress);
        verify(userDao, times(1)).getByEmail(userMock.getEmail());
        verify(userDao, times(1)).save(userMock);
        verify(userEventSender, times(1)).userCreated(any(UserBo.class));
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
        when(userDao.getByEmail(userMock.getEmail())).thenReturn(mock(UserBo.class));
        assertThrows(UserCreationException.class, () -> userService.create(userMock, "8.8.8.8"));
        verify(userDao, times(1)).getByEmail(userMock.getEmail());
        verify(userDao, never()).save(userMock);
    }

    @Test
    public void shouldRetrieveAUserById() throws UserNotFoundException {
        when(userDao.getById(userMock.getId())).thenReturn(userMock);
        userService.getById(userMock.getId());
        verify(userDao, times(1)).getById(userMock.getId());
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
        UserBo userToUpdate = mock(UserBo.class);
        UserBo existingUser = mock(UserBo.class);
        when(userDao.getById(userToUpdate.getId())).thenReturn(existingUser);
        when(userDao.save(userToUpdate)).thenReturn(userMock);
        doNothing().when(userEventSender).userUpdated(existingUser, userMock);
        userService.update(userToUpdate);
        verify(userDao, times(1)).getById(userToUpdate.getId());
        verify(userDao, times(1)).save(userToUpdate);
    }

    @Test
    public void shouldDeleteAUserAndSendAnEvent() throws UserNotFoundException {
        Long idToDelete = 1L;
        when(userDao.getById(idToDelete)).thenReturn(mock(UserBo.class));
        doNothing().when(userDao).deleteById(idToDelete);
        doNothing().when(userEventSender).userDeleted(any(UserBo.class));
        userService.deleteById(idToDelete);
        verify(userDao, times(1)).deleteById(idToDelete);
        verify(userEventSender, times(1)).userDeleted(any(UserBo.class));
    }
}
