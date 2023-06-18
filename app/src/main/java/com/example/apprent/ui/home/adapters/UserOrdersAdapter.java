package com.example.apprent.ui.home.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.R;


import com.example.apprent.domain.models.Order;

import java.util.List;

public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.ViewHolder> {
    private final List<Order> orders;
    private final Context context;
    private final String counterText;
    private final String idText;
    private final String startRentText;
    private final String isPaidText;
    private final String isNotPaidText;
    private final String isDeliveryText;
    private final String isPickupText;


    public UserOrdersAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
        counterText = context.getString(R.string.order_item_number_products);
        idText = context.getString(R.string.order_item_id);
        startRentText = context.getString(R.string.order_item_rent_start);
        isPaidText = context.getString(R.string.order_item_is_paid);
        isNotPaidText = context.getString(R.string.order_item_is_not_paid);
        isDeliveryText = context.getString(R.string.order_item_is_delivery);
        isPickupText = context.getString(R.string.order_item_is_pickup);
    }

    @NonNull
    @Override
    public UserOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new UserOrdersAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")//todo
    @Override
    public void onBindViewHolder(@NonNull UserOrdersAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.id.setText(idText + order.getId());
        holder.counter.setText(counterText + order.getQuantityProducts());
        holder.startRent.setText(startRentText + " 08.06.23");//todo

        if (order.isDelivery()) {
            holder.isDelivery.setText(isDeliveryText);
        } else {
            holder.isDelivery.setText(isPickupText);
        }
        if (order.isPaid()) {
            holder.isPaid.setText(isPaidText);
        } else {
            holder.isPaid.setText(isNotPaidText);
        }
    }


    @Override
    public int getItemCount() {
        Log.e("getorders", String.valueOf(orders.size()));
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView isDelivery;
        TextView isPaid;
        TextView startRent;
        TextView counter;//todo

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.order_id);
            isDelivery = itemView.findViewById(R.id.is_delivery);
            isPaid = itemView.findViewById(R.id.is_paid);
            startRent = itemView.findViewById(R.id.start_rent);
            counter = itemView.findViewById(R.id.number_products);//todo
        }
    }


}
