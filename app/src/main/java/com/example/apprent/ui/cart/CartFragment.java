package com.example.apprent.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.R;
import com.example.apprent.data.cart_database.CartDatabase;
import com.example.apprent.ui.cart.adapters.CartListAdapter;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityVM;

public class CartFragment extends Fragment {
    private CartFragmentVM vm;
    private MainActivityVM mainActivityVM;
    private CartDatabase cartDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        int marginBottom = 50;
        if (getArguments() != null) {
            marginBottom = getArguments().getInt("marginBottom");
        }
        vm = new ViewModelProvider(this).get(CartFragmentVM.class);
        mainActivityVM = ((MainActivity) getActivity()).getVM();//todo   | ? |
        cartDatabase = mainActivityVM.getCartDatabase();
        RecyclerView recyclerView = view.findViewById(R.id.product_list_cart);
        LinearLayout emptyCartLayer = view.findViewById(R.id.layer_empty_cart);
        LinearLayout orderingLayer = view.findViewById(R.id.ordering_layer);
        ((FrameLayout.LayoutParams) view.getLayoutParams()).setMargins(0, 0, 0, marginBottom);
        ImageButton orderingButton = view.findViewById(R.id.go_to_ordering);
        TextView finalPrice = view.findViewById(R.id.final_price_ordering);
        vm.loadCartProductList(cartDatabase);
        vm.setMainActivity(mainActivityVM);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        orderingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vm.getCartProductList().observe(getViewLifecycleOwner(), cartProductEntities -> {
            if (cartProductEntities.size() > 0) {
                emptyCartLayer.setVisibility(View.GONE);
                CartListAdapter adapter = new CartListAdapter(vm, getContext(), fragmentManager);
                adapter.setCartProducts(cartProductEntities);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
                Log.e("room", "observer");
                recyclerView.setVisibility(View.VISIBLE);
                vm.getFinalPrice().observe(getViewLifecycleOwner(), s -> finalPrice.setText(s + getString(R.string.currency)));
            } else {
                recyclerView.setVisibility(View.GONE);
                orderingLayer.setVisibility(View.GONE);
                emptyCartLayer.setVisibility(View.VISIBLE);

            }
        });


        return view;
    }

}