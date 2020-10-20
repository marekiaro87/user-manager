package com.rcrd.usermanager.event.sender;

import com.rcrd.usermanager.persistence.model.User;

public interface UserEventSenderI {

    void userCreated(User newUser);

    void userUpdated(User oldUser, User newUser);

    void userDeleted(User deletedUser);
}
