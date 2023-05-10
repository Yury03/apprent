package com.example.apprent.ui.cart_page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.R;
import com.example.apprent.data.cart_database.CartDatabase;
import com.example.apprent.ui.cart_page.adapters.CartListAdapter;
import com.example.apprent.ui.main_activity.MainActivityVM;

public class CartFragment extends Fragment {
    private CartFragmentVM vm;
    private MainActivityVM mainActivityVM;
    private CartDatabase cartDatabase;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(CartFragmentVM.class);
        Bundle arguments = getArguments();
        mainActivityVM = (MainActivityVM) arguments.getSerializable("MainActivityVM");//todo
        cartDatabase = mainActivityVM.getCartDatabase();
        RecyclerView recyclerView = view.findViewById(R.id.product_list_cart);
        LinearLayout emptyCartLayer=view.findViewById(R.id.layer_empty_cart);
        vm.loadCartProductList(cartDatabase);
        vm.getCartProductList().observe(getViewLifecycleOwner(), cartProductEntities -> {
            if (cartProductEntities.size() > 0) {
                emptyCartLayer.setVisibility(View.GONE);
                CartListAdapter adapter = new CartListAdapter(getContext());
                adapter.setCartProducts(cartProductEntities);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
                Log.e("room", "observer");
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
                emptyCartLayer.setVisibility(View.VISIBLE);
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
////                List<CartProductEntity> cartProducts = cartDatabase.cartDao().getAllCartProducts();
////                adapter.setCartProducts(cartProducts);
//            }
//        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
}