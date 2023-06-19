package com.example.apprent.domain.usecase.authentication;


import com.example.apprent.domain.models.AuraUser;

public interface AuthenticationCallback {
    interface signInCallback {
        void isAuthorized(AuraUser.State state);

        void isNotAuthorized(Exception e, AuraUser.State state);
    }

    interface signUpCallback {
        void accountIsCreated(AuraUser.State state);

        void accountIsNotCreated(Exception e, AuraUser.State state);
    }

    interface restoreAccessCallback {
        void letterWasSent(AuraUser user);
        void letterWasNotSent(Exception e);
    }

}
