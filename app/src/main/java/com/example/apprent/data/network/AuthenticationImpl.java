package com.example.apprent.data.network;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.AuraUser;
import com.example.apprent.domain.usecase.AuthenticationCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class AuthenticationImpl implements MainContract.Authentication {
    private final FirebaseAuth firebaseAuth;

    public AuthenticationImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;

    }


    @Override
    public void restoreAccess(AuthenticationCallback.restoreAccessCallback callback, AuraUser user) {

    }

    @Override
    public void signIn(AuthenticationCallback.signInCallback callback, AuraUser auraUser) {
//        String phoneNum = "+16505554567";
//        String testVerificationCode = "123456";
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
//                .setPhoneNumber(phoneNum)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(this)
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onCodeSent(@NonNull String verificationId,
//                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        // Save the verification id somewhere
//                        // ...
//
//                        // The corresponding whitelisted code above should be used to complete sign-in.
////                        MainActivity.this.enableUserManuallyInputCode();
//                    }
//
//                    @Override
//                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                        // Sign in with the credential
//                        // ...
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                        // ...
//                    }
//                })
//                .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
        String email = auraUser.getLogin();
        String password = auraUser.getPassword();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if(firebaseUser!=null) {
                            @SuppressLint("RestrictedApi") User user = new User(firebaseUser.getUid());//todo
                        }
                        //todo user->auraUser
                        callback.isAuthorized(auraUser);
                    } else {
                        callback.isNotAuthorized(task.getException());
                    }
                });


    }




//private void sendMessageToPhone()


    @Override
    public void signUp(AuthenticationCallback.signUpCallback callback, AuraUser auraUser) {
        String email = auraUser.getLogin();
        String password = auraUser.getPassword();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });
    }
}
