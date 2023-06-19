package com.example.apprent.domain.usecase.get_products;

import com.example.apprent.domain.MainContract;

public class GetProductList {
    private final MainContract.GetListData getItemsListData;

    public GetProductList(MainContract.GetListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(ProductListCallback callback, String category) {
        getItemsListData.getProductsList(callback, category);
    }

}




