package com.example.apprent.data.network;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GetCategoryList {
    StorageReference root;
    private List<String> categoryList;
    public List<String> getter() {
        AtomicBoolean error= new AtomicBoolean(false);
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("category");
        listRef.listAll().addOnSuccessListener(listResult -> {
            for(StorageReference file:listResult.getItems()){
                file.getDownloadUrl().addOnSuccessListener(uri -> {
                    categoryList.add(uri.toString());
                    Log.e("Itemvalue",uri.toString());
                }).addOnSuccessListener(uri -> {
                    error.set(true);
                });
            }
        });
        if (error.get()){
            return null;
        }else {
            return categoryList;
        }
    }

}
