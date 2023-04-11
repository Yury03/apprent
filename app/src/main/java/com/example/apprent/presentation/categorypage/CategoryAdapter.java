package com.example.apprent.presentation.categorypage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.apprent.R;
import com.example.apprent.domain.models.CategoryItem;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import okhttp3.OkHttpClient;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryItem> categoryItemArrayList;
    private Context context;
    private final CategoryFragmentVM categoryFragmentVM;

    public CategoryAdapter(List<CategoryItem> categoryItemArrayList, Context context, CategoryFragmentVM categoryFragmentVM) {
        this.categoryItemArrayList = categoryItemArrayList;
        this.context = context;
        this.categoryFragmentVM = categoryFragmentVM;



    }
    private void goNext(CategoryItem categoryItem) {
        //todo доработать до перехода на фрагмент товара
        String category = categoryItem.getPath();
        categoryFragmentVM.getCategoryList(category);

    }
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Log.d("debug", "thread: " + Thread.currentThread().getName());
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.what);
        Glide.with(context)
                .load(categoryItemArrayList.get(position).getImagePath())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        boolean isCachedInMemory = requestOptions.isMemoryCacheable();
                        boolean isCachedOnDisk = requestOptions.isDiskCacheStrategySet();
                        return false;
                    }
                })
                .into(holder.imageView);
        holder.description.setText(categoryItemArrayList.get(position).getName());
        holder.itemView.setOnClickListener(v -> goNext(categoryItemArrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return categoryItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_image_view);
            description = itemView.findViewById(R.id.category_description);
        }
    }
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}


