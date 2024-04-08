package com.georgen.hawthornerest.model.auth;

public class RegistrationRequest {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String nickname;

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

    public boolean isEmpty(){
        return this.login == null || this.login.isEmpty()
                || this.password == null || this.password.isEmpty()
                || this.firstname == null || this.firstname.isEmpty()
                || this.lastname == null || this.lastname.isEmpty();
    }
}
