package com.rcrd.usermanager.event.model;

import com.rcrd.usermanager.model.UserBo;

public class UserDeletedEvent implements UserEvent {

    private UserBo deletedUser;

    public UserBo getDeletedUser() {
        return deletedUser;
    }

    public void setDeletedUser(UserBo deletedUser) {
        this.deletedUser = deletedUser;
    }

    @Override
    public EventType getEventType() {
        return EventType.USER_DELETED;
    }

    @Override
    public Long getKey() {
        return deletedUser.getId();
    }


}
