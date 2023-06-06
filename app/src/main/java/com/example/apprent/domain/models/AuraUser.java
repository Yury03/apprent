package com.example.apprent.domain.models;

public class AuraUser {

    public enum State {
        INIT(0),
        USER_SIGN_IN(1),
        SIGN_IN_ERROR(-1),
        SIGN_UP(2),
        SIGN_UP_ERROR(-2),
        RESTORE_ACCESS(3),
        RESTORE_ACCESS_ERROR(-3),
        ADMIN_SIGN_IN(4),
        ;

        public final int stateId;

        State(int stateId) {
            this.stateId = stateId;
        }
    }

    private final String login;//email or phone number
    private final String password;

    private State state = State.INIT;

    public AuraUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
