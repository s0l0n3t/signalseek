package com.furkantokgoz.exception;


public class RoomNotFoundException extends RuntimeException {

    //alternative exception
    public RoomNotFoundException() {
        super("Room not found");
    }
    public RoomNotFoundException(String message) {
        super("Room not found: "+ message);
    }
    public RoomNotFoundException(String message, Throwable cause) {
        super("Room not found: "+ message, cause);
    }
    public RoomNotFoundException(Throwable cause) {
        super("Room not found", cause);
    }
}
