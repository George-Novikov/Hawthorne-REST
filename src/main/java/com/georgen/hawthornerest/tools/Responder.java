package com.georgen.hawthornerest.tools;

import com.georgen.hawthornerest.model.exceptions.NoContentException;
import com.georgen.hawthornerest.model.constants.Describing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responder {
    public static ResponseEntity sendOk(Object object){
        return ResponseEntity.ok(object);
    }

    public static ResponseEntity sendOk(Describing describing){
        return ResponseEntity.ok(describing.getDescription());
    }

    public static ResponseEntity sendBadRequest(Describing describing){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(describing.getDescription());
    }

    public static ResponseEntity sendError(Exception e){
        if (e instanceof NoContentException) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
