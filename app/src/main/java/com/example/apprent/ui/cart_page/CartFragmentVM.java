package com.example.apprent.ui.cart_page;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.data.cart_database.CartDatabase;
import com.example.apprent.data.cart_database.entity.CartProductEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class CartFragmentVM extends ViewModel {

    MutableLiveData<List<CartProductEntity>> cartProductList = new MutableLiveData<>();
//    private CartDatabase cartDatabase;

    public CartFragmentVM() {

    }

    public void loadCartProductList(CartDatabase cartDatabase) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<CartProductEntity> cartProductEntityList = cartDatabase.cartDao().getAllCartProducts();
                cartProductList.postValue(cartProductEntityList);
                Log.e("room", "post value");
            }
        });
    }

    public LiveData<List<CartProductEntity>> getCartProductList() {
        return cartProductList;
    }
}
