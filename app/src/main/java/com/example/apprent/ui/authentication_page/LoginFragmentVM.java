package com.example.apprent.ui.authentication_page;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.data.network.AuthenticationImpl;
import com.example.apprent.domain.models.AuraUser;
import com.example.apprent.domain.usecase.AuthenticationCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragmentVM extends ViewModel implements Serializable {
    AuthenticationImpl authentication;

    public LiveData<AuraUser> getUserLiveData() {
        return userLiveData;
    }

    MutableLiveData<AuraUser> userLiveData = new MutableLiveData<>();


    public LoginFragmentVM() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        authentication = new AuthenticationImpl(mAuth);
    }


    public void signIn(AuraUser userCommon) {
        authentication.signIn(new AuthenticationCallback.signInCallback() {
            @Override
            public void isAuthorized(AuraUser user) {
                Log.i("Login", "OK");
                userLiveData.postValue(user);
            }

            @Override
            public void isNotAuthorized(Exception e) {
                //todo userLiveData.postValue(user);
                Log.i("Login", "NOT OK");
            }
        }, userCommon);

    }

    public void signUp(AuraUser userCommon) {
        authentication.signUp(new AuthenticationCallback.signUpCallback() {
            @Override
            public void accountIsCreated(AuraUser user) {
                Log.i("Login", "signUp: OK(LoginFragmentVM)");
                userLiveData.postValue(user);

            }

            @Override
            public void accountIsNotCreated(Exception e) {
                Log.i("Login", "signUp: NOT OK(LoginFragmentVM)  " + e.toString());
//                userLiveData.postValue(user); todo
            }
        }, userCommon);
    }


    public void restoreAccess(AuraUser userCommon) {
        authentication.restoreAccess(new AuthenticationCallback.restoreAccessCallback() {
            @Override
            public void letterWasSent(AuraUser user) {
                userLiveData.setValue(user);
                Log.i("Login", "restore access OK");
            }

            @Override
            public void letterWasNotSent(Exception e) {

            }
        }, userCommon);
    }

    public boolean checkEmail(String email) {
        boolean result = false;
        if (email != null) {
            String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%" +
                    "&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x" +
                    "21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x" +
                    "7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?" +
                    ":[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9]" +
                    "[0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9" +
                    "-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\" +
                    "x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            result = matcher.matches();
        }
        return result;
    }
}