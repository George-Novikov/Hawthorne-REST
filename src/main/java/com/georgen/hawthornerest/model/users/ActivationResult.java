package com.georgen.hawthornerest.model.users;

import java.util.HashSet;
import java.util.Set;

public class ActivationResult {
    private Set<Integer> activatedIDs;
    private Set<Integer> notFoundIDs;

    public ActivationResult() {
        this.activatedIDs = new HashSet<>();
        this.notFoundIDs = new HashSet<>();
    }

    public Set<Integer> getActivatedIDs() {
        return activatedIDs;
    }

    public void setActivatedIDs(Set<Integer> activatedIDs) {
        this.activatedIDs = activatedIDs;
    }

    public Set<Integer> getNotFoundIDs() {
        return notFoundIDs;
    }

    public void setNotFoundIDs(Set<Integer> notFoundIDs) {
        this.notFoundIDs = notFoundIDs;
    }

    public void addToActivated(Integer id){
        this.activatedIDs.add(id);
    }

    public void addToNotFound(Integer id){
        this.notFoundIDs.add(id);
    }
}
