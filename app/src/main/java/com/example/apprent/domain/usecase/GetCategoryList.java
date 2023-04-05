package com.example.apprent.domain.usecase;

import android.database.Observable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apprent.domain.models.CategoryItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryList {
    private ArrayList<CategoryItem> categoryItemArrayList;

    public GetCategoryList() {

    }


    public void execute(CategoryListCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("category");
        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<CategoryItem> categoryItemArrayList = new ArrayList<>();
            for (StorageReference file : listResult.getItems()) {
                file.getDownloadUrl().addOnSuccessListener(uri -> {
                    categoryItemArrayList.add(new CategoryItem(uri.toString(), "description"));
                    Log.d("HELP", uri.toString());
                    if (categoryItemArrayList.size() == listResult.getItems().size()) {
                        callback.onCategoryListLoaded(categoryItemArrayList);
                    }
                });
            }
        });
    }
}
