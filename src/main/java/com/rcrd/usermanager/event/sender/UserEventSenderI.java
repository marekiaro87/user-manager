package com.rcrd.usermanager.event.sender;

import com.rcrd.usermanager.model.UserBo;

public interface UserEventSenderI {

    void userCreated(UserBo newUser);

    void userUpdated(UserBo oldUser, UserBo newUser);

    void userDeleted(UserBo deletedUser);
}
