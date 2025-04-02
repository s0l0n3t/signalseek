package com.furkantokgoz.exception;

import com.furkantokgoz.dto.UserDto;

public class UserNotFoundException extends RuntimeException {
    //alternative exception

    public UserNotFoundException(){
        super("User not found");
    }
    public UserNotFoundException(String message) {
        super(message + " not found");
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
