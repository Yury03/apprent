package com.example.apprent.domain.models;

public class AuraUser {
    public static final int SIGN_IN_ERROR = -1;
    public static final int SIGN_IN = 1;
    public static final int SIGN_UP_ERROR = -2;
    public static final int SIGN_UP = 2;
    public static final int RESTORE_ACCESS_ERROR = -3;
    public static final int RESTORE_ACCESS = 3;
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
