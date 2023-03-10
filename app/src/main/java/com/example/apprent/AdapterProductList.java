package com.example.apprent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterProductList  extends RecyclerView.Adapter<AdapterProductList.ViewHolder> {
	private final LayoutInflater inflater;
	private final List<ProductIcon> products;
	AdapterProductList(Context context, List<ProductIcon> products){
		this.products = products;
		this.inflater = LayoutInflater.from(context);
		
	}
	
	
	public AdapterProductList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.icon_product, parent, false);
		return new ViewHolder(view);
	}
	@Override
	public void onBindViewHolder(AdapterProductList.ViewHolder holder, int position) {
		ProductIcon product = products.get(position);
		holder.imageButton.setImageResource(product.getImagePath());
		holder.button.setText(product.getProductName());
		holder.price.setText(product.getPrice());
	}
	
	@Override
	public int getItemCount() {
		return products.size();
	}
	public static class ViewHolder extends RecyclerView.ViewHolder {
		final ImageButton imageButton;
		final Button button;
		final TextView price;
		
		ViewHolder(View view) {
			super(view);
			imageButton=view.findViewById(R.id.go_to_product_2);
			button=view.findViewById(R.id.go_to_product);
			price=view.findViewById(R.id.priceText);
		}
	}
}