package com.example.apprent.ui.main_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.data.network.GetItemsListImpl;
import com.example.apprent.domain.usecase.GetListLinks;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentVM extends ViewModel {
    private MutableLiveData<List<String>> bannerLinks = new MutableLiveData<>();

    public LiveData<List<String>> getBannerLinks() {
        return this.bannerLinks;
    }


    public void loadBanners() {
        GetItemsListImpl getItemsList = new GetItemsListImpl();
        GetListLinks getListLinks = new GetListLinks(getItemsList);
        getListLinks.execute(links -> {
            this.bannerLinks.postValue(links);

        });

    }
}
