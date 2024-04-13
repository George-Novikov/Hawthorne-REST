package com.georgen.hawthornerest.controllers;

import com.georgen.hawthornerest.controllers.openapi.OpenApi;
import com.georgen.hawthornerest.model.documents.Document;
import com.georgen.hawthornerest.model.exceptions.NoContentException;
import com.georgen.hawthornerest.model.files.FileContainer;
import com.georgen.hawthornerest.services.FileService;
import com.georgen.hawthornerest.tools.Responder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "Save file",
            description = OpenApi.SAVE_FILE_DESCRIPTION,
            tags = {OpenApi.FILE_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Echoed file metadata with generated id",
                    content = @Content(schema = @Schema(implementation = FileContainer.class))))
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
    @Operation(
            summary = "Get file metadata",
            description = OpenApi.GET_FILE_METADATA_DESCRIPTION,
            tags = {OpenApi.FILE_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "File metadata",
                    content = @Content(schema = @Schema(implementation = FileContainer.class))))
    public ResponseEntity getMetadata(String id){
        try {
            FileContainer fileContainer = service.get(id);
            if (fileContainer == null) throw new NoContentException();
            return Responder.sendOk(fileContainer);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return Responder.sendError(e);
        }
    }

    @GetMapping(value = "/binary")
    @Operation(
            summary = "Get binary data of the file",
            description = OpenApi.GET_BINARY_FILE_DESCRIPTION,
            tags = {OpenApi.FILE_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Byte array",
                    content = @Content(schema = @Schema(implementation = byte[].class))))
    public ResponseEntity getBinary(String id){
        try {
            FileContainer fileContainer = service.get(id);
            if (fileContainer == null) throw new NoContentException();

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
    @Operation(
            summary = "Delete file",
            description = OpenApi.DELETE_FILE_DESCRIPTION,
            tags = {OpenApi.FILE_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "true / false",
                    content = @Content(schema = @Schema(implementation = Boolean.class))))
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
    @Operation(
            summary = "List files (metadata)",
            description = OpenApi.LIST_FILES_DESCRIPTION,
            tags = {OpenApi.FILE_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "JSON array of files (metadata)",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FileContainer.class)))))
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
    @Operation(
            summary = "Count files",
            description = OpenApi.COUNT_FILES_DESCRIPTION,
            tags = {OpenApi.FILE_TAG},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Number of stored files",
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
