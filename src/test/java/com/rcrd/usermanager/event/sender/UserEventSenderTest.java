package com.rcrd.usermanager.event.sender;

import com.rcrd.usermanager.UserManagerApplication;
import com.rcrd.usermanager.event.model.UserCreatedEvent;
import com.rcrd.usermanager.event.model.UserDeletedEvent;
import com.rcrd.usermanager.event.model.UserEvent;
import com.rcrd.usermanager.event.model.UserUpdatedEvent;
import com.rcrd.usermanager.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserManagerApplication.class)
public class UserEventSenderTest {

    @MockBean
    private KafkaTemplate<Long, UserEvent> kafkaTemplate;

    @Autowired
    private UserEventSenderI userEventSender;

    @Test
    public void itShouldSendACreateEvent() {
        User created = mock(User.class);
        userEventSender.userCreated(created);
        verify(kafkaTemplate, times(1)).send(anyString(), anyLong(), any(UserCreatedEvent.class));
    }

    @Test
    public void itShouldSendAnUpdateEvent() {
        User oldUser = new User("Name", "password", "address", "email");
        oldUser.setId(1L);
        User newUser = new User("Name", "password", "addressChanged", "email");
        userEventSender.userUpdated(oldUser, newUser);
        verify(kafkaTemplate, times(1)).send(anyString(), anyLong(), any(UserUpdatedEvent.class));
    }

    @Test
    public void itShouldNotSendTheUpdateEvent() {
        User oldUser = new User("Name", "password", "address", "email");
        User usedWithoutUpdate = new User("Name", "password", "address", "email");
        userEventSender.userUpdated(oldUser, usedWithoutUpdate);
        verify(kafkaTemplate, never()).send(anyString(), anyLong(), any(UserUpdatedEvent.class));
    }

    @Test
    public void itShouldSendADeleteEvent() {
        User created = mock(User.class);
        userEventSender.userDeleted(created);
        verify(kafkaTemplate, times(1)).send(anyString(), anyLong(), any(UserDeletedEvent.class));
    }
}
