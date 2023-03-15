package com.example.apprent.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.listClasses.ProductItem;
import com.example.apprent.R;


public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ViewHolder> {
	private final LayoutInflater inflater;
	private final List<ProductItem> products;
	public AdapterProductList(Context context, List<ProductItem> products) {
		this.products = products;
		this.inflater = LayoutInflater.from(context);
	}
	
	
	public AdapterProductList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.product_item, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(AdapterProductList.ViewHolder holder, int position) {
		ProductItem product = products.get(position);
		holder.imageButton.setImageResource(product.getImagePath());
		holder.button.setText(product.getProductName());
		holder.price.setText(product.getPrice());
		
	}
	
	@Override
	public int getItemCount() {
		return products.size();
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		final ImageButton imageButton;
		final Button button;
		final TextView price;
		
		ViewHolder(View view) {
			super(view);
			imageButton = view.findViewById(R.id.go_to_product_2);
			button = view.findViewById(R.id.go_to_product);
			price = view.findViewById(R.id.priceText);
			imageButton.setOnClickListener(this);
			button.setOnClickListener(this);
		}
		@Override
		public void onClick(View view) {
			Log.i("MyApp", "Calling a recyclerView element listener");
		}
	}
}
