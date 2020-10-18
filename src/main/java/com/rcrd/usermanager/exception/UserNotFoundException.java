package com.rcrd.usermanager.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
