package com.example.apprent.domain.usecase;

import com.example.apprent.domain.MainContract;

public class GetProductList {
    private final MainContract.GetListData getItemsListData;

    public GetProductList(MainContract.GetListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(ProductListCallback callback, String category) { //todo нужен ли тут параметр и правильно ли что он передается в качестве параметра уже другой функции?
        getItemsListData.getProductsList(callback, category);
    }

}




