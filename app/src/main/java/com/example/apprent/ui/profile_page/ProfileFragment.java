package com.example.apprent.ui.profile_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apprent.R;
import com.example.apprent.ui.main_activity.MainActivityVM;
import com.example.apprent.ui.profile_page.adapters.ProfilePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ProfileFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Button logoutButton = view.findViewById(R.id.log_out_button);
//        logoutButton.setOnClickListener(v -> {
//            MainActivityVM mainActivityVM = (MainActivityVM) getArguments().getSerializable("MainActivityVM");
//            mainActivityVM.getNavController().navigate(R.id.authenticationFragment, getArguments());
//            mainActivityVM.getSharedPreferences().edit().putBoolean(getResources().getString(R.string.saved_log_in_key), false).apply();
//        });
        TabLayout tabLayout = view.findViewById(R.id.profile_tab_layout);
        ViewPager2 viewPager2 = view.findViewById(R.id.profile_view_pager);
        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(this);
        viewPager2.setAdapter(profilePagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Аккаунт");
            }
        }).attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}