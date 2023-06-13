package com.example.apprent.domain.models;

import com.example.apprent.data.cart_database.entity.CartEntity;

import java.util.Date;
import java.util.List;

public class Order {
    public static enum State {
        EXPECTED(0),
        CURRENT(1),
        COMPLETED(2),
        CANCELED(3),
        ;
        public final int stateId;

        State(int stateId) {
            this.stateId = stateId;
        }
    }

    private int id;
    private final String uid;
    private boolean isDelivery;
    private boolean isPaid;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private State state;
    private String phoneNumber;

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    private String deliveryAddress;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    private Date rentStart;
    private int quantityProducts;

    public int getId() {
        return id;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public Date getRentStart() {
        return rentStart;
    }

    public int getQuantityProducts() {
        return quantityProducts;
    }

    public List<CartEntity> getProductList() {
        return productList;
    }

    private List<CartEntity> productList;

    public Order(int id, boolean isDelivery, boolean isPaid, List<CartEntity> productList,
                 String phoneNumber, State state, String uid) {
        this.id = id;
        this.isDelivery = isDelivery;
        this.isPaid = isPaid;
        this.productList = productList;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.uid = uid;
        if (productList != null) {
            this.quantityProducts = productList.size();
            this.rentStart = productList.get(0).getDate();
        }
    }

    public void setCartProductEntity(List<CartEntity> productList) {
        this.productList = productList;
        this.quantityProducts = productList.size();
        this.rentStart = productList.get(0).getDate();
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
