package com.rcrd.usermanager.exception;

public class UserAlreadyExistingException extends Exception {

    public UserAlreadyExistingException(String errorMessage){
        super(errorMessage);
    }

}
