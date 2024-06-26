package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.controllers.openapi.OpenApi;
import com.georgen.hawthornerest.model.users.ActivationResult;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.model.users.UsersToActivate;
import com.georgen.hawthornerest.services.UserService;
import com.georgen.hawthornerest.tools.Responder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.georgen.hawthornerest.model.constants.SystemMessage.NO_REQUIRED_PARAMS;

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
                    content = @Content(schema = @Schema(implementation = User.class))))
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
            description = OpenApi.USER_GET_DESCRIPTION,
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Complete user information",
                    content = @Content(schema = @Schema(implementation = User.class))))
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
            description = OpenApi.USER_DELETE_DESCRIPTION,
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "true / false",
                    content = @Content(schema = @Schema(implementation = Boolean.class))))
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
    @Operation(
            summary = "List users",
            description = OpenApi.LIST_USERS_DESCRIPTION,
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "JSON array of user objects",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))))
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
    @Operation(
            summary = "Count users",
            description = OpenApi.COUNT_USERS_DESCRIPTION,
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Number of stored users",
                    content = @Content(schema = @Schema(implementation = Long.class))))
    public ResponseEntity count(){
        try {
            long count = service.count();
            return Responder.sendOk(count);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping("/bulkActivation")
    @Operation(
            summary = "Get a list of blocked user ids",
            description = OpenApi.GET_ACTIVATION_LIST_DESCRIPTION,
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "A list of user ids that are currently blocked",
                    content = @Content(schema = @Schema(implementation = UsersToActivate.class))))
    public ResponseEntity getActivationList(){
        try {
            UsersToActivate activationList = service.getActivationList();
            return Responder.sendOk(activationList);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @PostMapping("/bulkActivation")
    @Operation(
            summary = "Activate a list users",
            description = OpenApi.BULK_ACTIVATION_DESCRIPTION,
            tags = {OpenApi.USER_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "An object with two arrays: activated user ids and an not found user ids",
                    content = @Content(schema = @Schema(implementation = ActivationResult.class))))
    public ResponseEntity activateList(@RequestBody UsersToActivate activationList){
        try {
            ActivationResult result = service.activateList(activationList);
            return Responder.sendOk(result);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }
}
