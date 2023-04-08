package com.example.apprent.presentation.mainactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.apprent.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    // Create Object of the Adapter class
    MainActivityVM vm;
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        vm = new ViewModelProvider(this).get(MainActivityVM.class);
        bottomNavigationView.setSelectedItemId(vm.getFragmentID().getValue());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();//todo это надо обрабатывать?
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("profile", item.toString());
            int id = navController.getCurrentDestination().getId();
            if (item.getItemId() != id) {
                switch (item.getItemId()) {
                    case R.id.cart_page:
                        navController.navigate(R.id.cartFragment);
                        return true;
                    case R.id.home_page:
                        navController.navigate(R.id.mainFragment);
                        return true;
                    case R.id.profile_page:
                        navController.navigate(R.id.loginFragment);
                        return true;
                    case R.id.category_page:
                        navController.navigate(R.id.categoryFragment);
                        return true;
                    case R.id.search_page:
                        Toast.makeText(getApplicationContext(), "В РАЗРАБОТКЕ", Toast.LENGTH_SHORT).show();
                        return false;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        });
    }
}