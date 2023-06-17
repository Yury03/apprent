package com.example.apprent.data.network;

import android.annotation.SuppressLint;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apprent.domain.MainContract;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.domain.usecase.get_category.CategoryListCallback;
import com.example.apprent.domain.usecase.get_links.LinksCallback;
import com.example.apprent.domain.usecase.get_products.ProductListCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetItemsListImpl implements MainContract.GetListData {
    private String PATH_STRING_FOR_STORAGE = "";
    private final String pathStringForDB = "/aura";
    private String name;
    private String description;//todo где объявлять
    private String minPrice;
    private Boolean hasChild;
    private static final String TAG = "Debug: GetItemsListImpl";

    @Override
    public void getCategoryList(CategoryListCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(PATH_STRING_FOR_STORAGE);
        PATH_STRING_FOR_STORAGE = "";//todo
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference(pathStringForDB);
        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<Task<CategoryItem>> tasks = new ArrayList<>();
            for (StorageReference file : listResult.getItems()) {
                String fileName = file.getName();
                if (fileName.endsWith(".png") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")) {
                    tasks.add(file.getDownloadUrl().continueWithTask(task -> {
                        String imageUrl = String.valueOf(task.getResult());
                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('.'));
                        OnSuccessListener<DataSnapshot> dataSnapshotOnSuccessListener = dataSnapshot -> {
                            name = dataSnapshot.child("name").getValue(String.class);
                            hasChild = dataSnapshot.child("hasChild").getValue(Boolean.class);
                        };
                        return databaseRef
                                .child(temp)
                                .get()
                                .addOnSuccessListener(dataSnapshotOnSuccessListener)
                                .continueWith(task1 -> new CategoryItem(imageUrl, name, temp.substring(temp.lastIndexOf('/')), hasChild))
                                .addOnFailureListener(e -> {
                                });
                    }));
                } else {
                    Log.e(TAG, file.getPath());
                }
            }
            getListTask(callback, tasks);
        });
    }

    @NonNull
    private static Task<List<Object>> getListTask(CategoryListCallback callback, List<Task<CategoryItem>> tasks) {
        return Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
            List<CategoryItem> categoryItemList = new ArrayList<>();
            for (Object obj : results) {
                if (obj instanceof CategoryItem) {
                    categoryItemList.add((CategoryItem) obj);
                } else {
                    throw new IllegalStateException("Описание ошибки про херовый каст");
                }
            }
            callback.onItemListLoaded(categoryItemList);
        });
    }

    @Override
    public void getCategoryList(CategoryListCallback callback, String subCategory) {
        PATH_STRING_FOR_STORAGE += subCategory;
        Log.d(TAG, subCategory + "  " + PATH_STRING_FOR_STORAGE);
        this.getCategoryList(callback);
    }

    @Override
    public void getProductsList(ProductListCallback callback, String category) {
        Log.w(TAG, pathStringForDB);
        Log.w(TAG, PATH_STRING_FOR_STORAGE);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(PATH_STRING_FOR_STORAGE + category);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference(pathStringForDB);
        Log.i(TAG, pathStringForDB);
        storageRef.listAll().addOnSuccessListener(listResult -> {
            List<Task<ProductItem>> tasks = new ArrayList<>();
            Map<String, List<String>> directoryMap = new ArrayMap<>();
            for (StorageReference productItemsImages : listResult.getItems()) {
                String itemName = productItemsImages.getName();
                String key = itemName.substring(itemName.lastIndexOf('.'));
                Log.i(TAG, productItemsImages.getName());
                List<String> imagesPath = directoryMap.get(key);
                if (imagesPath == null) {
                    imagesPath = new ArrayList<>();
                    directoryMap.put(key, imagesPath);
                }
                tasks.add(productItemsImages.getDownloadUrl().continueWithTask(task -> {
                    String imageUrl = String.valueOf(task.getResult());
                    String itemPath = productItemsImages.getPath()
                            .substring(0, productItemsImages.getPath().lastIndexOf('.'));
                    return databaseRef.child(itemPath).get().addOnSuccessListener(dataSnapshot -> {
                                name = dataSnapshot.child("name").getValue(String.class);
                                description = dataSnapshot.child("description").getValue(String.class);
                                minPrice = dataSnapshot.child("minPrice").getValue(String.class);
                            }).continueWith(task1 -> new ProductItem(imageUrl, name, description, minPrice, itemPath))
                            .addOnFailureListener(e -> {
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
                        productImage.getDownloadUrl().addOnSuccessListener(uri -> currentImagesPath
                                .add(uri.toString()));//todo выше есть проверка
                    }
                });
            }
            Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
                List<ProductItem> productItemList = new ArrayList<>();
                for (Object obj : results) {
                    if (obj != null) {
                        Log.e(TAG, "key is " + ((ProductItem) obj).getFullPath());
                        String key = ((ProductItem) obj).getFullPath()
                                .substring(((ProductItem) obj).getFullPath().lastIndexOf('/') + 1);
                        Log.e(TAG, "key is " + key);
                        ((ProductItem) obj).setImagesPath(directoryMap.get(key));
                        productItemList.add((ProductItem) obj);
                    }
                }
                Log.w(TAG, productItemList.get(0).getName());
                callback.onItemListLoaded(productItemList);
            });
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void getSearchResults(ProductListCallback callback, String query, String path) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("/aura/category");

        List<ProductItem> productItemList = new ArrayList<>();
        Query query1 = databaseRef.orderByChild("name").startAt(query).endAt(query + "\uf8ff");

        List<Task<ProductItem>> tasks = new ArrayList<>();
        query1.get().addOnSuccessListener(dataSnapshot -> {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String key = snapshot.getKey();
                DataSnapshot itemSnapshot = snapshot.child(key);
                String storagePath = itemSnapshot.getRef().getPath().toString().substring(5);//LOG: /category/subcategory_1/item_1
                StorageReference storageRef = storage
                        .getReference()
                        .child(PATH_STRING_FOR_STORAGE + storagePath);

                /** tasks.add(storageRef.getDownloadUrl().continueWithTask(task -> {
                 String imageUrl = String.valueOf(task.getResult());
                 String itemPath = storageRef.getPath()
                 .substring(0, storageRef.getPath().lastIndexOf('.'));
                 return databaseRef.child(itemPath).get().addOnSuccessListener(dataSnapshot -> {
                 name = dataSnapshot.child("name").getValue(String.class);
                 description = dataSnapshot.child("description").getValue(String.class);
                 minPrice = dataSnapshot.child("minPrice").getValue(String.class);
                 }).continueWith(task1 -> new ProductItem(imageUrl, name, description, minPrice, itemPath))
                 .addOnFailureListener(e -> {
                 });
                 }));*/

//                itemSnapshot
//                new ProductItem()
            }
//            callback.onItemListLoaded();
        });

//        Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
//            List<ProductItem> productItemList = new ArrayList<>();
//            for (Object obj : results) {
//                if (obj != null) {
//                    Log.e(TAG, "key is " + ((ProductItem) obj).getFullPath());
//                    String key = ((ProductItem) obj).getFullPath()
//                            .substring(((ProductItem) obj).getFullPath().lastIndexOf('/') + 1);
//                    Log.e(TAG, "key is " + key);
//                    ((ProductItem) obj).setImagesPath(directoryMap.get(key));
//                    productItemList.add((ProductItem) obj);
//                }
//            }
//            Log.w(TAG, productItemList.get(0).getName());
//            callback.onItemListLoaded(productItemList);
//        });
    }

    @Override
    public void getBannerImages(LinksCallback linksCallback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        List<String> linksList = new ArrayList<>();
        StorageReference storageRef = storage.getReference().child("/banners");
        storageRef.listAll()
                .onSuccessTask(listResult -> {
                    List<StorageReference> items = listResult.getItems();
                    List<Task<String>> downloadUrlTasks = new ArrayList<>();
                    for (StorageReference item : items) {
                        Log.e(TAG, item.getName());
                        Task<String> downloadUrlTask = item.getDownloadUrl()
                                .onSuccessTask(uri -> {
                                    String imageUrl = uri.toString();
                                    linksList.add(imageUrl);
                                    return Tasks.forResult(imageUrl);
                                })
                                .addOnFailureListener(exception -> {
                                    Log.e(TAG, exception.toString());
                                });
                        downloadUrlTasks.add(downloadUrlTask);
                    }
                    return Tasks.whenAll(downloadUrlTasks)
                            .continueWith(task -> linksList);
                })
                .addOnSuccessListener(linksCallback::onLinksLoaded)
                .addOnFailureListener(exception -> {
                    Log.e(TAG, exception.toString());
                });
    }
}
