package com.example.apprent.domain.usecase;

import com.example.apprent.domain.models.ProductItem;

import java.util.List;

public interface ProductListCallback {
    void onItemListLoaded(List<ProductItem> productItems);
}
