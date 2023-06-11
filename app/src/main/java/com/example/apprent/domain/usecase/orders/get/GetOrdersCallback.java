package com.example.apprent.domain.usecase.orders.get;

import com.example.apprent.domain.models.Order;

import java.util.List;

public interface GetOrdersCallback {
    void onOrdersLoaded(List<Order> orders);
}
