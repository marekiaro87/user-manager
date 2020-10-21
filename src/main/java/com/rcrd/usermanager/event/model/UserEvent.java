package com.rcrd.usermanager.event.model;

public interface UserEvent {

    enum EventType {
        USER_CREATED,
        USER_UPDATED,
        USER_DELETED
    }

    EventType getEventType();

    Long getKey();

}
