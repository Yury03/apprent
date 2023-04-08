package com.example.apprent.domain.usecase;

import com.example.apprent.domain.MainContract;

public class GetSubCategoryList {
    private final MainContract.GetItemsListData getItemsListData;

    public GetSubCategoryList(MainContract.GetItemsListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(ItemsListCallback callback) {//todo нужен ли тут параметр и правильно ли что он передается в качестве параметра уже другой функции?
        getItemsListData.getCategoryList(callback);
    }

}




