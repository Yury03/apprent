package com.example.apprent.presetation.cartpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.apprent.R;

import com.example.apprent.presetation.mainpage.ProductItem;

import java.util.List;
	
	public class AdapterCartList extends ArrayAdapter<ProductItem> {
		private LayoutInflater inflater;
		private int layout;
		private List<ProductItem> items;
		public AdapterCartList(Context context, int resource,
							   List<ProductItem> items) {
			super(context, resource, items);
			this.items = items;
			this.layout = resource;
			this.inflater = LayoutInflater.from(context);
		}
		public View getView(int position, View convertView,
							ViewGroup parent) {
			@SuppressLint("ViewHolder") View view=inflater.inflate(this.layout, parent, false);
			Button button1 = view.findViewById(R.id.go_to_product);
			ImageButton button2 = view.findViewById(R.id.go_to_product_2);
			TextView price = view.findViewById(R.id.priceText);
			ProductItem item = items.get(position);
			button2.setImageResource(item.getImagePath());
			button1.setText(item.getProductName());
			price.setText(item.getPrice());
			return view;
		}
	}
