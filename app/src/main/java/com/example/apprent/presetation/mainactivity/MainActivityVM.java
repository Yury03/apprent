package com.example.apprent.presetation.mainactivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.R;

public class MainActivityVM extends ViewModel {
    private final MutableLiveData<Integer> fragmentID = new MutableLiveData<>(R.id.home_page);

    public LiveData<Integer> getFragmentID() {
        return fragmentID;
    }

}
