package com.rcrd.usermanager.event.model;

import com.rcrd.usermanager.persistence.model.User;

public class UserUpdatedEvent implements UserEvent {

    private final EventType eventType = EventType.USER_UPDATED;

    private User oldUser;

    private User newUser;

    public User getOldUser() {
        return oldUser;
    }

    public void setOldUser(User oldUser) {
        this.oldUser = oldUser;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
