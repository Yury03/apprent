package com.example.apprent.domain.usecase;

import com.example.apprent.domain.MainContract;

public class GetSearchResults {
    private final MainContract.GetListData getItemsListData;

    public GetSearchResults(MainContract.GetListData getItemsListData) {
        this.getItemsListData = getItemsListData;
    }

    public void execute(ProductListCallback callback, String query, String path) { //todo нужен ли тут параметр и правильно ли что он передается в качестве параметра уже другой функции?
        getItemsListData.getSearchResults(callback, query, path);
    }
}