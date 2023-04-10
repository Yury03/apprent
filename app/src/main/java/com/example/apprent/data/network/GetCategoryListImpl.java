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
    private final String pathStringForStorage = "/category";
    private final String pathStringForDB = "/aura";
    private List<Task<CategoryItem>> tasks = new ArrayList<>();
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
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpeg")) {
                    // Создаем задачи для загрузки файлов из Firebase Storage и чтения данных из Firebase Realtime Database
                    tasks.add(file.getDownloadUrl().continueWithTask(task -> {
                            String imageUrl = String.valueOf(task.getResult());
                            String temp = file.getPath().substring(0, file.getPath().lastIndexOf("."));

                            return databaseRef.child(temp).get().addOnSuccessListener(dataSnapshot -> {
                                Log.e("MyTag", temp + "/" + "name");
                                name = dataSnapshot.child("name").getValue(String.class);
                                if (name != null) Log.e("MyTag1", name);
                            }).continueWith(task1 -> new CategoryItem(imageUrl, name)).addOnFailureListener(e -> {
                                Log.e("MyTag", "Error getting name from database", e);
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
//        pathStringForStorage = "subcategory/" + subCategory;
        this.getCategoryList(callback);
    }

    @Override
    public void getProductsList() {
        //todo
    }

}
