package com.rcrd.usermanager.event.model;

import com.rcrd.usermanager.model.UserBo;

public class UserCreatedEvent implements UserEvent {

    private UserBo createdUser;

    public UserBo getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(UserBo createdUser) {
        this.createdUser = createdUser;
    }

    @Override
    public EventType getEventType() {
        return EventType.USER_CREATED;
    }

    @Override
    public Long getKey() {
        return createdUser.getId();
    }
}
