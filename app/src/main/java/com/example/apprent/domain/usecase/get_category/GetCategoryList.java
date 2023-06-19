package com.example.apprent.domain.usecase.get_category;

import com.example.apprent.domain.MainContract;

public class GetCategoryList {

    private final MainContract.GetListData getItemsListData;

    public GetCategoryList(MainContract.GetListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(CategoryListCallback callback, String subcategory) {
        getItemsListData.getCategoryList(callback, subcategory);
    }
}
