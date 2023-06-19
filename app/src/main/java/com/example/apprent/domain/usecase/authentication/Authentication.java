package com.example.apprent.domain.usecase.authentication;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.AuraUser;


public class Authentication {

    private final MainContract.Authentication authenticationRepository;

    public Authentication(MainContract.Authentication authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void signIn(AuthenticationCallback.signInCallback callback, AuraUser user) {
        authenticationRepository.signIn(callback, user.getLogin(), user.getPassword());
    }

    public void signUp(AuthenticationCallback.signUpCallback callback, AuraUser user) {
        authenticationRepository.signUp(callback, user.getLogin(), user.getPassword());
    }

    public void restoreAccess(AuthenticationCallback.restoreAccessCallback callback, AuraUser user) {
        authenticationRepository.restoreAccess(callback, user);
    }
}
