package com.rcrd.usermanager.event.model;

import com.rcrd.usermanager.model.UserBo;

public class UserDeletedEvent implements UserEvent {

    private final EventType eventType = EventType.USER_DELETED;

    private UserBo deletedUser;

    public UserBo getDeletedUser() {
        return deletedUser;
    }

    public void setDeletedUser(UserBo deletedUser) {
        this.deletedUser = deletedUser;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public Long getKey() {
        return deletedUser.getId();
    }


}
