package com.rcrd.usermanager.event.model;

import com.rcrd.usermanager.persistence.model.User;

public class UserCreatedEvent implements UserEvent {

    private final EventType eventType = EventType.USER_CREATED;

    private User createdUser;

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
