package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.model.FileContainer;
import com.georgen.hawthornerest.services.FileService;
import com.georgen.hawthornerest.tools.Responder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/metadata")
    public ResponseEntity getMetadata(String id){
        try {
            FileContainer fileContainer = service.get(id);
            return Responder.sendOk(fileContainer);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping(value = "/binary")
    public ResponseEntity getBinary(String id){
        try {
            FileContainer fileContainer = service.get(id);

            MediaType mediaType = MediaType.valueOf(fileContainer.getMimeType());

            return ResponseEntity
                    .status(200)
                    .contentType(mediaType)
                    .body(fileContainer.getBinaryData());
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(String id){
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
            @RequestParam(defaultValue = "0") int limit,
            @RequestParam(defaultValue = "0") int offset
    ){
        try {
            List<FileContainer> files = service.list(limit, offset);
            return Responder.sendOk(files);
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
}
