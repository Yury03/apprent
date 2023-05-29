package com.example.apprent.domain.usecase;

import com.example.apprent.domain.MainContract;

public class GetCategoryList {

    private final MainContract.GetListData getItemsListData;

    public GetCategoryList(MainContract.GetListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(CategoryListCallback callback, String subcategory) {//todo нужен ли тут параметр и правильно ли что он передается в качестве параметра уже другой функции?
        getItemsListData.getCategoryList(callback, subcategory);
    }
}
