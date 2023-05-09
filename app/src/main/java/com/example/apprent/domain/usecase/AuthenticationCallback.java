package com.example.apprent.domain.usecase;


import com.example.apprent.domain.models.AuraUser;

public interface AuthenticationCallback {
    interface signInCallback {
        void isAuthorized(AuraUser user);

        void isNotAuthorized(Exception e);
    }

    interface signUpCallback {
        void accountIsCreated(AuraUser user);

        void accountIsNotCreated(Exception e);
    }

    interface restoreAccessCallback {
        void letterWasSent(AuraUser user);
        void letterWasNotSent(Exception e);
    }

}
