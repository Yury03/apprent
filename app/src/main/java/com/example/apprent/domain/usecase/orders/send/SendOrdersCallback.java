package com.example.apprent.domain.usecase.orders.send;

import com.example.apprent.domain.models.Order;

public interface SendOrdersCallback {
    void returnState(Order.SendOrderError Error);
}
