package com.example.apprent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginFragment extends Fragment {
    Bundle resultBundle = new Bundle();
    AtomicBoolean signIn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signIn = new AtomicBoolean(false);
        getParentFragmentManager()
                .setFragmentResultListener("requestKey", this,
                        ((requestKey, result) -> {
                            signIn.set(resultBundle.getBoolean("isSignIn"));
                        }));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button signInButton = view.findViewById(R.id.signIn);
        Button signUpButton = view.findViewById(R.id.signUp);
        signInButton.setOnClickListener(v -> {
            signIn.set(!(signIn.get()));
            Toast.makeText(this.getContext(), signIn.toString(), Toast.LENGTH_LONG).show();
        });
        signUpButton.setOnClickListener(view1 -> {
            resultBundle.putBoolean("isSignIn", signIn.get());
            getParentFragmentManager().setFragmentResult(
                    "requestKey", resultBundle);
            Fragment fragment = new MainFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}