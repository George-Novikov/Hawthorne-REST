package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.model.FileContainer;
import com.georgen.hawthornerest.services.FileService;
import com.georgen.hawthornerest.tools.Responder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity save(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "description", defaultValue = "", required = false) String description,
            @RequestParam(value = "file") MultipartFile file
    ){
        try {
            FileContainer fileContainer = service.save(id, description, file);
            return Responder.sendOk(fileContainer);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }
}
