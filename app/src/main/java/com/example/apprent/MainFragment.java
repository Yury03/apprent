package com.example.apprent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.car.ui.recyclerview.RecyclerViewAdapterAdapterV1;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
	Boolean signIn = false;
	private static final String Tag = "MyApp";
	List<ProductIcon> products = new ArrayList<>();
	private RecyclerView recyclerView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(Tag, "onCreate [MainFragment]");
	}
	
	private void setInitialData() {
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductIcon("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setInitialData();
		recyclerView = view.findViewById(R.id.productList);
		// создаем адаптер
		AdapterProductList adapter = new AdapterProductList(this.getContext(), products);
		// устанавливаем для списка адаптер
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
		ImageButton profileButton = view.findViewById(R.id.profile_button);
		ImageButton goToCart = view.findViewById(R.id.cart_button);
		TextView profileNick = view.findViewById(R.id.profile_IN);
		Log.i(Tag, "onViewCreated [MainFragment]");
		goToCart.setOnClickListener(view12 -> {
			Fragment fragment = new CartFragment();
			getParentFragmentManager()
					.beginTransaction()
					.setReorderingAllowed(true)
					.replace(R.id.fragmentContainerView, fragment)
					.commit();
		});
		getParentFragmentManager().setFragmentResultListener("request2",
				this, (requestKey, result) -> {
					signIn = result.getBoolean("isSignIn");
					if (signIn) {
						profileNick.setText("ЮК");
					} else {
						profileNick.setText(R.string.default_nick);
					}
				});
		profileButton.setOnClickListener(view1 -> {
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
//		// Inflate the layout for this fragment
//		View view = inflater.inflate(R.layout.fragment_main, container, false);
//
//		// Add the following lines to create RecyclerView
//		setInitialData();
//		recyclerView = view.findViewById(R.id.productList);
//		recyclerView.setHasFixedSize(true);
//		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//		recyclerView.setAdapter(new AdapterProductList(getContext(), products));
//		Log.i(Tag, "onCreateView [MainFragment]");
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
}