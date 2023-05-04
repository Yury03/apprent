package com.example.apprent.domain.models;

public class AuraUser {
    private final String login;//email or phone number
    private final String password;


    public AuraUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
