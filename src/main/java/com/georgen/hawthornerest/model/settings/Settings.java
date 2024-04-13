package com.georgen.hawthornerest.model.settings;

import com.georgen.hawthorne.api.annotations.SingletonEntity;

@SingletonEntity
public class Settings {
    private boolean isAuthRequired;
    private boolean isManualUserActivation;

    public boolean isAuthRequired() {
        return isAuthRequired;
    }

    public void setAuthRequired(boolean authRequired) {
        isAuthRequired = authRequired;
    }

    public boolean isManualUserActivation() {
        return isManualUserActivation;
    }

    public void setManualUserActivation(boolean manualUserActivation) {
        isManualUserActivation = manualUserActivation;
    }
}
