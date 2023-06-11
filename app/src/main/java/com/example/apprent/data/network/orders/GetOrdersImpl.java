package com.example.apprent.data.network.orders;

import androidx.annotation.NonNull;

import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.Order;
import com.example.apprent.domain.usecase.orders.get.GetOrdersCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetOrdersImpl implements MainContract.GetOrders {

    private final String PATH_FOR_ORDERS = "/aura/orders/";
    private final String PATH_FOR_CART = "/aura/cart";


    public void getOrders(GetOrdersCallback callback, String group) {
        String pathOrders = PATH_FOR_ORDERS + group;
        List<Order> orderList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceForOrders = database.getReference(pathOrders);

        databaseReferenceForOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task<CartEntity>> tasks = new ArrayList<>();
                List<Order> orderList = new ArrayList<>();

                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    String uid = orderSnapshot.getKey();
                    int orderId = Integer.parseInt(uid);
                    boolean isDelivery = Boolean.TRUE.equals(orderSnapshot.child("is_delivery").getValue(Boolean.class));
                    boolean isPaid = Boolean.TRUE.equals(orderSnapshot.child("is_paid").getValue(Boolean.class));
                    int productListId = orderSnapshot.child("product_list_id").getValue(Integer.class);
                    Task<CartEntity> task = getProductListTask(String.valueOf(productListId));
                    tasks.add(task);
                    orderList.add(new Order(orderId, isDelivery, isPaid, null));
                }

                Tasks.whenAllSuccess(tasks).addOnSuccessListener(cartProductEntityList -> {
                    for (int i = 0; i < snapshot.getChildrenCount(); i++) {
                        Order order = orderList.get(i);
                        order.setCartProductEntity((List<CartEntity>) cartProductEntityList.get(i));
                    }

                    callback.onOrdersLoaded(orderList);
                }).addOnFailureListener(error -> {
                    // Обработка ошибок, если не удалось получить данные CartProductEntity
                });
            }

            private Task<CartEntity> getProductListTask(String productListId) {
                TaskCompletionSource<CartEntity> taskCompletionSource = new TaskCompletionSource<>();
                DatabaseReference databaseReferenceForProductList = database.getReference(PATH_FOR_CART + productListId);
                databaseReferenceForProductList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String name = snapshot.child("name").getValue(String.class);
                            String dateString = snapshot.child("date").getValue(String.class);
                            int period = snapshot.child("period").getValue(Integer.class);
                            String imageUri = snapshot.child("imageUri").getValue(String.class);
                            int minPrice = snapshot.child("minPrice").getValue(Integer.class);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            Date date;
                            try {
                                date = dateFormat.parse(dateString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                taskCompletionSource.setException(e);
                                return;
                            }
//todo                            CartEntity cartEntity = new CartEntity(name, date, period, imageUri, minPrice);
//todo                            taskCompletionSource.setResult(cartEntity);
                        } else {
                            taskCompletionSource.setException(new Exception("Product list not found"));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        taskCompletionSource.setException(error.toException());
                    }
                });

                return taskCompletionSource.getTask();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //todo
            }
        });

    }

    @Override
    public void getOrders(GetOrdersCallback callback) {
        getOrders(callback, "current/");
    }
}
