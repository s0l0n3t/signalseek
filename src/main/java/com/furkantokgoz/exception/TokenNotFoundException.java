package com.furkantokgoz.exception;

public class TokenNotFoundException extends RuntimeException{

    public TokenNotFoundException(){
        super("Token not found");
    }

    public TokenNotFoundException(String message){
        super(message + " Token not found");
    }
    public TokenNotFoundException(String message, Throwable cause){
        super(message + " Token not found", cause);
    }
    public TokenNotFoundException(Throwable cause){
        super("Token not found", cause);
    }
}
