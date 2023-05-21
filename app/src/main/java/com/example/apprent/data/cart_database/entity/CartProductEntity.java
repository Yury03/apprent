package com.example.apprent.data.cart_database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cart_products")
public class CartProductEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @TypeConverters(DateConverter.class)
    private Date date;
    private int period;
    private int quantity;
    private String imageUri;
    private int price;
    public CartProductEntity(String name, Date date, int period, int quantity, String imageUri, int price) {
        this.name = name;
        this.date = date;
        this.period = period;
        this.quantity = quantity;
        this.imageUri = imageUri;
        this.price = price;
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

    public int getPrice() {
        return price;
    }


    public int getPeriod() {
        return period;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
