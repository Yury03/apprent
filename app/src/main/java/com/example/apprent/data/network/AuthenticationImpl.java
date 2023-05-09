package com.example.apprent.data.network;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.AuraUser;
import com.example.apprent.domain.usecase.AuthenticationCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

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
                        user.setState(AuraUser.RESTORE_ACCESS);
                        callback.letterWasSent(user);
                    } else {
                        user.setState(AuraUser.RESTORE_ACCESS_ERROR);
                        callback.letterWasNotSent(task.getException());
                    }
                });
    }

    @Override
    public void signIn(AuthenticationCallback.signInCallback callback, AuraUser auraUser) {
        String email = auraUser.getLogin();
        String password = auraUser.getPassword();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            @SuppressLint("RestrictedApi") User user = new User(firebaseUser.getUid());//todo
                        }
                        auraUser.setState(AuraUser.SIGN_IN);
                        //todo user->auraUser
                        callback.isAuthorized(auraUser);
                    } else {
                        auraUser.setState(AuraUser.SIGN_IN_ERROR);
                        callback.isNotAuthorized(task.getException());
                    }
                });
    }


    @Override
    public void signUp(AuthenticationCallback.signUpCallback callback, AuraUser auraUser) {
        String email = auraUser.getLogin();
        String password = auraUser.getPassword();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        auraUser.setState(AuraUser.SIGN_UP);
                        callback.accountIsCreated(auraUser);
                    } else {
                        auraUser.setState(AuraUser.SIGN_UP_ERROR);
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        callback.accountIsNotCreated(task.getException());
                    }
                });
    }
}
