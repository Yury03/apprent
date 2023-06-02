package com.example.apprent.ui.main_activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
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

    private MainActivityVM vm;
    private final String TAG = "Debug: MainActivity";
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sp;
    private MaterialToolbar topAppBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//todo https://developer.android.com/develop/ui/views/search/training/setup
            @Override
            public boolean onQueryTextSubmit(String query) {
                vm.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

        });
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
                searchEditText.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lightGray));
            }
        });
        searchView.setVisibility(View.INVISIBLE);
        return true;
    }

    public MainActivityVM getVM() {
        if (this.vm == null) vm = new ViewModelProvider(this).get(MainActivityVM.class);
        return this.vm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topAppBar = findViewById(R.id.topAppBar);
        sp = getSharedPreferences(MainActivityVM.APP_PREFERENCES, Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        setSupportActionBar(topAppBar);
        vm = this.getVM();
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
                    int marginBottom = bottomNavigationView.getHeight();
                    Bundle bundleForCartFragment = new Bundle();
                    bundleForCartFragment.putInt("marginBottom", marginBottom);
                    navController.navigate(R.id.cartFragment, bundleForCartFragment);
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
                topAppBar.getMenu().findItem(R.id.action_search).setVisible(true);
                NavBackStackEntry backStackEntry = navController.getPreviousBackStackEntry();
                if (backStackEntry != null) {
                    if (backStackEntry.getDestination().getId() != R.id.productFragment) {
                        vm.setTitleOfTopBar(getResources().getString(R.string.category_fragment_name));
                    }
                }
            } else {
                layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                topAppBar.getMenu().findItem(R.id.action_search).setVisible(false);
                topAppBar.getMenu().findItem(R.id.action_search).collapseActionView();
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
                }
            }
        });
        vm.getTitleOfTopBar().observe(this, s -> topAppBar.setTitle(s));
        topAppBar.setNavigationOnClickListener(v -> {
            vm.setBackButtonState(true);
        });
        //todo -----------------------------------------------------
        boolean isLogIn = sp.getBoolean(getResources().getString(R.string.saved_log_in_key), false);
        if (!isLogIn) {
            navController.navigate(R.id.authenticationFragment);
        } else {
            topAppBar.getMenu().findItem(R.id.action_search).setVisible(false);
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

    @Override
    protected void onResume() {
        super.onResume();
        vm.createDatabase(getApplicationContext());
    }

    public MaterialToolbar getTopAppBar() {
        return topAppBar;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        navController.navigate(R.id.mainFragment);
//        bottomNavigationView.setSelectedItemId(R.id.home_page);
//        bottomNavigationView.performClick();
        //   todo bottomNavigationView.show???
    }
}