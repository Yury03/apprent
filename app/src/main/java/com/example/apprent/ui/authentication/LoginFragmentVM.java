package com.example.apprent.ui.authentication;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.data.network.AuthenticationImpl;
import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.AuraUser;
import com.example.apprent.domain.usecase.authentication.AuthenticationCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragmentVM extends ViewModel implements Serializable {
    private MainContract.Authentication authentication;

    public LiveData<AuraUser.State> getUserLiveData() {
        return userLiveData;
    }

    private final MutableLiveData<AuraUser.State> userLiveData = new MutableLiveData<>();

    public void initAuthentication(Context context) {
        authentication = new AuthenticationImpl(context);
    }

    public void signIn(String login, String password) {
        authentication.signIn(new AuthenticationCallback.signInCallback() {
            @Override
            public void isAuthorized(AuraUser.State state) {
                Log.i("Login", "OK");
                userLiveData.postValue(state);
            }

            @Override
            public void isNotAuthorized(Exception e, AuraUser.State state) {
                userLiveData.postValue(state);
                Log.i("Login", "NOT OK");
            }
        }, login, password);

    }

    public void signUp(String login, String password) {
        authentication.signUp(new AuthenticationCallback.signUpCallback() {
            @Override
            public void accountIsCreated(AuraUser.State state) {
                Log.i("Login", "signUp: OK(LoginFragmentVM)");
                userLiveData.postValue(state);
            }

            @Override
            public void accountIsNotCreated(Exception e, AuraUser.State state) {
                Log.i("Login", "signUp: NOT OK(LoginFragmentVM)  " + e.toString());
                userLiveData.postValue(state);
            }
        }, login, password);
    }


    public void restoreAccess(AuraUser userCommon) {
        authentication.restoreAccess(new AuthenticationCallback.restoreAccessCallback() {
            @Override
            public void letterWasSent(AuraUser user) {
//                userLiveData.setValue(user.); todo
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