package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.model.exceptions.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AuthenticationException.class, ExpiredJwtException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleJwtException(Exception exception){
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
