package com.example.apprent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginFragment extends Fragment {
	Boolean signIn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button signInButton = view.findViewById(R.id.signIn);
		Button signUpButton = view.findViewById(R.id.signUp);
		getParentFragmentManager().setFragmentResultListener("request",
				this, (requestKey, result) -> {
					signIn = result.getBoolean("isSignIn");
					
				});
		signInButton.setOnClickListener(v -> {
			signIn = !(signIn);
			Toast.makeText(this.getContext(), signIn.toString(), Toast.LENGTH_LONG).show();
		});
		signUpButton.setOnClickListener(view1 -> {
			Bundle bundle = new Bundle();
			bundle.putBoolean("isSignIn2", signIn);
			getParentFragmentManager().setFragmentResult(
					"request2", bundle);
			Fragment fragment = new MainFragment();
			getParentFragmentManager()
					.beginTransaction()
					.setReorderingAllowed(true)
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