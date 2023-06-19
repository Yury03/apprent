package com.example.apprent.data.network.orders.models;

public class CartEntityJSON {
    public int product_id;
    public String product_name;


    public String product_image_uri;
    public String finalPrice;
    public int period;
    public int quantity;
    public String start_date;


    public int min_price;

    public CartEntityJSON(int product_id,
                          String product_name,
                          String product_image_uri,
                          String finalPrice,
                          int period,
                          int quantity,
                          String start_date,
                          int min_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image_uri = product_image_uri;
        this.finalPrice = finalPrice;
        this.period = period;
        this.quantity = quantity;
        this.start_date = start_date;
        this.min_price = min_price;
    }

    public CartEntityJSON() {
    }
}
