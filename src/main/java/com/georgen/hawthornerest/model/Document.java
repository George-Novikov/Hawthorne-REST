package com.georgen.hawthornerest.model;

import com.georgen.hawthorne.api.annotations.EntityCollection;

import java.time.LocalDateTime;
import java.util.List;

@EntityCollection
public class Document {
    private Long id;
    private String title;
    private String text;
    private String authorID;
    private LocalDateTime modificationDate;
    private List<String> attachedFileIDs;
    private List<Role> ownerRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public List<String> getAttachedFileIDs() {
        return attachedFileIDs;
    }

    public void setAttachedFileIDs(List<String> attachedFileIDs) {
        this.attachedFileIDs = attachedFileIDs;
    }

    public List<Role> getOwnerRoles() {
        return ownerRoles;
    }

    public void setOwnerRoles(List<Role> ownerRoles) {
        this.ownerRoles = ownerRoles;
    }
}
