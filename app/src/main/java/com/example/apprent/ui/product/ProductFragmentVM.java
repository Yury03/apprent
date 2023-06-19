package com.example.apprent.ui.product;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ProductFragmentVM extends ViewModel {
    @SuppressLint("StaticFieldLeak")//todo стоит ли передавать это в конструкторе?
    private Context context;
    public void setContext(Context context) {
        this.context = context;
    }

    public void loadImages(List<String> imagePaths) {
        for (int i = 1; i < imagePaths.size(); i++) {
            Glide.with(context)
                    .load(imagePaths.get(i))
                    .override(700,700)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .preload();
        }
    }
}