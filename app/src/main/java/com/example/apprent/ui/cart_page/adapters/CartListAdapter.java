package com.example.apprent.ui.cart_page.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apprent.R;
import com.example.apprent.data.cart_database.entity.CartProductEntity;
import com.example.apprent.ui.cart_page.CartFragmentVM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private List<CartProductEntity> cartProducts = new ArrayList<>();
    private final CartFragmentVM cartFragmentVM;


    private Context context;

    public CartListAdapter(CartFragmentVM cartFragmentVM, Context context) {
        this.cartFragmentVM = cartFragmentVM;
        this.context = context;
    }

    public void setCartProducts(List<CartProductEntity> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        CartProductEntity currentProduct = cartProducts.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        String dateString = formatter.format(currentProduct.getDate());
        holder.productName.setText(context.getString(R.string.name_cart_item) + currentProduct.getName());
        holder.productDate.setText(context.getString(R.string.date_cart_item) + dateString);
        holder.productPeriod.setText(context.getString(R.string.period_cart_item) + String.valueOf(currentProduct.getPeriod()));
        holder.productQuantity.setText(context.getString(R.string.count_cart_item) + String.valueOf(currentProduct.getQuantity()));
        holder.productPrice.setText(String.valueOf(currentProduct.getFinalPrice()) + " " + context.getString(R.string.currency));
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.expand_animation);

        Glide.with(context)
                .load(currentProduct.getImageUri())
                .override(500, 500)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.skeleton)//todo присоединить либу для скелетонов
                .into(holder.productImage);
        holder.productDelete.setOnClickListener(v -> cartFragmentVM.removeFromCart(position));
        holder.infoButton.setOnClickListener(v -> {
            CartProductEntity item = cartProducts.get(position);
//            transitionItemToFragment(holder.itemView);
            cartFragmentVM.openFullItem(item, position);
        });


    }

    //    private void transitionItemToFragment(View itemView) {
//        Transition transition = new ChangeBounds();
//        transition.setDuration(500);
//        TransitionManager.beginDelayedTransition((ViewGroup) itemView.getParent(), transition);
//        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        itemView.setLayoutParams(layoutParams);
//    }
    private void transitionItemToFragment(final View itemView) {
        // Анимация исчезновения
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(500);
        itemView.setAnimation(alphaAnimation);
        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        TransitionManager.beginDelayedTransition((ViewGroup) itemView.getParent(), transition);
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        itemView.setLayoutParams(layoutParams);
        itemView.startAnimation(alphaAnimation);
        itemView.setVisibility(View.INVISIBLE);

    }


    @Override
    public int getItemCount() {
        return cartProducts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productName;
        private final TextView productDate;
        private final TextView productPeriod;
        private final TextView productQuantity;
        private final ImageView productImage;
        private final TextView productPrice;
        private final ImageButton infoButton;
        private final ImageButton phoneButton;
        public final ImageButton productDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.name_cart_item);
            productDate = itemView.findViewById(R.id.date_cart_item);
            productPeriod = itemView.findViewById(R.id.period_cart_item);
            productQuantity = itemView.findViewById(R.id.count_cart_item);
            productImage = itemView.findViewById(R.id.image_cart_item);
            productPrice = itemView.findViewById(R.id.price_cart_item);
            infoButton = itemView.findViewById(R.id.info_button);
            phoneButton = itemView.findViewById(R.id.phone_button);
            productDelete = itemView.findViewById(R.id.delete_button);
        }
    }

}

