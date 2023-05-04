package com.example.apprent.ui.authentication_page.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.apprent.ui.authentication_page.SignInFragment;
import com.example.apprent.ui.authentication_page.SignUpFragment;

public class LoginPagerAdapter extends FragmentStateAdapter {


    public LoginPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {
            SignUpFragment signUpFragment = new SignUpFragment();
            return signUpFragment;
        } else {
            SignInFragment signInFragment = new SignInFragment();
            return signInFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}