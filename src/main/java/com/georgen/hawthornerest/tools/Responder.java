package com.georgen.hawthornerest.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responder {
    public static ResponseEntity sendOk(Object object){
        return ResponseEntity.ok(object);
    }

    public static ResponseEntity sendError(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
