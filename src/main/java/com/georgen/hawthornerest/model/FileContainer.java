package com.georgen.hawthornerest.model;

import com.georgen.hawthorne.api.annotations.BinaryData;
import com.georgen.hawthorne.api.annotations.EntityCollection;
import com.georgen.hawthorne.api.annotations.Id;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@EntityCollection
public class FileContainer {
    @Id
    private String id;
    private String fileName;
    private String mimeType;
    private String description;
    @BinaryData
    private byte[] binaryData;

    public FileContainer() {}

    public FileContainer(String id, String description, MultipartFile file) throws IOException {
        this.id = id;
        this.description = description;
        this.fileName = file.getOriginalFilename();
        this.mimeType = file.getContentType();
        this.binaryData = file.getBytes();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }
}
