package com.rcrd.usermanager.event.model;

import com.rcrd.usermanager.persistence.model.User;

public class UserDeletedEvent implements UserEvent {

    private final EventType eventType = EventType.USER_DELETED;

    private User deletedUser;

    public User getDeletedUser() {
        return deletedUser;
    }

    public void setDeletedUser(User deletedUser) {
        this.deletedUser = deletedUser;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
