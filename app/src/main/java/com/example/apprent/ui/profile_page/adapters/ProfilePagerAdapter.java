package com.example.apprent.ui.profile_page.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.apprent.ui.profile_page.AccountFragment;

public class ProfilePagerAdapter extends FragmentStateAdapter {
    private final Bundle bundle;

    public ProfilePagerAdapter(@NonNull Fragment fragment, Bundle bundle) {
        super(fragment);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment result;
        result = new AccountFragment();
        if (position == 0) result.setArguments(bundle);
        return result;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
