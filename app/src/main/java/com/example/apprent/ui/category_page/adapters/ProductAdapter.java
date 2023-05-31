package com.example.apprent.ui.category_page.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.apprent.R;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.ui.category_page.CategoryFragmentVM;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Serializable {
    private final List<ProductItem> itemArrayList;
    private final Context context;
    private final CategoryFragmentVM categoryFragmentVM;
    private final FragmentManager fragmentManager;

    public ProductAdapter(List<ProductItem> itemArrayList, Context context, CategoryFragmentVM categoryFragmentVM, FragmentManager fragmentManager) {
        this.itemArrayList = itemArrayList;
        this.context = context;
        this.categoryFragmentVM = categoryFragmentVM;
        this.fragmentManager = fragmentManager;
    }

    private void goNext(ProductItem productItem) {
        categoryFragmentVM.goToProductFragment(productItem);
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.what);
        Glide.with(context)
                .load((itemArrayList.get(position)).getMainImagePath())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource
                            dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.productImage);
        holder.productName.setText((itemArrayList.get(position)).getName());
        holder.productPrice.setText(((itemArrayList.get(position)).getMinPrice() + " руб/сутки"));//todo
        holder.itemView.setOnClickListener(v -> goNext(itemArrayList.get(position)));
        holder.productReservation.setOnClickListener(v -> categoryFragmentVM.reservation(fragmentManager));
        holder.productSelectDate.setOnClickListener(v -> categoryFragmentVM.selectDate(itemArrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPriceFrom;
        TextView productPrice;
        ImageButton productSelectDate;
        ImageButton productReservation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPriceFrom = itemView.findViewById(R.id.price_from);
            productPrice = itemView.findViewById(R.id.product_price);
            productSelectDate = itemView.findViewById(R.id.change_date);
            productReservation = itemView.findViewById(R.id.reservation);
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
