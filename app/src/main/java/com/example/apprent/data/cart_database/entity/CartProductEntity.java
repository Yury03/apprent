package com.example.apprent.data.cart_database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "cart_products")
public class CartProductEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;//todo long->int
    private String name;
    @TypeConverters(DateConverter.class)
    private Date date;
    private int period;
    private int quantity;
    private String imageUri;
    private int minPrice;

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    private int finalPrice;

    public CartProductEntity(String name, Date date, int period, String imageUri, int minPrice) {
        this.name = name;
        this.date = date;
        this.period = period;
        this.quantity = 1;
        this.imageUri = imageUri;
        this.minPrice = minPrice;
        this.finalPrice = minPrice * period;
    }

    private void update() {
        this.finalPrice = this.period * this.quantity * this.minPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPeriod(int period) {
        this.period = period;
        update();
    }

    public int getPeriod() {
        return period;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        update();
    }

    public String getImageUri() {
        return imageUri;
    }

    public static class DateConverter {
        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }
}
