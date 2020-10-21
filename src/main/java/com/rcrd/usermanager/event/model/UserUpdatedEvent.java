package com.rcrd.usermanager.event.model;

import com.rcrd.usermanager.model.UserBo;

public class UserUpdatedEvent implements UserEvent {

    private UserBo oldUser;

    private UserBo newUser;

    public UserBo getOldUser() {
        return oldUser;
    }

    public void setOldUser(UserBo oldUser) {
        this.oldUser = oldUser;
    }

    public UserBo getNewUser() {
        return newUser;
    }

    public void setNewUser(UserBo newUser) {
        this.newUser = newUser;
    }

    @Override
    public EventType getEventType() {
        return EventType.USER_UPDATED;
    }

    @Override
    public Long getKey() {
        return oldUser.getId();
    }
}
