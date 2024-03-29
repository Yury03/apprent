package com.example.apprent.ui.main_activity;

import static com.example.apprent.domain.models.AuraUser.State.USER_NOT_SIGN_IN;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.apprent.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel vm;
    private final String TAG = "Debug: MainActivity";
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar topAppBar;


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//todo https://developer.android.com/develop/ui/views/search/training/setup
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                vm.search(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//
//        });
//        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
//            if (!hasFocus) {
//                EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//                searchEditText.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lightGray));
//            }
//        });
//        searchView.setVisibility(View.INVISIBLE);
//        return true;
//    }

    public MainActivityViewModel getViewModel() {
        if (this.vm == null) vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        return this.vm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        topAppBar = findViewById(R.id.topAppBar);
        SharedPreferences sp = getSharedPreferences(MainActivityViewModel.APP_PREFERENCES, Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setSupportActionBar(topAppBar);
        vm = this.getViewModel();
        vm.setAppContext(getApplicationContext());
        vm.setSharedPreferences(sp);
        vm.setMaterialToolbar(topAppBar);
        bottomNavigationView.setSelectedItemId(vm.getFragmentID().getValue());//todo
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();//todo
        vm.setNavController(navController);
        vm.setSupportFragmentManager(getSupportFragmentManager());
        vm.setBottomNavigationView(bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("profile", item.toString());
            int id = bottomNavigationView.getSelectedItemId();
            if (item.getItemId() != id) {
                int itemId = item.getItemId();
                if (itemId == R.id.cart_page) {
                    navController.navigate(R.id.cartFragment);
                    return true;
                } else if (itemId == R.id.home_page) {
                    navController.navigate(R.id.mainFragment);
                    return true;
                } else if (itemId == R.id.profile_page) {
                    navController.navigate(R.id.profileFragment);
                    return true;
                } else if (itemId == R.id.category_page) {
                    navController.navigate(R.id.categoryFragment);
                    return true;
                }
            }
            return false;
        });
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            Log.i(TAG, "nav controller listener!");
            HideBottomViewOnScrollBehavior hideBottomViewOnScrollBehavior = new HideBottomViewOnScrollBehavior();
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
            if (navDestination.getId() == R.id.categoryFragment) {
                layoutParams.setBehavior(hideBottomViewOnScrollBehavior);
                NavBackStackEntry backStackEntry = navController.getPreviousBackStackEntry();
                if (backStackEntry != null
                        && backStackEntry.getDestination().getId() != R.id.productFragment) {
                    vm.setTitleOfTopBar(getResources().getString(R.string.category_fragment_name));
                }
            } else {
                layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                if (navDestination.getId() == R.id.cartFragment) {
                    vm.hideBackButton();
                    vm.setTitleOfTopBar(getResources().getString(R.string.cart_fragment_name));
                } else if (navDestination.getId() == R.id.authenticationFragment) {
                    vm.hideBackButton();
                    vm.setTitleOfTopBar(getResources().getString(R.string.authentication_fragment_name));
                } else if (navDestination.getId() == R.id.mainFragment) {
                    vm.hideBackButton();
                    vm.setTitleOfTopBar(getResources().getString(R.string.home_fragment_name));
                } else if (navDestination.getId() == R.id.productFragment) {
                    vm.showBackButton();
                    vm.setTitleOfTopBar(getResources().getString(R.string.product_fragment_name));
                } else if (navDestination.getId() == R.id.profileFragment) {
                    vm.hideBackButton();
                    vm.setTitleOfTopBar(getResources().getString(R.string.profile_fragment_name));
                } else if (navDestination.getId() == R.id.adminFragment) {
                    vm.hideBackButton();
                    vm.getBottomNavigationView().setVisibility(View.GONE);
                    topAppBar.setTitle("Страница администратора");
                }
            }
        });
        vm.getTitleOfTopBar().observe(this, s -> topAppBar.setTitle(s));
        topAppBar.setNavigationOnClickListener(v -> {
            vm.setBackButtonState(true);
        });
        vm.createDatabase(getApplicationContext());
        //todo -----------------------------------------------------
        int isLogIn = sp.getInt(getResources().getString(R.string.saved_log_in_key), USER_NOT_SIGN_IN.stateId);
        Log.i("IS_LOG_IN_STATE: ", String.valueOf(isLogIn));
        switch (isLogIn) {
            case 0, 1, 2, 3 -> {//USER_SIGN_IN//todo
//                navController.navigate(R.id.home_page);
            }
            case 4 -> {//ADMIN_SIGN_IN
                navController.navigate(R.id.adminFragment);
            }
            case 5 -> { //USER_NOT_SIGN_IN
                navController.navigate(R.id.authenticationFragment);
            }
            default -> throw new IllegalStateException("Unexpected value: " + isLogIn);
        }
        //todo -----------------------------------------------------
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (vm.getCartDatabase() != null) {
            vm.getCartDatabase().close();
        }
    }


    public MaterialToolbar getTopAppBar() {
        return topAppBar;
    }

    @Override
    public void onBackPressed() {
//        navController.navigate(R.id.mainFragment);
//        bottomNavigationView.setSelectedItemId(R.id.home_page);
//        bottomNavigationView.performClick();
        //   todo bottomNavigationView.show???
        if (topAppBar.getNavigationIcon() != null) {
            vm.setBackButtonState(true);
        } else if (navController.getCurrentDestination().getId() == R.id.mainFragment) {
            super.onBackPressed();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.home_page);
        }

    }
}