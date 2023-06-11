package com.example.apprent.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.R;
import com.example.apprent.data.network.orders.GetOrdersImpl;
import com.example.apprent.domain.usecase.orders.get.GetOrdersAdmin;
import com.example.apprent.ui.admin.adapters.OrdersListAdapter;


public class AdminFragment extends Fragment {
    private RecyclerView ordersList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetOrdersImpl getOrders = new GetOrdersImpl();
        GetOrdersAdmin getOrdersAdmin = new GetOrdersAdmin(getOrders);
        ordersList = view.findViewById(R.id.orders_list);
        getOrdersAdmin.execute(orders -> {
            OrdersListAdapter adapter = new OrdersListAdapter(orders, getContext());
            ordersList.setAdapter(adapter);
        });
        ordersList.setLayoutManager(new LinearLayoutManager(requireContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }
}