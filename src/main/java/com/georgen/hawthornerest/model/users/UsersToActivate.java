package com.georgen.hawthornerest.model.users;

import com.georgen.hawthorne.api.annotations.SingletonEntity;

import java.util.HashSet;
import java.util.Set;

@SingletonEntity
public class UsersToActivate {
    private Set<Integer> userIDs = new HashSet<>();

    public Set<Integer> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(Set<Integer> userIDs) {
        this.userIDs = userIDs;
    }

    public void add(User user){
        add(user.getId());
    }

    public void add(Integer userID){
        this.userIDs.add(userID);
    }

    public void remove(User user){
        this.userIDs.remove(user.getId());
    }

    public void remove(Integer userID){
        this.userIDs.remove(userID);
    }
}
