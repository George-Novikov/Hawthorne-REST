package com.georgen.hawthornerest.model.system;

import com.georgen.hawthorne.api.annotations.SingletonEntity;

@SingletonEntity
public class Settings {
    private boolean isAuthRequired;

    public boolean isAuthRequired() {
        return isAuthRequired;
    }

    public void setAuthRequired(boolean authRequired) {
        isAuthRequired = authRequired;
    }
}
