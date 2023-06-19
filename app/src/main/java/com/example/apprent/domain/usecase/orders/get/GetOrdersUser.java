package com.example.apprent.domain.usecase.orders.get;

import com.example.apprent.domain.MainContract;

public class GetOrdersUser {
    private final MainContract.GetOrders getOrders;

    public GetOrdersUser(MainContract.GetOrders getOrders) {
        this.getOrders = getOrders;
    }

    public void execute(GetOrdersCallback callback, String group) {
        getOrders.getOrdersForUser(callback, group);
    }
}
