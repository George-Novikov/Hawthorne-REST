package com.georgen.hawthornerest.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.georgen.hawthorne.api.annotations.EntityCollection;
import com.georgen.hawthorne.api.annotations.Id;
import com.georgen.hawthornerest.model.exceptions.UserException;

import java.util.HashMap;
import java.util.Map;

@EntityCollection
public class UserIndex {
    @Id
    private Integer id;
    private Map<String, Integer> index = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Integer> getIndex() {
        return index;
    }

    public void setIndex(Map<String, Integer> index) {
        this.index = index;
    }

    public void add(User user) throws UserException {
        if (user.isNew()) throw new UserException("To be stored in the index, the user must have an id.");
        this.index.put(user.getLogin(), user.getId());
    }

    @JsonIgnore
    public boolean isOverwhelmed(){
        return index.size() >= 1000;
    }
}
