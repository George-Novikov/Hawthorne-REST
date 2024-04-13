package com.georgen.hawthornerest.model.exceptions;

import com.georgen.hawthornerest.model.constants.Describing;

public class AuthException extends Exception {
    public AuthException(){
        super();
    }
    public AuthException(String message){
        super(message);
    }

    public AuthException(Describing describing){
        super(describing.getDescription());
    }
}
