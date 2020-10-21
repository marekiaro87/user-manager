package com.rcrd.usermanager.event.sender;

import com.rcrd.usermanager.UserManagerApplication;
import com.rcrd.usermanager.event.model.UserCreatedEvent;
import com.rcrd.usermanager.event.model.UserDeletedEvent;
import com.rcrd.usermanager.event.model.UserEvent;
import com.rcrd.usermanager.event.model.UserUpdatedEvent;
import com.rcrd.usermanager.model.UserBo;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
        UserBo created = mock(UserBo.class);
        userEventSender.userCreated(created);
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
    }

    @Test
    public void itShouldSendAnUpdateEvent() {
        UserBo oldUser = new UserBo("Name", "password", "address", "email");
        oldUser.setId(1L);
        UserBo newUser = new UserBo("Name", "password", "addressChanged", "email");
        userEventSender.userUpdated(oldUser, newUser);
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
    }

    @Test
    public void itShouldNotSendTheUpdateEvent() {
        UserBo oldUser = new UserBo("Name", "password", "address", "email");
        UserBo usedWithoutUpdate = new UserBo("Name", "password", "address", "email");
        userEventSender.userUpdated(oldUser, usedWithoutUpdate);
        verify(kafkaTemplate, never()).send(any(ProducerRecord.class));
    }

    @Test
    public void itShouldSendADeleteEvent() {
        UserBo created = mock(UserBo.class);
        userEventSender.userDeleted(created);
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
    }
}
