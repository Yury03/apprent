package com.example.apprent.data.network.orders;

import android.content.Context;

import com.example.apprent.data.CartEntityMapper;
import com.example.apprent.data.OrderMapper;
import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.data.network.orders.models.CartEntityJSON;
import com.example.apprent.data.network.orders.models.OrderJSON;
import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.Order;
import com.example.apprent.domain.usecase.orders.send.SendOrdersCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SendOrdersImpl implements MainContract.SendOrders {
    private final Context context;

    public SendOrdersImpl(Context context) {
        this.context = context;
    }

    public void sendOrders(SendOrdersCallback callback, Order order, String uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "/tests/" + order.getState().toString().toLowerCase();
        DatabaseReference databaseReferenceForOrders = database.getReference(path).child(uid);
        List<CartEntityJSON> cartEntityJSONList = new ArrayList<>();
        for (CartEntity cartEntity : order.getProductList()) {
            cartEntityJSONList.add(CartEntityMapper.cartEntityToCartEntityJSON(cartEntity));
        }
        OrderJSON orderJSON = OrderMapper.getOrderJSONFromOrder(order, cartEntityJSONList);
        databaseReferenceForOrders.setValue(orderJSON).addOnSuccessListener(unused -> {
            callback.returnState(0);//todo добавить enum Error
        }).addOnFailureListener(e -> {
            callback.returnState(1);//todo добавить enum Error
        });

    }

    @Override
    public void sendOrders(SendOrdersCallback callback, Order order) {
        //todo получаем uid пользователя в data слое
        sendOrders(callback, order, "uid001");
    }
}
