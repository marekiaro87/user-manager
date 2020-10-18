package com.rcrd.usermanager.exception;

public final class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Don't create an instance of this class, use its static constants");
    }

    public static final String CREATION_NOT_ALLOWED = "User creation is allowed only from Swiss: %s";

    public static final String EMAIL_ALREADY_EXISTS = "A user with the same email already exists: %s";

    public static final String USER_NOT_EXISTING = "The requested User doesn't exist in the system";
}
