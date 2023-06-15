package com.example.apprent.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.data.network.GetItemsListImpl;
import com.example.apprent.data.network.orders.GetOrdersImpl;
import com.example.apprent.domain.models.Order;
import com.example.apprent.domain.usecase.get_links.GetListLinks;
import com.example.apprent.domain.usecase.orders.get.GetOrdersUser;

import java.util.List;

public class MainFragmentVM extends ViewModel {
    private final MutableLiveData<List<String>> bannerLinks = new MutableLiveData<>();
    private final MutableLiveData<List<Order>> ordersList = new MutableLiveData<>();

    public LiveData<List<Order>> getOrdersList() {
        return ordersList;
    }

    public LiveData<List<String>> getBannerLinks() {
        return this.bannerLinks;
    }


    public void loadBanners() {
        GetItemsListImpl getItemsList = new GetItemsListImpl();
        GetListLinks getListLinks = new GetListLinks(getItemsList);
        getListLinks.execute(bannerLinks::postValue);
    }

    public void loadOrders(Context context, String group) {
        GetOrdersImpl getOrdersFromFirebase = new GetOrdersImpl(context);
        GetOrdersUser getOrdersUser = new GetOrdersUser(getOrdersFromFirebase);
        getOrdersUser.execute(ordersList::postValue, group);
    }
}
