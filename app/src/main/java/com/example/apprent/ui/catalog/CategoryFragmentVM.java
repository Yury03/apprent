package com.example.apprent.ui.catalog;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.data.network.GetItemsListImpl;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.domain.usecase.GetCategoryList;
import com.example.apprent.domain.usecase.GetProductList;
import com.example.apprent.ui.main_activity.MainActivityVM;

import java.util.List;

public class CategoryFragmentVM extends ViewModel {
    private MainActivityVM mainActivityVM;
    private final GetCategoryList categoryUseCase;
    private final GetProductList productUseCase;
    private final MutableLiveData<ProductItem> openProduct;
    private String fragmentPath = "/category";
    private final MutableLiveData<List<CategoryItem>> categoryListLiveData;
    private final MutableLiveData<List<ProductItem>> productListLiveData;

    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }

    private MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>(true);
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
        showProgressBar.postValue(true);
        categoryUseCase.execute(categoryListLiveData::postValue, path);
        fragmentPath = path;
        mainActivityVM.setPathForCategoryFragment(fragmentPath);
        updateBackButton();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        showProgressBar.postValue(false);
        this.adapter.postValue(adapter);
    }

    public LiveData<RecyclerView.Adapter> getAdapter() {
        return this.adapter;
    }

    public void getProductList(String path) {
        showProgressBar.postValue(true);
        productUseCase.execute(productItems -> {
            productListLiveData.postValue(productItems);
        }, path);
        fragmentPath = path;
        mainActivityVM.setPathForCategoryFragment(fragmentPath);
        updateBackButton();
    }


    private void updateBackButton() {
        if (this.fragmentPath.equals("/category")) {
            mainActivityVM.hideBackButton();
        } else {
            mainActivityVM.showBackButton();
        }
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
        showProgressBar.setValue(true);
        if (this.openProduct.getValue() != null && productItem.getName().equals(this.openProduct.getValue().getName())) {
            this.openProduct.postValue(null);
        }
        this.openProduct.postValue(productItem);
        showProgressBar.postValue(false);
    }

    public String getFragmentPath() {
        return fragmentPath;
    }

    public void setPath(String fragmentPath) {
        mainActivityVM.setPathForCategoryFragment(fragmentPath);
        this.fragmentPath = fragmentPath;
    }

    public void reservation(FragmentManager fragmentManager) {
        mainActivityVM.reservation(fragmentManager);
    }

    public void selectDate(ProductItem productItem) {
        mainActivityVM.selectDate(productItem);//todo
    }

    public void setMainVM(MainActivityVM mainActivityVM) {
        this.mainActivityVM = mainActivityVM;
        updateBackButton();
    }
}
