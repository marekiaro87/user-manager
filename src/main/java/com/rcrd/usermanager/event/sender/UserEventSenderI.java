package com.rcrd.usermanager.event.sender;

import com.rcrd.usermanager.persistence.model.User;

public interface UserEventSenderI {

    void userUpdated(User oldUser, User newUser);
}
