package com.example.apprent.ui.cart_page;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.R;
import com.example.apprent.data.cart_database.CartDatabase;
import com.example.apprent.data.cart_database.entity.CartProductEntity;
import com.example.apprent.ui.main_activity.MainActivityVM;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;

public class CartFragmentVM extends ViewModel implements Serializable {

    private MutableLiveData<List<CartProductEntity>> cartProductList = new MutableLiveData<>();
    private MainActivityVM mainActivityVM;
    private MotionLayout motionLayout;

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

    public void removeFromCart(int index) {
        CartProductEntity cartProductEntity = cartProductList.getValue().get(index);
        mainActivityVM.removeFromCart(cartProductEntity);
        cartProductList.getValue().remove(index);
        List<CartProductEntity> updateList = cartProductList.getValue();
        cartProductList.postValue(updateList);
    }

    public LiveData<List<CartProductEntity>> getCartProductList() {
        return cartProductList;
    }

    public void setMainActivity(MainActivityVM mainActivityVM) {
        this.mainActivityVM = mainActivityVM;
    }

    public void openFullItem(CartProductEntity item, int index) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", item);
        bundle.putSerializable("CartFragmentVM", this);
        bundle.putInt("index", index);
        mainActivityVM.getNavController().navigate(R.id.action_cartFragment_to_cartListFullItem, bundle);
        mainActivityVM.setTitleOfTopBar("Детали");
        mainActivityVM.getBottomNavigationView().setVisibility(View.GONE);
    }

    public void changeDataFromDB(int id, int index, CartProductEntity cartProduct) {
        mainActivityVM.changeDataCartDB(id, cartProduct);
//        mainActivityVM.addToCart(cartProduct, 10);

    }

}
