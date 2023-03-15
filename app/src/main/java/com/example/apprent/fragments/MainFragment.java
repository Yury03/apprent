package com.example.apprent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
	
	
	
	private void setInitialData() {
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake1));
		products.add(new ProductItem("249$", "Nikon EOD. Digital Camera For Good Guys", R.drawable.cake2));
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
}