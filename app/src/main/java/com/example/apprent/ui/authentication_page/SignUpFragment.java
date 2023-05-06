package com.example.apprent.ui.authentication_page;

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

public class SignUpFragment extends Fragment {

    private Button createAccount;
    private String email;
    private String password1;
    private String password2;
    private Bundle arguments;
    private LoginFragmentVM loginFragmentVM;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAccount = view.findViewById(R.id.signUp);
        EditText loginView = view.findViewById(R.id.email_for_sign_up);
        EditText pass1View = view.findViewById(R.id.password_for_sign_up);
        EditText pass2View = view.findViewById(R.id.repeat_password_for_sign_up);
        arguments = getArguments();
        if (arguments != null) {
            loginFragmentVM = (LoginFragmentVM) arguments.getSerializable("LoginFragmentVM");
        }
        createAccount.setOnClickListener(v -> {
            email = loginView.getText().toString();
            password1 = pass1View.getText().toString();
            password2 = pass2View.getText().toString();
            if (checkForValidity() && loginFragmentVM != null) {
                AuraUser user = new AuraUser(email, password1);
                loginFragmentVM.signUp(user);
            }
        });
    }

    private boolean checkForValidity() {
        boolean resultPassword = false;
        if (password1 != null) {
            if (password1.equals(password2)) {
                resultPassword = password1.length() > 5;
                if (!resultPassword)
                    Toast.makeText(getContext(), getResources().getString(R.string.invalid_password), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.pass1_is_not_equal_pass2), Toast.LENGTH_LONG).show();
            }
        }
        if (!loginFragmentVM.checkEmail(email)) {
            Toast.makeText(getContext(), getResources().getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
        }
        return loginFragmentVM.checkEmail(email) && resultPassword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
}