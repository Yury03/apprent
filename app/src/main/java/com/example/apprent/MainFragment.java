package com.example.apprent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainFragment extends Fragment {
    AtomicBoolean signIn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signIn = new AtomicBoolean(true);
        getParentFragmentManager().setFragmentResultListener("requestKey",
                this,
                (requestKey, bundle) -> signIn.set(bundle.getBoolean("isSignIn")));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.button2);
        TextView textView = view.findViewById(R.id.textView4);

        if (signIn.get()) {
            textView.setText("Вход выполнен");
        } else {
            textView.setText("Вход не выполнен");
        }

        button.setOnClickListener(view1 -> {
            Fragment fragment = new LoginFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}