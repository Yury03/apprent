package com.example.apprent.ui.main_activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.apprent.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MainActivityVM vm;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences(MainActivityVM.APP_PREFERENCES, Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        topAppBar = findViewById(R.id.topAppBar);
        vm = new ViewModelProvider(this).get(MainActivityVM.class);
        vm.setSharedPreferences(sp);
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
                    boolean isLogIn = sp.getBoolean(getResources().getString(R.string.saved_log_in_key), false);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("MainActivityVM", vm);
                    if (isLogIn) {
                        navController.navigate(R.id.profileFragment, bundle);
                    } else {
                        navController.navigate(R.id.authenticationFragment, bundle);
                    }
                    return true;
                } else if (itemId == R.id.category_page) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("MainActivityVM", vm);
                    navController.navigate(R.id.categoryFragment, bundle);
                    return true;
                } else if (itemId == R.id.search_page) {
                    Toast.makeText(getApplicationContext(), "В РАЗРАБОТКЕ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return false;
        });
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.categoryFragment) {
                NavBackStackEntry backStackEntry = navController.getPreviousBackStackEntry();
                if (backStackEntry != null) {
                    if (backStackEntry.getDestination().getId() != R.id.productFragment) {
                        vm.setTitleOfTopBar(getResources().getString(R.string.category_fragment_name));
                    }
                }
            } else if (navDestination.getId() == R.id.cartFragment) {
                vm.setTitleOfTopBar(getResources().getString(R.string.cart_fragment_name));
            } else if (navDestination.getId() == R.id.authenticationFragment) {
                vm.setTitleOfTopBar(getResources().getString(R.string.profile_fragment_name));
            } else if (navDestination.getId() == R.id.mainFragment) {
                vm.setTitleOfTopBar(getResources().getString(R.string.home_fragment_name));
            } else if (navDestination.getId() == R.id.productFragment) {
                vm.setTitleOfTopBar(getResources().getString(R.string.product_fragment_name));
            }
        });
        vm.getTitleOfTopBar().observe(this, s -> topAppBar.setTitle(s));
        //        vm.getCurrentFragment().observe(this, navDestination -> {
        //            Log.e("MyApp", "navigation Destination is NULL!!!");
        //            if (navDestination != null) {
        //                switch (navDestination.getId()) {
        //                    case R.id.categoryFragment:
        //                        topAppBar.setTitle(R.string.category_fragment_name);
        //
        //                    default:
        //                        break;
        //                }
        //            }
        //
        //        });todo remove
        //        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {//todo feature
        //            @Override
        //            public boolean onMenuItemClick(MenuItem item) {
        //                switch (item.getItemId()){
        //                    default:
        //                        return false;
        //                }
        //            }
        //        });
        topAppBar.setNavigationOnClickListener(v -> {
            vm.setBackButtonState(true);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navController.navigate(R.id.mainFragment);
        bottomNavigationView.setSelectedItemId(R.id.home_page);
        bottomNavigationView.performClick();
        //   todo bottomNavigationView.show???
    }
}