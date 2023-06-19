package com.example.apprent.data;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.data.network.orders.models.CartEntityJSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CartEntityMapper {
    public static CartEntityJSON cartEntityToCartEntityJSON(CartEntity cartEntity) {
        return new CartEntityJSON(
                getProductId(cartEntity.getFullPath()),
                cartEntity.getName(),
                cartEntity.getImageUri(),
                String.valueOf(cartEntity.getFinalPrice()),//todo исправить на String
                cartEntity.getPeriod(),
                cartEntity.getQuantity(),
                getStringFromDate(cartEntity.getDate()),
                cartEntity.getMinPrice());
    }

    public static CartEntity cartEntityJSONToCartEntity(CartEntityJSON cartEntityJSON) {
        return new CartEntity(cartEntityJSON.product_name,
                getDateFromString(cartEntityJSON.start_date),//todo nullable!!!
                cartEntityJSON.period,
                cartEntityJSON.product_image_uri,
                cartEntityJSON.min_price,
                getFullPath(cartEntityJSON.product_id)
        );
    }

    private static String getFullPath(int product_id) {
        StringBuilder fullPath = new StringBuilder("/aura/");
        String id = String.valueOf(product_id);
        int length = id.length();
        for (int i = 0; i < length - 1; i++) {
            fullPath.append("subcategory_").append(id.charAt(i)).append("/");
        }
        fullPath.append("item_").append(id.charAt(length - 1));
        return fullPath.toString();
    }

    @SuppressLint("SimpleDateFormat")
    private static Date getDateFromString(String start_date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(start_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    private static String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    private static int getProductId(String path) {//todo оптимизировать
        Log.e("Mapper", path);
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }
        return Integer.parseInt(digits.toString());
    }
}