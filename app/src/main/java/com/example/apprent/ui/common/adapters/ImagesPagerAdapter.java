package com.example.apprent.ui.common.adapters;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.apprent.ui.common.ImageFragmentForViewPager;

import java.util.List;

public class ImagesPagerAdapter extends FragmentStateAdapter {

    private final List<String> imagesList;

    public ImagesPagerAdapter(@NonNull Fragment fragment, List<String> imagesList) {
        super(fragment);
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("link", imagesList.get(position));
        return new ImageFragmentForViewPager(bundle);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
