package com.example.apprent.domain.usecase.orders.get;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.Order;

public class GetOrdersAdmin {
    private final MainContract.GetOrders getOrders;

    public GetOrdersAdmin(MainContract.GetOrders getOrders) {
        this.getOrders = getOrders;
    }

    public void execute(GetOrdersCallback callback, String group) {
        getOrders.getOrders(callback, group);
    }
}
