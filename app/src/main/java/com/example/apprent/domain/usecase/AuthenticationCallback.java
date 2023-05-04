package com.example.apprent.domain.usecase;


import com.example.apprent.domain.models.AuraUser;

public interface AuthenticationCallback {
    interface signInCallback{
        void isAuthorized(AuraUser user);
        void isNotAuthorized(Exception e);
    }
    interface signUpCallback{

    }
    interface restoreAccessCallback{
//        void isRestore
    }

}
