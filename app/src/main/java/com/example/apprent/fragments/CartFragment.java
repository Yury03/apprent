package com.example.apprent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.example.apprent.R;
import com.example.apprent.adapters.AdapterCartList;
import com.example.apprent.adapters.AdapterProductList;
import com.example.apprent.listClasses.ProductItem;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

public class CartFragment extends Fragment {
	List<ProductItem> products = new ArrayList<>();
	private ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("MyApp", "onCreate [CartFragment]");
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ImageButton backToMain = view.findViewById(R.id.back_button_cart);
		setInitialData();
		Log.i("MyApp", "onViewCreated [CartFragment]");
		listView = view.findViewById(R.id.cartProducts);
		AdapterCartList adapter = new AdapterCartList(this.getContext(), R.layout.product_item, products);
		listView.setOnItemClickListener((adapterView, view12, i, l) -> Log.i("MyApp", "onItemClickListener"));
		listView.setAdapter(adapter);
		backToMain.setOnClickListener(view1 ->{
			Navigation.findNavController(view1).navigate(R.id.action_cartFragment_to_mainFragment);
			Log.i("MyApp", "navigation: cart fragment replace to main fragment");
		});
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
		return inflater.inflate(R.layout.fragment_cart, container, false);
	}
}