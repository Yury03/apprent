package com.example.apprent.ui.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apprent.R;
import com.example.apprent.domain.models.AuraUser;


public class SignInFragment extends Fragment {
    private String email;
    private String password;
    private Bundle arguments;
    private LoginFragmentVM loginFragmentVM;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText emailEditText = view.findViewById(R.id.email_for_sign_in);
        EditText passwordEditText = view.findViewById(R.id.password_for_sign_in);
        Button logIn = view.findViewById(R.id.signIn);
        Button restoreAccess = view.findViewById(R.id.forgot_password_for_sign_in);
        arguments = getArguments();
        if (arguments != null) {
            loginFragmentVM = (LoginFragmentVM) arguments.getSerializable("LoginFragmentVM");
        }
        restoreAccess.setOnClickListener(v -> {
            email = emailEditText.getText().toString();
            if (loginFragmentVM != null)
                if (loginFragmentVM.checkEmail(email)) {
                    AuraUser auraUser = new AuraUser(email, null);
                    loginFragmentVM.restoreAccess(auraUser);
                    //todo call fragment forgot pass
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
                }
        });
        logIn.setOnClickListener(v -> {
            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            if (loginFragmentVM != null && checkForValidity()) {
                loginFragmentVM.signIn(email, password);
            }
        });
    }

    private boolean checkForValidity() {
        boolean resultEmail = loginFragmentVM.checkEmail(email), resultPassword = false;
        if (!resultEmail) {
            Toast.makeText(getContext(), getResources().getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
        }
        if (password != null) {
            resultPassword = password.length() > 5;
            if (!resultPassword)
                Toast.makeText(getContext(), getResources().getString(R.string.invalid_password), Toast.LENGTH_LONG).show();
        }
        return resultEmail && resultPassword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
}