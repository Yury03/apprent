package com.example.apprent.ui.cart;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.R;
import com.example.apprent.data.cart_database.CartDatabase;
import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.ui.main_activity.MainActivityVM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class CartFragmentVM extends ViewModel implements Serializable {

    private MutableLiveData<ArrayList<CartEntity>> cartProductList = new MutableLiveData<ArrayList<CartEntity>>();
    private MainActivityVM mainActivityVM;

    public LiveData<String> getFinalPrice() {
        return finalPrice;
    }


    private final MutableLiveData<String> finalPrice = new MutableLiveData<>();


    public void loadCartProductList(CartDatabase cartDatabase) {
        Executors.newSingleThreadExecutor().execute(() -> {
            ArrayList<CartEntity> cartEntityList = (ArrayList<CartEntity>) cartDatabase.cartDao().getProductsWithState(CartEntity.State.CART.stateId);
            double price = 0;
            for (CartEntity product :
                    cartEntityList) {
                price += product.getFinalPrice();
            }
            finalPrice.postValue(String.valueOf(price));
            cartProductList.postValue(cartEntityList);

        });
    }

    public void removeFromCart(int index) {
        CartEntity cartEntity = cartProductList.getValue().get(index);
        mainActivityVM.removeFromCart(cartEntity);
        cartProductList.getValue().remove(index);
        ArrayList<CartEntity> updateList = cartProductList.getValue();
        cartProductList.postValue(updateList);
    }

    public LiveData<ArrayList<CartEntity>> getCartProductList() {
        return cartProductList;
    }

    public void setMainActivity(MainActivityVM mainActivityVM) {
        this.mainActivityVM = mainActivityVM;
    }

    public void openFullItem(CartEntity item, int index) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("entity", item);
        bundle.putSerializable("CartFragmentVM", this);
        bundle.putInt("index", index);
        mainActivityVM.getNavController().navigate(R.id.action_cartFragment_to_cartListFullItem, bundle);
        mainActivityVM.setTitleOfTopBar("Детали");
        mainActivityVM.getBottomNavigationView().setVisibility(View.GONE);
        mainActivityVM.showBackButton();
    }

    public void changeDataFromDB(int id, CartEntity cartProduct) {
        mainActivityVM.changeDataCartDB(id, cartProduct);
    }

    public void closeDetails() {
        mainActivityVM.getBottomNavigationView().setVisibility(View.VISIBLE);
        mainActivityVM.hideBackButton();
    }
}
