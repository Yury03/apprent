package com.example.apprent.domain;

public interface MainContract {
    interface GetNetworkData{
        public void getCategoryList();
        public void getCategoryList(String subCategory);
    }

}
