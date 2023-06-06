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
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class CartFragmentVM extends ViewModel implements Serializable {

    private MutableLiveData<ArrayList<CartProductEntity>> cartProductList = new MutableLiveData<ArrayList<CartProductEntity>>();
    private MainActivityVM mainActivityVM;

    public LiveData<String> getFinalPrice() {
        return finalPrice;
    }


    private final MutableLiveData<String> finalPrice = new MutableLiveData<>();


    public void loadCartProductList(CartDatabase cartDatabase) {
        Executors.newSingleThreadExecutor().execute(() -> {
            ArrayList<CartProductEntity> cartProductEntityList = (ArrayList<CartProductEntity>) cartDatabase.cartDao().getProductsWithState(CartProductEntity.State.CART.stateId);
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
        ArrayList<CartProductEntity> updateList = cartProductList.getValue();
        cartProductList.postValue(updateList);
    }

    public LiveData<ArrayList<CartProductEntity>> getCartProductList() {
        return cartProductList;
    }

    public void setMainActivity(MainActivityVM mainActivityVM) {
        this.mainActivityVM = mainActivityVM;
    }

    public void openFullItem(CartProductEntity item, int index) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("entity", item);
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
