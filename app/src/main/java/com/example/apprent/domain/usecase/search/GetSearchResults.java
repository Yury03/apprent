package com.example.apprent.domain.usecase.search;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.usecase.get_products.ProductListCallback;

public class GetSearchResults {
    private final MainContract.GetListData getItemsListData;

    public GetSearchResults(MainContract.GetListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(ProductListCallback callback, String query, String path) {
        getItemsListData.getSearchResults(callback, query, path);
    }
}