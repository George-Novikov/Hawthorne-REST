package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.controllers.openapi.OpenApi;
import com.georgen.hawthornerest.model.auth.AuthResponse;
import com.georgen.hawthornerest.model.auth.RegistrationRequest;
import com.georgen.hawthornerest.services.AuthenticationService;
import com.georgen.hawthornerest.tools.Responder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register as a new user",
            description = OpenApi.REGISTRATION_DESCRIPTION,
            tags = {OpenApi.AUTH_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The result message and a token",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))))
    public ResponseEntity register(@RequestBody RegistrationRequest request){
        try {
            AuthResponse response = service.register(request);
            return Responder.sendOk(response);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Operation(
            summary = "Login",
            description = OpenApi.LOGIN_DESCRIPTION,
            tags = {OpenApi.AUTH_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The result message and a token",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))))
    public ResponseEntity login(
            @RequestParam(value = "login", defaultValue = "") String login,
            @RequestParam(value = "password", defaultValue = "") String password
    ){
        try {
            AuthResponse response = service.login(login, password);
            return Responder.sendOk(response);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping("/activate/{userID}")
    @Operation(
            summary = "Activate registered user",
            description = OpenApi.ACTIVATION_DESCRIPTION,
            tags = {OpenApi.AUTH_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The result message and a token",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))))
    public ResponseEntity activate(@PathVariable(value = "userID") Integer userID){
        try {
            AuthResponse response = service.activate(userID);
            return Responder.sendOk(response);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

}
