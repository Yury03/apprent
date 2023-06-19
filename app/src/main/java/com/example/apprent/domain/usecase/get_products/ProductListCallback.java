package com.example.apprent.domain.usecase.get_products;

import com.example.apprent.domain.models.ProductItem;

import java.util.List;

public interface ProductListCallback {
    void onItemListLoaded(List<ProductItem> productItems);
}
