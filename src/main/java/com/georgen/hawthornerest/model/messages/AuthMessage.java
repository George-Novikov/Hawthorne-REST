package com.georgen.hawthornerest.model.messages;

public enum AuthMessage implements Describing{
    SUCCESSFUL_AUTH("Successful authentication. Welcome!"),
    CONTACT_ADMIN_RESPONSE("Contact your administrator to activate your account."),
    ADDITIONAL_INFO_RESPONSE("Registration was successful. You will receive additional info about your account activation.");

    private String description;

    AuthMessage(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
