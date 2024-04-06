package com.georgen.hawthornerest.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.georgen.hawthorne.api.annotations.EntityCollection;
import com.georgen.hawthorne.api.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@EntityCollection
public class User implements UserDetails {
    @Id
    private Integer id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String nickname;
    private String avatarFileID;
    private Role role;
    private boolean isExpired;
    private boolean isBlocked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarFileID() {
        return avatarFileID;
    }

    public void setAvatarFileID(String avatarFileID) {
        this.avatarFileID = avatarFileID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isExpired() { return isExpired; }

    public void setExpired(boolean expired) { isExpired = expired; }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.toGrantedAuthorities();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.login;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return !isBlocked();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return !isExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return !isBlocked();
    }

    @JsonIgnore
    public boolean isNew(){
        return this.id == null;
    }
}
