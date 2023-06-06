package com.example.apprent.data.network;

import android.util.Log;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.AuraUser;
import com.example.apprent.domain.usecase.AuthenticationCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationImpl implements MainContract.Authentication {

    private final FirebaseAuth firebaseAuth;
    private final String TAG = "Login";

    public AuthenticationImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;

    }


    @Override
    public void restoreAccess(AuthenticationCallback.restoreAccessCallback callback, AuraUser user) {
        String emailAddress = user.getLogin();
        firebaseAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                        user.setState(AuraUser.State.RESTORE_ACCESS);
                        callback.letterWasSent(user);
                    } else {
                        user.setState(AuraUser.State.RESTORE_ACCESS_ERROR);
                        callback.letterWasNotSent(task.getException());
                    }
                });
    }

    @Override
    public void signIn(AuthenticationCallback.signInCallback callback, String login, String password) {
        firebaseAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser.getUid().equals("OHZ6UODMfFbp9Vd42ETGC8ZbwYw1")) {
                            callback.isAuthorized(AuraUser.State.ADMIN_SIGN_IN);
                        }
                        callback.isAuthorized(AuraUser.State.USER_SIGN_IN);
                    } else {
                        callback.isNotAuthorized(task.getException(), AuraUser.State.SIGN_IN_ERROR);
                    }
                });
    }


    @Override
    public void signUp(AuthenticationCallback.signUpCallback callback, String login, String password) {
        firebaseAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        callback.accountIsCreated(AuraUser.State.SIGN_UP);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        callback.accountIsNotCreated(task.getException(), AuraUser.State.SIGN_UP_ERROR);
                    }
                });
    }
}
