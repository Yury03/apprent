package com.example.apprent.domain.usecase.orders.send;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.Order;

public class SendOrders {
    private final MainContract.SendOrders sendOrders;

    public SendOrders(MainContract.SendOrders sendOrders) {

        this.sendOrders = sendOrders;
    }

    public void execute(SendOrdersCallback callback, Order order) {
        sendOrders.sendOrders(callback, order);
    }

}
