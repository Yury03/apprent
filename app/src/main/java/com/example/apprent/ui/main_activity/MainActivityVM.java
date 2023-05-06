package com.example.apprent.ui.main_activity;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.example.apprent.R;

import java.io.Serializable;

public class MainActivityVM extends ViewModel implements Serializable {
    private final MutableLiveData<Integer> fragmentID = new MutableLiveData<>(R.id.home_page);
    private final MutableLiveData<Boolean> backButtonState = new MutableLiveData<>(false);
    private NavController navController;
    public static final String APP_PREFERENCES = "MainSettings";
    private SharedPreferences sharedPreferences;
    private final MutableLiveData<String> titleOfTopBar = new MutableLiveData<>();
    private final MutableLiveData<NavDestination> currentFragment = new MutableLiveData<>();

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
}
