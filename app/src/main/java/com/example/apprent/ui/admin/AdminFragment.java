package com.example.apprent.ui.admin;

import static com.example.apprent.domain.models.AuraUser.State.USER_NOT_SIGN_IN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.R;
import com.example.apprent.data.network.orders.GetOrdersImpl;
import com.example.apprent.domain.usecase.orders.get.GetOrdersAdmin;
import com.example.apprent.ui.admin.adapters.OrdersListAdapter;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityVM;


public class AdminFragment extends Fragment {
    private RecyclerView ordersList;
    private MainActivityVM mainActivityVM;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetOrdersImpl getOrders = new GetOrdersImpl(getContext());
        GetOrdersAdmin getOrdersAdmin = new GetOrdersAdmin(getOrders);
        ordersList = view.findViewById(R.id.admin_orders);
        getOrdersAdmin.execute(orders -> {
            OrdersListAdapter adapter = new OrdersListAdapter(orders, getContext());
            ordersList.setAdapter(adapter);
        }, "expected");
        mainActivityVM = ((MainActivity) getActivity()).getVM();//todo   | ? |
        mainActivityVM.getBottomNavigationView().setVisibility(View.GONE);
        Button logout = view.findViewById(R.id.logout_button_admin);
        logout.setOnClickListener(v -> {
            mainActivityVM.getNavController().navigate(R.id.authenticationFragment);
            mainActivityVM.getSharedPreferences()
                    .edit()
                    .putInt(getResources().getString(R.string.saved_log_in_key), USER_NOT_SIGN_IN.stateId)
                    .apply();
        });
//        ordersList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true));//todo getContext()?
        ordersList.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }
}