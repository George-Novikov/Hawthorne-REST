package com.georgen.hawthornerest.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.georgen.hawthornerest.model.messages.Describing;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthResponse {
    private String message;
    private String token;

    public AuthResponse() {}
    public AuthResponse(Describing describing) {
        this.message = describing.getDescription();
    }
    public AuthResponse(String token) {
        this.message = "Successful authentication. Welcome!";
        this.token = token;
    }
    public AuthResponse(Describing describing, String token) {
        this.message = describing.getDescription();
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
