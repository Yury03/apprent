package com.example.apprent.presetation.mainpage;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.data.network.GetCategoryListImpl;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.usecase.GetCategoryList;

import java.util.List;

public class MainFragmentVM extends ViewModel {
    private GetCategoryList categoryUseCase;
    private List<CategoryItem> categoryItemList;
    private MutableLiveData<List<CategoryItem>> categoryItemListLiveData;
    private GetCategoryListImpl getCategoryListImpl=new GetCategoryListImpl();
    public MainFragmentVM() {
        categoryUseCase = new GetCategoryList(getCategoryListImpl);
        categoryItemListLiveData = new MutableLiveData<>();
    }
    public void getCategoryList() {
        categoryUseCase.execute(categoryItems -> {
            categoryItemList = categoryItems;
            categoryItemListLiveData.postValue(categoryItemList);
        });
    }
    public LiveData<List<CategoryItem>> getCategoryItemArrayList() {
        return categoryItemListLiveData;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
