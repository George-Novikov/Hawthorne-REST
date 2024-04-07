package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.model.documents.Document;
import com.georgen.hawthornerest.services.DocumentService;
import com.georgen.hawthornerest.tools.Responder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity save(Document document){
        try {
            Document savedDocument = service.save(document);
            return Responder.sendOk(savedDocument);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping
    public ResponseEntity get(@RequestParam Long id){
        try {
            Document document = service.get(id);
            return Responder.sendOk(document);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id){
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
            List<Document> documents = service.list(limit, offset);
            return Responder.sendOk(documents);
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
