package com.example.apprent.data.network.orders.models;

public class CartEntityJSON {//todo record?
    private final int product_id;
    private final String product_name;


    private final String product_image_uri;
    private final String finalPrice;
    private final int period;
    private final int quantity;
    private final String start_date;


    private final int min_price;

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

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }


    public String getProduct_image_uri() {
        return product_image_uri;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public int getPeriod() {
        return period;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStart_date() {
        return start_date;
    }

    public int getMin_price() {
        return min_price;
    }

}
