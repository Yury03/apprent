package com.example.apprent.data.network;

import android.util.Log;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.usecase.ItemsListCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryListImpl implements MainContract.GetItemsListData {
    private String pathStringForStorage = "/category";
    private String pathStringForDB = "/aura";
    private String name;

    @Override
    public void getCategoryList(ItemsListCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(pathStringForStorage);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference(pathStringForDB);
        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<Task<CategoryItem>> tasks = new ArrayList<>();
            for (StorageReference file : listResult.getItems()) {
                Log.d("loadJSON", file.getPath());
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".jpg")) {
                    tasks.add(file.getDownloadUrl().continueWithTask(task -> {
                        String imageUrl = String.valueOf(task.getResult());
                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('.'));
                        Log.d("loadJSON", temp.substring(temp.lastIndexOf('/')));
                        return databaseRef.child(temp).get().addOnSuccessListener(dataSnapshot -> {
                            name = dataSnapshot.child("name").getValue(String.class);
                        }).continueWith(task1 -> new CategoryItem(imageUrl, name, temp.substring(temp.lastIndexOf('/')))).addOnFailureListener(e -> {
                        });
                    }));
                }
            }
            Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
                List<CategoryItem> categoryItemList = new ArrayList<>();
                for (Object obj : results) {
                    categoryItemList.add((CategoryItem) obj);
                }
                callback.onCategoryListLoaded(categoryItemList);
            });
        });
    }


    @Override
    public void getCategoryList(ItemsListCallback callback, String subCategory) {
        pathStringForStorage += subCategory;
        Log.d("loadJSON", "default+subCategory = " + pathStringForStorage);
        this.getCategoryList(callback);
    }

    @Override
    public void getProductsList() {
        //todo
    }

}
