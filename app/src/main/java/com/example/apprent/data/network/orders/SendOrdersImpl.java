package com.example.apprent.data.network.orders;


import static com.example.apprent.data.network.AuthenticationImpl.AUTH_PREFERENCES;

import android.content.Context;
import android.content.SharedPreferences;

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

    private final SharedPreferences sharedPreferences;

    public SendOrdersImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void sendOrders(SendOrdersCallback callback, Order order, String uid) {
        if (uid.equals("errorUID")) {
            callback.returnState(Order.SendOrderError.UID_ERROR);
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "/tests/" + order.getState().toString().toLowerCase();
        DatabaseReference databaseReferenceForOrders = database.getReference(path).child(uid).push();
        List<CartEntityJSON> cartEntityJSONList = new ArrayList<>();
        for (CartEntity cartEntity : order.getProductList()) {
            cartEntityJSONList.add(CartEntityMapper.cartEntityToCartEntityJSON(cartEntity));
        }
        OrderJSON orderJSON = OrderMapper.getOrderJSONFromOrder(order, cartEntityJSONList);
        databaseReferenceForOrders.setValue(orderJSON).addOnSuccessListener(unused -> {
            callback.returnState(Order.SendOrderError.ORDER_IS_SEND);
        }).addOnFailureListener(e -> callback.returnState(Order.SendOrderError.FIREBASE_ERROR));
    }

    @Override
    public void sendOrders(SendOrdersCallback callback, Order order) {
        String uid = sharedPreferences.getString("UID", "errorUID");
        sendOrders(callback, order, uid);
    }
}
