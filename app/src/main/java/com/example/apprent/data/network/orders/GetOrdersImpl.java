package com.example.apprent.data.network.orders;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apprent.data.CartEntityMapper;
import com.example.apprent.data.OrderMapper;
import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.data.network.orders.models.CartEntityJSON;
import com.example.apprent.data.network.orders.models.OrderJSON;
import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.Order;
import com.example.apprent.domain.usecase.orders.get.GetOrdersCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetOrdersImpl implements MainContract.GetOrders {

//    private final String PATH_FOR_ORDERS = "/aura/orders/";
    private final String PATH_FOR_ORDERS= "/tests/";

    private final String PATH_FOR_CART = "/aura/cart";

    @Override
    public void getOrders(GetOrdersCallback callback, String group) {
        String pathOrders = PATH_FOR_ORDERS + group;
        List<Order> orderList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceForOrders = database.getReference(pathOrders);
        databaseReferenceForOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {//todo оптимизация
                    OrderJSON orderJSON = orderSnapshot.getValue(OrderJSON.class);
                    List<CartEntity> cartEntityList = new ArrayList<>();
                    for (CartEntityJSON cartEntityJSON : orderJSON.product_list) {
                        cartEntityList.add(CartEntityMapper.cartEntityJSONToCartEntity(cartEntityJSON));
                    }
                    orderList.add(OrderMapper.getOrderFromOrderJSON(orderJSON, cartEntityList, group, orderSnapshot.getKey()));
                }
                callback.onOrdersLoaded(orderList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
