package com.georgen.hawthornerest.model.users;

public enum Permission {
    DOCUMENT_READ("document:read"),
    DOCUMENT_WRITE("document:write"),
    FILE_READ("file:read"),
    FILE_WRITE("file:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private String subject;

    Permission(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
