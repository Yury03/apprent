package com.example.apprent.data.cart_database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apprent.data.cart_database.entity.CartProductEntity;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(CartProductEntity cartProduct);

    @Update
    void update(CartProductEntity cartProduct);

    @Delete
    void delete(CartProductEntity cartProduct);

    @Query("SELECT * FROM cart_products")
    List<CartProductEntity> getAllCartProducts();

    @Query("SELECT * FROM cart_products WHERE id = :id")
    CartProductEntity getById(int id);
}
