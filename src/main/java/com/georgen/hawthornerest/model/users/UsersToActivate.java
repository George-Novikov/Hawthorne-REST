package com.georgen.hawthornerest.model.users;

import com.georgen.hawthorne.api.annotations.SingletonEntity;

import java.util.HashSet;
import java.util.Set;

@SingletonEntity
public class UsersToActivate {
    private Set<Integer> users = new HashSet<>();

    public Set<Integer> getUsers() {
        return users;
    }

    public void setUsers(Set<Integer> users) {
        this.users = users;
    }

    public void add(User user){
        add(user.getId());
    }

    public void add(Integer userID){
        this.users.add(userID);
    }
}
