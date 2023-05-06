package com.example.apprent.ui.product_page.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.apprent.ui.product_page.ProductImageFragment;

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
        return new ProductImageFragment(bundle);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
