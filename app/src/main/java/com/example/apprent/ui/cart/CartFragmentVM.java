package com.example.apprent.ui.cart;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.R;
import com.example.apprent.data.cart_database.CartDatabase;
import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.ui.main_activity.MainActivityViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class CartFragmentVM extends ViewModel implements Serializable {

    private MutableLiveData<ArrayList<CartEntity>> cartProductList = new MutableLiveData<ArrayList<CartEntity>>();
    private MainActivityViewModel mainActivityViewModel;

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
        mainActivityViewModel.removeFromCart(cartEntity);
        cartProductList.getValue().remove(index);
        ArrayList<CartEntity> updateList = cartProductList.getValue();
        cartProductList.postValue(updateList);
    }

    public LiveData<ArrayList<CartEntity>> getCartProductList() {
        return cartProductList;
    }

    public void setMainActivity(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
    }

    public void openFullItem(CartEntity item, int index) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("entity", item);
        bundle.putSerializable("CartFragmentVM", this);
        bundle.putInt("index", index);
        mainActivityViewModel.getNavController().navigate(R.id.action_cartFragment_to_cartListFullItem, bundle);
        mainActivityViewModel.setTitleOfTopBar("Детали");
        mainActivityViewModel.getBottomNavigationView().setVisibility(View.GONE);
        mainActivityViewModel.showBackButton();
    }

    public void changeDataFromDB(int id, CartEntity cartProduct) {
        mainActivityViewModel.changeDataCartDB(id, cartProduct);
    }

    public void closeDetails() {
        mainActivityViewModel.getBottomNavigationView().setVisibility(View.VISIBLE);
        mainActivityViewModel.hideBackButton();
    }
}
