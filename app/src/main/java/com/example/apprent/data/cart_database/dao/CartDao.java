package com.example.apprent.data.cart_database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apprent.data.cart_database.entity.CartEntity;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(CartEntity cartProduct);

    @Update
    void update(CartEntity cartProduct);

    @Delete
    void delete(CartEntity cartProduct);

    @Query("SELECT * FROM cart_products")
    List<CartEntity> getAllCartProducts();

    @Query("SELECT * FROM cart_products WHERE id = :id")
    CartEntity getById(int id);

    @Query("SELECT * FROM cart_products WHERE state = :state")

    List<CartEntity> getProductsWithState(int state);
//
//    @Query("SELECT * FROM cart_products WHERE state.stateId = CartProductEntity.State.CART")
//
//            List<CartProductEntity>getOrderProducts();
}
