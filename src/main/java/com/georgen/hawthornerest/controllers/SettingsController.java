package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.model.system.Settings;
import com.georgen.hawthornerest.services.SettingsService;
import com.georgen.hawthornerest.tools.Responder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity save(@RequestBody Settings settings){
        try {
            Settings savedSettings = service.save(settings);
            return Responder.sendOk(savedSettings);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping
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
