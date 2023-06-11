package com.example.apprent.domain.usecase.search;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.usecase.get_products.ProductListCallback;

public class GetSearchResults {
    private final MainContract.GetListData getItemsListData;

    public GetSearchResults(MainContract.GetListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(ProductListCallback callback, String query, String path) { //todo нужен ли тут параметр и правильно ли что он передается в качестве параметра уже другой функции?
        getItemsListData.getSearchResults(callback, query, path);
    }
}