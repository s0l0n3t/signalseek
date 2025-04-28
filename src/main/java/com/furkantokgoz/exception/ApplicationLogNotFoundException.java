package com.furkantokgoz.exception;

public class ApplicationLogNotFoundException extends RuntimeException{
    public ApplicationLogNotFoundException(){
        super("Application Log Not Found");
    }
    public ApplicationLogNotFoundException(String message) {
        super("Application Log Not Found Exception: " + message);
    }
    public ApplicationLogNotFoundException(String message, Throwable cause) {
        super("Application Log Not Found Exception: " + message, cause);
    }
    public ApplicationLogNotFoundException(Throwable cause) {
        super("Application Log Not Found Exception", cause);
    }
}
