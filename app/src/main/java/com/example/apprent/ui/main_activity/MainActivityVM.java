package com.example.apprent.ui.main_activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.example.apprent.R;
import com.example.apprent.data.cart_database.CartDatabase;
import com.example.apprent.data.cart_database.dao.CartDao;
import com.example.apprent.data.cart_database.entity.CartProductEntity;
import com.example.apprent.data.network.GetItemsListImpl;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.domain.usecase.GetSearchResults;
import com.example.apprent.domain.usecase.ProductListCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivityVM extends ViewModel implements Serializable {
    private final MutableLiveData<Integer> fragmentID = new MutableLiveData<>(R.id.home_page);
    private final MutableLiveData<Boolean> backButtonState = new MutableLiveData<>(false);

    public LiveData<List<ProductItem>> getSearchResultsForCategoryFragment() {
        return searchResultsForCategoryFragment;
    }

    private final MutableLiveData<List<ProductItem>> searchResultsForCategoryFragment = new MutableLiveData<>();
    private NavController navController;

    public String getPathForCategoryFragment() {
        return pathForCategoryFragment;
    }

    public void setPathForCategoryFragment(String pathForCategoryFragment) {
        this.pathForCategoryFragment = pathForCategoryFragment;
    }

    private String pathForCategoryFragment;//todo

    public void setMaterialToolbar(MaterialToolbar materialToolbar) {
        this.materialToolbar = materialToolbar;
    }

    private MaterialToolbar materialToolbar;

    private Context appContext;
    private CartDatabase cartDatabase;
    private CartDao cartDao;

    private BottomNavigationView bottomNavigationView;
    public static final String APP_PREFERENCES = "MainSettings";
    private SharedPreferences sharedPreferences;
    private final MutableLiveData<String> titleOfTopBar = new MutableLiveData<>();
    private final MutableLiveData<NavDestination> currentFragment = new MutableLiveData<>();

    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    private FragmentManager supportFragmentManager;

    public LiveData<Integer> getFragmentID() {
        return fragmentID;
    }

    public LiveData<Boolean> getBackButtonState() {
        return backButtonState;
    }

    public LiveData<String> getTitleOfTopBar() {
        return titleOfTopBar;
    }

    public void setTitleOfTopBar(String title) {
        this.titleOfTopBar.postValue(title);
    }

    public void setBackButtonState(boolean state) {
        backButtonState.setValue(state);
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
        Log.d("MyApp", navController.toString());
        this.currentFragment.postValue(navController.getCurrentDestination());
    }

    public LiveData<NavDestination> getCurrentFragment() {
        Log.d("MyApp", navController.toString() + "  " + currentFragment.getValue());
        return currentFragment;
    }

    public NavController getNavController() {
        return navController;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public void reservation() {
        //todo
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectDate(ProductItem productItem) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTheme(com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
        builder.setTitleText("Выберите диапазон дат");
        CalendarConstraints.Builder calendarConstraintsBuilder = new CalendarConstraints.Builder();
        calendarConstraintsBuilder.setValidator(DateValidatorPointForward.now());
        calendarConstraintsBuilder.setEnd(System.currentTimeMillis() + 1000L * 60L * 60L * 24L * 365L * 10L);//todo?
        calendarConstraintsBuilder.setFirstDayOfWeek(Calendar.MONDAY);
        builder.setCalendarConstraints(calendarConstraintsBuilder.build());
        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
        picker.addOnPositiveButtonClickListener(selection -> {
            long start = selection.first;
            long end = selection.second;
            int days = (int) TimeUnit.DAYS.convert(end - start, TimeUnit.MILLISECONDS);
            int price = priceParser(productItem.getMinPrice());
            CartProductEntity cartProductEntity = new CartProductEntity(productItem.getName(), new Date(selection.first), days, productItem.getMainImagePath(), price);
            addToCart(cartProductEntity);
        });
        picker.show(supportFragmentManager, picker.toString());
    }

    private int priceParser(String minPrice) {
        int price;
        minPrice = minPrice.substring(3);
        price = Integer.parseInt(minPrice);
        return price;
    }

    public void addToCart(CartProductEntity product) {
        Executors.newSingleThreadExecutor().execute(() -> cartDao.insert(product));
    }

    public void removeFromCart(CartProductEntity cartProduct) {
        Executors.newSingleThreadExecutor().execute(() -> cartDao.delete(cartProduct));
    }

    public void changeDataCartDB(int id, CartProductEntity cartProductEntity) {
        Executors.newSingleThreadExecutor().execute(() -> {
            cartDao.getById(id).setQuantity(cartProductEntity.getQuantity());
            Log.i("DB: ", String.valueOf(cartProductEntity.getQuantity()));
            cartDao.getById(id).setPeriod(cartProductEntity.getPeriod());
            cartDao.getById(id).setDate(cartProductEntity.getDate());
            cartDao.update(cartProductEntity);
        });

    }


    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public void createDatabase(Context context) {
        cartDatabase = CartDatabase.getInstance(context);
        cartDao = cartDatabase.cartDao();
    }

    public void showBackButton() {
        materialToolbar.setNavigationIcon(R.drawable.back_arrow);
    }

    public void hideBackButton() {
        materialToolbar.setNavigationIcon(null);
    }


    public CartDatabase getCartDatabase() {
        return this.cartDatabase;
    }


    public void search(String query) {
        GetItemsListImpl getItemsList = new GetItemsListImpl();
        GetSearchResults getSearchResults = new GetSearchResults(getItemsList);
        getSearchResults.execute(searchResultsForCategoryFragment::postValue, query, this.pathForCategoryFragment);
    }
}
