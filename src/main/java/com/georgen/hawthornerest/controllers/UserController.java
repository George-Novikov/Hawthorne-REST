package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.controllers.openapi.OpenApi;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.model.users.UsersToActivate;
import com.georgen.hawthornerest.services.UserService;
import com.georgen.hawthornerest.tools.Responder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.georgen.hawthornerest.model.messages.SystemMessage.NO_REQUIRED_PARAMS;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Save user",
            description = OpenApi.USER_SAVE_DESCRIPTION,
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Echoed user information with generated id",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    )
    public ResponseEntity save(@RequestBody User user){
        try {
            User savedUser = service.save(user);
            return Responder.sendOk(savedUser);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping
    @Operation(
            summary = "Get user",
            description = "Get user",
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Complete user information",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    )
    public ResponseEntity get(
            @RequestParam(value = "id", defaultValue = "0") int id,
            @RequestParam(value = "login", defaultValue = "") String login
    ){
        if (id == 0 && login.isEmpty()) return Responder.sendBadRequest(NO_REQUIRED_PARAMS);
        try {
            User savedUser = id != 0 ? service.get(id) : service.getByLogin(login);
            return Responder.sendOk(savedUser);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @DeleteMapping
    @Operation(
            summary = "Delete user",
            description = "Get complete user information.",
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "true / false",
                    content = @Content(schema = @Schema(implementation = Boolean.class))
            )
    )
    public ResponseEntity delete(@RequestParam(value = "id", defaultValue = "0") int id){
        if (id == 0) return Responder.sendBadRequest(NO_REQUIRED_PARAMS);
        try {
            boolean isDeleted = service.delete(id);
            return Responder.sendOk(isDeleted);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity list(
            @RequestParam(value = "limit", defaultValue = "0") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset
    ){
        try {
            List<User> list = service.list(limit, offset);
            return Responder.sendOk(list);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping("/count")
    public ResponseEntity count(){
        try {
            long count = service.count();
            return Responder.sendOk(count);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @PostMapping("/activateList")
    public ResponseEntity activateList(@RequestBody UsersToActivate activationList){
        try {

        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }
}
