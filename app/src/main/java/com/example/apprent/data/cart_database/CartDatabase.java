package com.example.apprent.data.cart_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.apprent.data.cart_database.dao.CartDao;
import com.example.apprent.data.cart_database.entity.CartProductEntity;

@Database(entities = {CartProductEntity.class}, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {
    private static volatile CartDatabase instance;

    public abstract CartDao cartDao();

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CartDatabase.class, "cart_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}


