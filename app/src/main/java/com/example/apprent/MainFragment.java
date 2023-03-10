package com.example.apprent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainFragment extends Fragment {
	Boolean signIn = false;
	private static final String Tag = "MyApp";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(Tag, "onCreate [MainFragment]");
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ImageButton profileButton = view.findViewById(R.id.profile_button);

		Log.i(Tag, "onViewCreated [MainFragment]");
		getParentFragmentManager().setFragmentResultListener("request2",
				this, (requestKey, result) -> {
					signIn = result.getBoolean("isSignIn");
					
					if (signIn) {
						profileButton.setText("Вход выполнен");
					} else {
						textView.setText("Вход не выполнен");
					}
				});
//		if (signIn) {
//			textView.setText("Вход выполнен");
//		} else {
//			textView.setText("Вход не выполнен");
//		}
		button.setOnClickListener(view1 -> {
			Bundle bundle = new Bundle();
			bundle.putBoolean("isSignIn", signIn);
			getParentFragmentManager().setFragmentResult(
					"request", bundle);
			Fragment fragment = new LoginFragment();
			getParentFragmentManager()
					.beginTransaction()
					.setReorderingAllowed(true)
					.replace(R.id.fragmentContainerView, fragment)
					.commit();
		});
	}
	
	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		Log.i(Tag, "onViewStateRestored [MainFragment]");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(Tag, "onStart [MainFragment]");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(Tag, "onResume [MainFragment]");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.i(Tag, "onPause [MainFragment]");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.i(Tag, "onStop [MainFragment]");
	}
	
	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(Tag, "onSaveInstanceState [MainFragment]");
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i(Tag, "onDestroyView [MainFragment]");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(Tag, "onDestroy [MainFragment]");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		Log.i(Tag, "onCreateView [MainFragment]");
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
}