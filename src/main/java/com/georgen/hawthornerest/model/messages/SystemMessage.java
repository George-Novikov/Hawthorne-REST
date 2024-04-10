package com.georgen.hawthornerest.model.messages;

public enum SystemMessage implements Describing {
    AUTH_REQUIRED("Authentication and authorization are required for all REST API methods except **/auth endpoint."),
    NO_AUTH_REQUIRED("REST API methods do not require authentication or authorization."),
    NO_REQUIRED_PARAMS("Required request parameters are missing.");

    private String description;

    SystemMessage(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
