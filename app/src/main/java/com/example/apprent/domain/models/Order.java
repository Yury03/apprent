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
    public static enum SendOrderError {
        ORDER_IS_SEND(0),
        UID_ERROR(1),
        FIREBASE_ERROR(2),
        ;
        public final int stateId;

        SendOrderError(int stateId) {
            this.stateId = stateId;
        }
    }

    private final int id;

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getComment() {
        return comment;
    }

    private final String firstName;
    private final String secondName;
    private final String comment;
    private final boolean isDelivery;
    private final boolean isPaid;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private State state;
    private final String phoneNumber;

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

    public Order(int id, boolean isDelivery, boolean isPaid,
                 List<CartEntity> productList, String phoneNumber,
                 State state, String firstName, String secondName, String comment) {
        this.id = id;
        this.isDelivery = isDelivery;
        this.isPaid = isPaid;
        this.productList = productList;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.firstName = firstName;
        this.secondName = secondName;
        this.comment = comment;
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
