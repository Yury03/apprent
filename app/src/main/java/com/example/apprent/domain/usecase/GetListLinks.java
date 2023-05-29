package com.example.apprent.domain.usecase;

import com.example.apprent.domain.MainContract;

public class GetListLinks {
    private final MainContract.GetListData getListData;
    public GetListLinks(MainContract.GetListData getListData) {
        this.getListData = getListData;
    }

    public void execute(LinksCallback linksCallback){
        getListData.getBannerImages(linksCallback);
    }
}
