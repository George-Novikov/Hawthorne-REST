package com.georgen.hawthornerest.model.constants;

public enum SystemMessage implements Describing {
    AUTH_REQUIRED("Authentication and authorization are required for all REST API methods except /auth/** endpoints."),
    NO_AUTH_REQUIRED("REST API methods do not require authentication or authorization."),
    NO_REQUIRED_PARAMS("Required request parameters are missing."),
    HAS_EMPTY_FIELDS("The entity is not filled with data or has empty fields."),
    SETTINGS_SAVE_SUCCESS("The settings were successfully saved.");

    private String description;

    SystemMessage(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
