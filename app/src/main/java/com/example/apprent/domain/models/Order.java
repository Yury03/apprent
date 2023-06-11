package com.example.apprent.domain.models;

import com.example.apprent.data.cart_database.entity.CartEntity;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private boolean isDelivery;
    private boolean isPaid;
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

    public Order(int id, boolean isDelivery, boolean isPaid, List<CartEntity> productList) {
        this.id = id;
        this.isDelivery = isDelivery;
        this.isPaid = isPaid;
        this.productList = productList;
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
}
