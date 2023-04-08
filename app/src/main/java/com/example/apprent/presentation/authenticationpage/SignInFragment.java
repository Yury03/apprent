package com.example.apprent.presentation.authenticationpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.apprent.R;


public class SignInFragment extends Fragment {
    Button goToSignUp;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goToSignUp = view.findViewById(R.id.go_to_sign_up);
        goToSignUp.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
}