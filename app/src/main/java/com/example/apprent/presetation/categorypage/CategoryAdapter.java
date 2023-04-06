package com.example.apprent.presetation.categorypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apprent.R;
import com.example.apprent.domain.models.CategoryItem;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryItem> categoryItemArrayList;
    private Context context;

    public CategoryAdapter(List<CategoryItem> categoryItemArrayList, Context context) {
        this.categoryItemArrayList = categoryItemArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        // loading the images from the position
        Glide.with(holder.itemView.getContext()).load(categoryItemArrayList.get(position).getImagePath()).into(holder.imageView);
        holder.description.setText(categoryItemArrayList.get(position).getDescription());
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
            description=itemView.findViewById(R.id.category_description);
        }
    }
}