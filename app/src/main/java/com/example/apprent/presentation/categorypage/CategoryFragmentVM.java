package com.example.apprent.presentation.categorypage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.data.network.GetCategoryListImpl;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.usecase.GetCategoryList;

import java.util.List;

public class CategoryFragmentVM extends ViewModel {
    private final GetCategoryList categoryUseCase;
    private List<CategoryItem> categoryItemList;
    private final MutableLiveData<List<CategoryItem>> categoryItemListLiveData;

    public CategoryFragmentVM() {
        GetCategoryListImpl getCategoryListImpl = new GetCategoryListImpl();
        categoryUseCase = new GetCategoryList(getCategoryListImpl);
        categoryItemListLiveData = new MutableLiveData<>();

    }

    public void getCategoryList() {
        categoryUseCase.execute(categoryItems -> {
            categoryItemList = categoryItems;
            categoryItemListLiveData.postValue(categoryItemList);
        });
    }

    public void getCategoryList(String subcategory) {
        categoryUseCase.execute(categoryItems -> {
            categoryItemList = categoryItems;
            categoryItemListLiveData.postValue(categoryItemList);
        }, subcategory);
    }

    public LiveData<List<CategoryItem>> getCategoryItemArrayList() {
        return categoryItemListLiveData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
