package com.example.apprent.domain.usecase;

import com.example.apprent.domain.models.CategoryItem;

import java.util.List;

public interface CategoryListCallback {
    void onItemListLoaded(List<CategoryItem> categoryItems);

}
