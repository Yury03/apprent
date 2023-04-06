package com.example.apprent.domain;

import com.example.apprent.domain.usecase.CategoryListCallback;

public interface MainContract {
    interface GetItemsListData{
        public void getCategoryList(CategoryListCallback callback);
        public void getCategoryList(CategoryListCallback callback, String subCategory);
        public void getProductsList();
    }

}
