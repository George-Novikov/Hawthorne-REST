package com.georgen.hawthornerest.model.exceptions;

public class NoContentException extends Exception {
    public NoContentException(){
        super();
    }

    public NoContentException(String message) {
        super(message);
    }
}
