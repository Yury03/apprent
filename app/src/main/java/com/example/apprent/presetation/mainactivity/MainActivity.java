package com.example.apprent.presetation.mainactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.apprent.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
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
        navController = navHostFragment.getNavController();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.cart_page:
                    navController.navigate(R.id.cartFragment);
                    return true;
                case R.id.home_page:
                    return true;
                case R.id.profile_page:
                    navController.navigate(R.id.loginFragment2);
                case R.id.category_page:
                    return true;
                case R.id.search_page:
                    return true;
                default:
                    return false;
            }
        });
//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            int destId = destination.getId();
//            if (destId == R.id.mainFragment) {
//                bottomNavigationView.setSelectedItemId(R.id.home_page);
//            } else if (destId == R.id.cartFragment) {
//                bottomNavigationView.setSelectedItemId(R.id.cart_page);
//            } else if (destId == R.id.loginFragment2) {
//                bottomNavigationView.setSelectedItemId(R.id.profile_page);
//            }
//        });//todo для обработки меню нижней навигации после нажатия системной кнопки "назад"
    }
}