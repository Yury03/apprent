package com.example.apprent.ui.profile_page.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.apprent.ui.profile_page.AccountFragment;
import com.example.apprent.ui.profile_page.ProfileFragment;

public class ProfilePagerAdapter extends FragmentStateAdapter {

    public ProfilePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment result;
        result = new AccountFragment();
        return result;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
