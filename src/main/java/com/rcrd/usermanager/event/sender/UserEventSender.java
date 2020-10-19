package com.rcrd.usermanager.event.sender;

import com.rcrd.usermanager.event.model.UserEvent;
import com.rcrd.usermanager.event.model.UserUpdatedEvent;
import com.rcrd.usermanager.persistence.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventSender implements UserEventSenderI {

    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserEventSender(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void userUpdated(User oldUser, User newUser) {
        UserUpdatedEvent event = new UserUpdatedEvent();
        event.setOldUser(oldUser);
        event.setNewUser(newUser);
        kafkaTemplate.send("user.update", oldUser.getId().toString(), event);
    }
}
