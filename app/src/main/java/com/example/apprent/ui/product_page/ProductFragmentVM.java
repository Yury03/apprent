package com.example.apprent.ui.product_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.apprent.R;

import java.util.List;

public class ProductFragmentVM extends ViewModel {


    @SuppressLint("StaticFieldLeak")//todo стоит ли передавать это в конструкторе?
    private Context context;
    private ImageSwitcher productImageSwitcher;


    public void setContext(Context context) {
        this.context = context;
    }

    public void setProductImageSwitcher(ImageSwitcher productImageSwitcher) {
        this.productImageSwitcher = productImageSwitcher;
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

    public void setImage(String path) {
//        RequestOptions requestOptions = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .placeholder(R.drawable.what)
//                .error(R.drawable.what);//todo
//        Glide.with(context)
//                .load(path)
//                .apply(requestOptions)
//                .into((ImageView) productImageSwitcher.getCurrentView());
        Glide.with(context)
                .load(path)
                .override(700,700)
//                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

                .into((ImageView) productImageSwitcher.getCurrentView());
    }//todo разделить на два метода и вынести глайд в домейн?


}