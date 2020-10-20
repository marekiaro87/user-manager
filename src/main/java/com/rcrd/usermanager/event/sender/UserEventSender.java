package com.rcrd.usermanager.event.sender;

import com.rcrd.usermanager.event.model.UserCreatedEvent;
import com.rcrd.usermanager.event.model.UserDeletedEvent;
import com.rcrd.usermanager.event.model.UserEvent;
import com.rcrd.usermanager.event.model.UserUpdatedEvent;
import com.rcrd.usermanager.model.UserBo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.rcrd.usermanager.event.KafkaTopicConfig.USER_UPDATES_TOPIC;

@Component
public class UserEventSender implements UserEventSenderI {

    private final KafkaTemplate<Long, UserEvent> kafkaTemplate;

    public UserEventSender(KafkaTemplate<Long, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void userCreated(UserBo newUser) {
        UserCreatedEvent event = new UserCreatedEvent();
        event.setCreatedUser(newUser);
        kafkaTemplate.send(USER_UPDATES_TOPIC, newUser.getId(), event);
    }

    @Override
    public void userUpdated(UserBo oldUser, UserBo newUser) {
        UserUpdatedEvent event = new UserUpdatedEvent();
        if (isUserChanged(oldUser, newUser)) {
            event.setOldUser(oldUser);
            event.setNewUser(newUser);
            kafkaTemplate.send(USER_UPDATES_TOPIC, oldUser.getId(), event);
        }
    }

    @Override
    public void userDeleted(UserBo deletedUser) {
        UserDeletedEvent event = new UserDeletedEvent();
        event.setDeletedUser(deletedUser);
        kafkaTemplate.send(USER_UPDATES_TOPIC, deletedUser.getId(), event);
    }

    private boolean isUserChanged(UserBo oldUser, UserBo newUser) {
        return !oldUser.equals(newUser);
    }
}
