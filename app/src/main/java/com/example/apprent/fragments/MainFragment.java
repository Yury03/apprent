package com.example.apprent.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.R;
import com.example.apprent.adapters.AdapterProductList;
import com.example.apprent.listClasses.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    Boolean signIn = false;
    private static final String Tag = "MyApp";
    SharedPreferences sharedPreferences;
    List<ProductItem> products = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Tag, "onCreate [MainFragment]");
        Context context = getActivity();
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        }
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
        signIn = sharedPreferences.getBoolean(getString(R.string.saved_log_in_key), false);
        if (signIn) {
            profileNick.setText("ЮК");
        } else {
            profileNick.setText(R.string.default_nick);
        }
        Log.i(Tag, "onViewCreated [MainFragment]");
        profileButton.setOnClickListener(view1 -> {
            Navigation
                    .findNavController(view1)
                    .navigate(R.id.action_mainFragment_to_loginFragment2);
        });
        goToCart.setOnClickListener(view1 -> Navigation
                .findNavController(view1)
                .navigate(R.id.action_mainFragment_to_cartFragment));
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