package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.controllers.openapi.OpenApi;
import com.georgen.hawthornerest.model.constants.SystemMessage;
import com.georgen.hawthornerest.model.settings.Settings;
import com.georgen.hawthornerest.services.SettingsService;
import com.georgen.hawthornerest.tools.Responder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/settings")
public class SettingsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    private SettingsService service;

    public SettingsController(SettingsService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Save settings",
            description = OpenApi.SAVE_SETTINGS_DESCRIPTION,
            tags = {OpenApi.SETTINGS_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Message about successful saving",
                    content = @Content(schema = @Schema(implementation = String.class))))
    public ResponseEntity save(@RequestBody Settings settings){
        try {
            Settings savedSettings = service.save(settings);
            return Responder.sendOk(SystemMessage.SETTINGS_SAVE_SUCCESS);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping
    @Operation(
            summary = "Get settings",
            description = OpenApi.GET_SETTINGS_DESCRIPTION,
            tags = {OpenApi.SETTINGS_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Settings object",
                    content = @Content(schema = @Schema(implementation = Settings.class))))
    public ResponseEntity get(){
        try {
            Settings settings = service.get();
            return Responder.sendOk(settings);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }
}
