package com.example.apprent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.apprent.R;
import com.example.apprent.fragments.MainFragment;

public class LoginFragment extends Fragment {
	Boolean signIn;
	
	private static final String Tag = "MyApp";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(Tag, "onCreate [LoginFragment]");
		
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button signInButton = view.findViewById(R.id.signIn);
		Button signUpButton = view.findViewById(R.id.signUp);
		Log.i(Tag, "onViewCreated [LoginFragment]");
		signInButton.setOnClickListener(view12 -> {
			signIn = !(signIn);
			if (signIn) {
				signInButton.setText("Выйти");
			} else {
				signInButton.setText("Войти");
			}
			Log.i(Tag, signIn.toString());
		});
		try {
			signIn = getArguments() != null && getArguments().getBoolean("isSignIn");
			if (signIn) {
				signInButton.setText("Выйти");
			} else {
				signInButton.setText("Войти");
			}
		} catch (Exception exception) {
			Log.e("MyApp", exception.getMessage());
		}
		
		
		signUpButton.setOnClickListener(view1 -> {
			Bundle bundle = new Bundle();
			bundle.putBoolean("isSignIn", signIn);
			Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_mainFragment, bundle);
		});
		
		
	}
	
	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		Log.i(Tag, "onViewStateRestored [LoginFragment]");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(Tag, "onStart [LoginFragment]");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(Tag, "onResume [LoginFragment]");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.i(Tag, "onPause [LoginFragment]");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.i(Tag, "onStop [LoginFragment]");
	}
	
	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		
		Log.i(Tag, "onSaveInstanceState [LoginFragment]");
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i(Tag, "onDestroyView [LoginFragment]");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(Tag, "onDestroy [LoginFragment]");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(Tag, "onCreateView [LoginFragment]");
		return inflater.inflate(R.layout.fragment_login, container, false);
	}
}