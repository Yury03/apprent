package com.example.apprent.domain.usecase;

import com.example.apprent.domain.MainContract;

public class GetCategoryList {

    private final MainContract.GetItemsListData getItemsListData;
    public GetCategoryList(MainContract.GetItemsListData getItemsListData){
        this.getItemsListData=getItemsListData;
    }
    public void execute(ItemsListCallback callback) {//todo нужен ли тут параметр и правильно ли что он передается в качестве параметра уже другой функции?
        getItemsListData.getCategoryList(callback);
    }
}
