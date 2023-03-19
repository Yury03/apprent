package com.example.apprent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.apprent.adapters.AdapterProductList;
import com.example.apprent.listClasses.ProductItem;
import com.example.apprent.R;


import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
	Boolean signIn = false;
	private static final String Tag = "MyApp";
	List<ProductItem> products = new ArrayList<>();
	private RecyclerView recyclerView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(Tag, "onCreate [MainFragment]");
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setInitialData();
		recyclerView = view.findViewById(R.id.productList);
		AdapterProductList adapter = new AdapterProductList(this.getContext(), products);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
		ImageButton profileButton = view.findViewById(R.id.profile_button);
		ImageButton goToCart = view.findViewById(R.id.cart_button);
		TextView profileNick = view.findViewById(R.id.profile_IN);
		try {
			signIn = getArguments() != null && getArguments().getBoolean("isSignIn");
			if (signIn) {
				profileNick.setText("ЮК");
			} else {
				profileNick.setText(R.string.default_nick);
			}
		} catch (Exception exception) {
			Log.e("MyApp", exception.getMessage());
		}
		Log.i(Tag, "onViewCreated [MainFragment]");
		profileButton.setOnClickListener(view1 -> {
			Bundle bundle=new Bundle();
			bundle.putBoolean("isSignIn", signIn);
			Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_loginFragment2, bundle);
			
		}
		);
		goToCart.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.action_mainFragment_to_cartFragment));
	}
	
	
	private void setInitialData() {
		for (int i = 0; i < 8; i++) {
			products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
			products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
}