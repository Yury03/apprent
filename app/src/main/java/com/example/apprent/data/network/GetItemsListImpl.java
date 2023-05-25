package com.example.apprent.data.network;

import android.util.ArrayMap;
import android.util.Log;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.domain.usecase.CategoryListCallback;
import com.example.apprent.domain.usecase.ProductListCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetItemsListImpl implements MainContract.GetItemsListData {
    private String pathStringForStorage = "";
    private final String pathStringForDB = "/aura";
    private String name;
    private String description;//todo где объявлять
    private String minPrice;
    private Boolean hasChild;
    private static final String TAG = "Debug: GetItemsListImpl";

    @Override
    public void getCategoryList(CategoryListCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(pathStringForStorage);
        pathStringForStorage = "";//todo
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference(pathStringForDB);
        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<Task<CategoryItem>> tasks = new ArrayList<>();
            for (StorageReference file : listResult.getItems()) {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".jpg")) {
                    tasks.add(file.getDownloadUrl().continueWithTask(task -> {
                        String imageUrl = String.valueOf(task.getResult());
                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('.'));
                        return databaseRef.child(temp).get().addOnSuccessListener(dataSnapshot -> {
                            name = dataSnapshot.child("name").getValue(String.class);
                            hasChild = dataSnapshot.child("hasChild").getValue(Boolean.class);
                        }).continueWith(task1 -> new CategoryItem(imageUrl, name, temp.substring(temp.lastIndexOf('/')), hasChild)).addOnFailureListener(e -> {
                        });
                    }));
                } else {
                    Log.e(TAG, file.getPath());
                }
            }
            Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
                List<CategoryItem> categoryItemList = new ArrayList<>();
                for (Object obj : results) {
                    categoryItemList.add((CategoryItem) obj);
                }
                callback.onItemListLoaded(categoryItemList);
            });
        });
    }

    @Override
    public void getCategoryList(CategoryListCallback callback, String subCategory) {
        pathStringForStorage += subCategory;
        Log.d(TAG, subCategory + "  " + pathStringForStorage);
        this.getCategoryList(callback);
    }

    @Override
    public void getProductsList(ProductListCallback callback, String category) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(pathStringForStorage + category);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference(pathStringForDB);
        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<Task<ProductItem>> tasks = new ArrayList<>();
            Map<String, List<String>> directoryMap = new ArrayMap<>();
            for (StorageReference productItemsImages : listResult.getItems()) {
                String itemName = productItemsImages.getName();
                String key = itemName.substring(itemName.lastIndexOf('.'));
                Log.i(TAG, productItemsImages.getName());
                List<String> imagesPath;
                imagesPath = directoryMap.get(key);
                if (imagesPath == null) {
                    imagesPath = new ArrayList<>();
                    directoryMap.put(key, imagesPath);
                }
                tasks.add(productItemsImages.getDownloadUrl().continueWithTask(task -> {
                    String imageUrl = String.valueOf(task.getResult());
                    String itemPath = productItemsImages.getPath().substring(0, productItemsImages.getPath().lastIndexOf('.'));
                    return databaseRef.child(itemPath).get().addOnSuccessListener(dataSnapshot -> {
                        name = dataSnapshot.child("name").getValue(String.class);
                        description = dataSnapshot.child("description").getValue(String.class);
                        minPrice = dataSnapshot.child("minPrice").getValue(String.class);
                    }).continueWith(task1 -> new ProductItem(imageUrl, name, description, minPrice, itemPath)).addOnFailureListener(e -> {
                    });
                }));
            }
            for (StorageReference productItemsFolders : listResult.getPrefixes()) {
                String itemName = productItemsFolders.getName();
                Log.i(TAG, productItemsFolders.getName());
                List<String> imagesPath;
                imagesPath = directoryMap.get(itemName);
                if (imagesPath == null) {
                    imagesPath = new ArrayList<>();
                    directoryMap.put(itemName, imagesPath);
                }
                Log.e(TAG, productItemsFolders.getPath());
                productItemsFolders.listAll().addOnSuccessListener(listResult1 -> {
                    List<String> currentImagesPath = directoryMap.get(itemName);
                    for (StorageReference productImage : listResult1.getItems()) {
                        productImage.getDownloadUrl().addOnSuccessListener(uri -> currentImagesPath.add(uri.toString()));//todo выше есть проверка
                    }
                });
            }//TODO что тут с потоками?
            Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
                List<ProductItem> productItemList = new ArrayList<>();
                for (Object obj : results) {
                    if (obj != null) {
                        Log.e(TAG, "key is " + ((ProductItem) obj).getFullPath());
                        String key = ((ProductItem) obj).getFullPath().substring(((ProductItem) obj).getFullPath().lastIndexOf('/') + 1);
                        Log.e(TAG, "key is " + key);
                        ((ProductItem) obj).setImagesPath(directoryMap.get(key));
                        productItemList.add((ProductItem) obj);
                    }
                }
                callback.onItemListLoaded(productItemList);
            });
        });
    }

    @Override
    public void getSearchResults(ProductListCallback callback, String query, String path) {
        getProductsList(callback, "/category/subcategory_1/subcategory_2");
    }
}
