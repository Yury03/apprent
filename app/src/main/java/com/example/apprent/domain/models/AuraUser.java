package com.example.apprent.domain.models;

public class AuraUser {
    private final String login;//email or phone number
    private final String password;

    private int state = 0;

    public AuraUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
