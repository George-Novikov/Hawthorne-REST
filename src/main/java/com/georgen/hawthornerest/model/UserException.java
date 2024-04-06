package com.georgen.hawthornerest.model;

public class UserException extends Exception {
    public UserException(String message){
        super(message);
    }

    public UserException(Throwable cause){
        super(cause);
    }
}
