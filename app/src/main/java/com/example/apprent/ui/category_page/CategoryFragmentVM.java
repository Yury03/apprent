package com.example.apprent.ui.category_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.data.network.GetItemsListImpl;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.domain.usecase.GetCategoryList;
import com.example.apprent.domain.usecase.GetProductList;

import java.util.List;

public class CategoryFragmentVM extends ViewModel {
    private final GetCategoryList categoryUseCase;
    private final GetProductList productUseCase;
    private MutableLiveData<ProductItem> openProduct;
    private String fragmentPath = "/category";
    private MutableLiveData<List<CategoryItem>> categoryListLiveData;
    private MutableLiveData<List<ProductItem>> productListLiveData;
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<RecyclerView.Adapter> adapter = new MutableLiveData<>();

    public LiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.postValue(title);
    }

    public CategoryFragmentVM() {
        GetItemsListImpl getItemsListImpl = new GetItemsListImpl();
        categoryUseCase = new GetCategoryList(getItemsListImpl);
        productUseCase = new GetProductList(getItemsListImpl);
        categoryListLiveData = new MutableLiveData<>();
        productListLiveData = new MutableLiveData<>();
        openProduct = new MutableLiveData<>();
    }

    public void getCategoryList(String path) {
        /**subcategory contains only the latest directory
         Example: /subcategory_1 */
        categoryUseCase.execute(categoryItems -> {
            categoryListLiveData.postValue(categoryItems);
        }, path);
        fragmentPath = path;

    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter.postValue(adapter);
    }

    public LiveData<RecyclerView.Adapter> getAdapter() {
        return this.adapter;
    }

    public void getProductList(String path) {
        productUseCase.execute(productItems -> {
            productListLiveData.postValue(productItems);
        }, path);
        fragmentPath = path;
    }

    public LiveData<List<CategoryItem>> getCategoryItemArrayList() {
        return categoryListLiveData;
    }

    public LiveData<List<ProductItem>> getProductItemArrayList() {
        return productListLiveData;
    }

    public LiveData<ProductItem> getOpenProduct() {
        return openProduct;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void goToProductFragment(ProductItem productItem) {
        if (this.openProduct.getValue() != null && productItem.getName().equals(this.openProduct.getValue().getName())) {
            this.openProduct.postValue(null);
        }
        this.openProduct.postValue(productItem);
    }

    public String getFragmentPath() {
        return fragmentPath;
    }

    public void setPath(String fragmentPath) {
        this.fragmentPath = fragmentPath;
    }
}
