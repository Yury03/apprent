package com.example.apprent.domain;

import com.example.apprent.domain.usecase.ItemsListCallback;

public interface MainContract {
    interface GetItemsListData{
        public void getCategoryList(ItemsListCallback callback);
        public void getCategoryList(ItemsListCallback callback, String subCategory);
        public void getProductsList();
    }

}
