package com.example.apprent.ui.catalog.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.apprent.R;
import com.example.apprent.domain.models.CategoryItem;
import com.example.apprent.ui.catalog.CategoryFragmentVM;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<CategoryItem> itemArrayList;
    private final Context context;
    private final CategoryFragmentVM categoryFragmentVM;


    public CategoryAdapter(List<CategoryItem> itemArrayList, Context context, CategoryFragmentVM categoryFragmentVM) {
        this.itemArrayList = itemArrayList;
        this.context = context;
        this.categoryFragmentVM = categoryFragmentVM;

    }

    private void goNext(CategoryItem categoryItem) {
        String category = categoryItem.getPath();
        Log.i("MyApp", "String category = " + category);
        categoryFragmentVM.setTitle(categoryItem.getName());
        if (categoryItem.getHasChild()) {
            categoryFragmentVM.getCategoryList(categoryFragmentVM.getFragmentPath() + category);
        } else {
            categoryFragmentVM.getProductList(categoryFragmentVM.getFragmentPath() + category);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Transformation<Bitmap> transformation = new CenterCrop();
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transform(transformation)
                .placeholder(R.drawable.what);
        Glide.with(context)
                .load((itemArrayList.get(position)).getImagePath())
                .apply(requestOptions)
                .override(holder.categoryImage.getWidth(), holder.categoryImage.getHeight())
                .centerCrop()
                .listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.categoryImage);
        holder.categoryName.setText((itemArrayList.get(position)).getName());
        holder.itemView.setOnClickListener(v -> goNext(itemArrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image_view);
            categoryName = itemView.findViewById(R.id.category_description);
        }
    }
//    private boolean isConnected() {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//    }
}


