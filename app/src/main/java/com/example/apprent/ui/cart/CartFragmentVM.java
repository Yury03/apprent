package com.example.apprent.ui.cart;

import android.os.Bundle;
import android.view.View;

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

    public LiveData<String> getFinalPrice() {
        return finalPrice;
    }


    private MutableLiveData<String> finalPrice = new MutableLiveData<>();

    public CartFragmentVM() {
    }

    public void loadCartProductList(CartDatabase cartDatabase) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<CartProductEntity> cartProductEntityList = cartDatabase.cartDao().getAllCartProducts();
            double price = 0;
            for (CartProductEntity product :
                    cartProductEntityList) {
                price += product.getFinalPrice();
            }
            finalPrice.postValue(String.valueOf(price));
            cartProductList.postValue(cartProductEntityList);

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
        mainActivityVM.showBackButton();
    }

    public void changeDataFromDB(int id, CartProductEntity cartProduct) {
        mainActivityVM.changeDataCartDB(id, cartProduct);
    }

    public void closeDetails() {
        mainActivityVM.getBottomNavigationView().setVisibility(View.VISIBLE);
        mainActivityVM.hideBackButton();
    }
}
