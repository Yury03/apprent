package com.example.apprent.data.network.orders;

import static com.example.apprent.data.network.AuthenticationImpl.AUTH_PREFERENCES;

import android.content.Context;
import android.content.SharedPreferences;

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
    private final String PATH_FOR_ORDERS = "/tests/";// /tests/current/uid1
    private final SharedPreferences sharedPreferences;
    private final FirebaseDatabase database;

    public GetOrdersImpl(Context context) {
        this.database = FirebaseDatabase.getInstance();
        this.sharedPreferences = context
                .getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void getOrdersForAdmin(GetOrdersCallback callback, String group) {
        String pathOrders = PATH_FOR_ORDERS + group;

        DatabaseReference databaseReferenceForOrders = database.getReference(pathOrders);
        databaseReferenceForOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orderList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String uid = childSnapshot.getKey();
                    for (DataSnapshot orderSnapshot : childSnapshot.getChildren()) {
                        orderList.add(readOrder(orderSnapshot, group, uid));
                    }
                }
                callback.onOrdersLoaded(orderList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void getOrdersForUser(GetOrdersCallback callback, String group, String uid) {
        String path = PATH_FOR_ORDERS + group + "/" + uid;

        DatabaseReference databaseReferenceForOrders = database.getReference(path);
        databaseReferenceForOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orderList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    orderList.add(readOrder(childSnapshot, group, uid));
                }
                callback.onOrdersLoaded(orderList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void getOrdersForUser(GetOrdersCallback callback, String group) {
        String uid = sharedPreferences.getString("UID", "errorUID");
        getOrdersForUser(callback, group, uid);
    }

    private Order readOrder(DataSnapshot childSnapshot, String group, String uid) {
        OrderJSON orderJSON = childSnapshot.getValue(OrderJSON.class);
        List<CartEntity> cartEntityList = new ArrayList<>();
        for (CartEntityJSON cartEntityJSON : orderJSON.product_list) {
            cartEntityList.add(CartEntityMapper.cartEntityJSONToCartEntity(cartEntityJSON));
        }
        return OrderMapper.getOrderFromOrderJSON(orderJSON, cartEntityList, group, uid);
    }

}
