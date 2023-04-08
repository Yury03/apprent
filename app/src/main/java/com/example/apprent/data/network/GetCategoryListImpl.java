package com.example.apprent.data.network;

import android.util.Log;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.usecase.ItemsListCallback;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryListImpl implements MainContract.GetItemsListData {
    private String pathString = "category/";

    @Override
    public void getCategoryList(ItemsListCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(pathString);
        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<CategoryItem> categoryItemArrayList = new ArrayList<>();//todo чью модель данных использовать(слой domain)?
            for (StorageReference file : listResult.getItems()) {
                file.getDownloadUrl().addOnSuccessListener(uri -> {
                    categoryItemArrayList.add(new CategoryItem(uri.toString(), "description"));
                    Log.d("HELP", file.toString());
                    if (categoryItemArrayList.size() == listResult.getItems().size()) {
                        callback.onCategoryListLoaded(categoryItemArrayList);
                    }
                });
            }
        });
    }

    @Override
    public void getCategoryList(ItemsListCallback callback, String subCategory) {
        pathString = "subcategory/" + subCategory;
        this.getCategoryList(callback);
    }

    @Override
    public void getProductsList() {
        //todo
    }

}