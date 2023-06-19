package com.example.apprent.data.network.orders.models;

import java.util.List;

public class OrderJSON {
    public int id;
    public boolean is_delivery;
    public boolean is_paid;
    public String delivery_address;
    public String firstName;
    public String secondName;
    public String comment;
    public String phone_number;
    public List<CartEntityJSON> product_list;


    public OrderJSON() {
    }

    public OrderJSON(int id,
                     boolean is_delivery,
                     boolean is_paid,
                     String delivery_address,
                     List<CartEntityJSON> product_list,
                     String phone_number, String firstName,
                     String secondName, String comment) {
        this.id = id;
        this.is_delivery = is_delivery;
        this.is_paid = is_paid;
        this.delivery_address = delivery_address;
        this.product_list = product_list;
        this.phone_number = phone_number;
        this.firstName = firstName;
        this.secondName = secondName;
        this.comment = comment;
    }
}
