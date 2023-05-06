package com.example.apprent.ui.product_page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apprent.R;


public class ProductImageFragment extends Fragment {
    private String link;

    public ProductImageFragment(Bundle bundle) {
        super();
        if (bundle != null) {
            link = bundle.getString("link");
        } else {
            Log.e("MyApp", "ProductImageFragment: arguments is null");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.product_image_fragment);
        setImage(imageView);
    }

    public void setImage(ImageView imageView) {
        Glide.with(getContext())
                .load(link)
                .override(700, 700)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.skeleton)//todo присоединить либу для скелетонов
                .into(imageView);
    }//todo вынести во viewModel?

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_image, container, false);
    }
}