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
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainFragment extends Fragment {
	Boolean signIn=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button button = view.findViewById(R.id.button2);
		TextView textView = view.findViewById(R.id.textView4);
		getParentFragmentManager().setFragmentResultListener("request2",
				this, (requestKey, result) -> {
					signIn = result.getBoolean("isSignIn2");
					Toast.makeText(this.getContext(), signIn.toString(), Toast.LENGTH_LONG).show();
					if (signIn) {
						textView.setText("Вход выполнен");
					} else {
						textView.setText("Вход не выполнен");
					}
				});

		
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
}