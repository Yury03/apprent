package com.example.apprent.ui.authentication_page.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.apprent.ui.authentication_page.LoginFragmentVM;
import com.example.apprent.ui.authentication_page.SignInFragment;
import com.example.apprent.ui.authentication_page.SignUpFragment;

public class LoginPagerAdapter extends FragmentStateAdapter {
    private final LoginFragmentVM loginFragmentVM;

    public LoginPagerAdapter(@NonNull Fragment fragment, LoginFragmentVM loginFragmentVM) {
        super(fragment);
        this.loginFragmentVM = loginFragmentVM;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("LoginFragmentVM", loginFragmentVM);
        if (position == 1) {
            SignUpFragment signUpFragment = new SignUpFragment();
            signUpFragment.setArguments(bundle);
            return signUpFragment;
        } else {
            SignInFragment signInFragment = new SignInFragment();
            signInFragment.setArguments(bundle);
            return signInFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}