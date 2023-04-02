package com.example.apprent.presetation.cartpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.apprent.R;
import com.example.apprent.presetation.mainpage.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
	List<ProductItem> products = new ArrayList<>();
	private ListView listView;
	private static final String Tag = "MyApp";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(Tag, "onCreate [CartFragment]");
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.i(Tag, "onViewCreated [CartFragment]");


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