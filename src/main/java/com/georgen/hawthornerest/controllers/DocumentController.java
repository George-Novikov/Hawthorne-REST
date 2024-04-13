package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.controllers.openapi.OpenApi;
import com.georgen.hawthornerest.model.documents.Document;
import com.georgen.hawthornerest.model.exceptions.NoContentException;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.services.DocumentService;
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

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Save document",
            description = OpenApi.SAVE_DOCUMENT_DESCRIPTION,
            tags = {OpenApi.DOCUMENT_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Echoed document information with generated id",
                    content = @Content(schema = @Schema(implementation = Document.class))))
    public ResponseEntity save(@RequestBody Document document){
        try {
            Document savedDocument = service.save(document);
            return Responder.sendOk(savedDocument);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping
    @Operation(
            summary = "Get document",
            description = OpenApi.GET_DOCUMENT_DESCRIPTION,
            tags = {OpenApi.DOCUMENT_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Document object",
                    content = @Content(schema = @Schema(implementation = Document.class))))
    public ResponseEntity get(@RequestParam Long id){
        try {
            Document document = service.get(id);
            if (document == null) throw new NoContentException();
            return Responder.sendOk(document);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @DeleteMapping
    @Operation(
            summary = "Delete document",
            description = OpenApi.DELETE_DOCUMENT_DESCRIPTION,
            tags = {OpenApi.DOCUMENT_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "true / false",
                    content = @Content(schema = @Schema(implementation = Boolean.class))))
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
    @Operation(
            summary = "List documents",
            description = OpenApi.LIST_DOCUMENTS_DESCRIPTION,
            tags = {OpenApi.DOCUMENT_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "JSON array of documents",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Document.class)))))
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
    @Operation(
            summary = "Count documents",
            description = OpenApi.COUNT_DOCUMENTS_DESCRIPTION,
            tags = {OpenApi.DOCUMENT_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Number of documents in storage",
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
}
