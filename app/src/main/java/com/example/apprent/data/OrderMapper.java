package com.example.apprent.data;

import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.data.network.orders.models.CartEntityJSON;
import com.example.apprent.data.network.orders.models.OrderJSON;
import com.example.apprent.domain.models.Order;

import java.util.List;

public class OrderMapper {
    public static OrderJSON getOrderJSONFromOrder(Order order, List<CartEntityJSON> cartEntityJSONList) {
        return new OrderJSON(order.getId(),
                order.isDelivery(),
                order.isPaid(),
                order.getDeliveryAddress(),
                cartEntityJSONList,
                order.getPhoneNumber());
    }

    public static Order getOrderFromOrderJSON(OrderJSON orderJSON, List<CartEntity> cartEntityList, String state, String uid) {//todo возможно понадобится uid
        Order.State res = switch (state) {
            case "expected" -> Order.State.EXPECTED;
            case "current" -> Order.State.CURRENT;
            case "completed" -> Order.State.COMPLETED;
            case "canceled" -> Order.State.CANCELED;
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
        Order result = new Order(orderJSON.id,
                orderJSON.is_delivery,
                orderJSON.is_paid,
                cartEntityList,
                orderJSON.phone_number, res, uid);
        if (orderJSON.is_delivery) {
            result.setDeliveryAddress(orderJSON.delivery_address);
        }
        return result;
    }
}
