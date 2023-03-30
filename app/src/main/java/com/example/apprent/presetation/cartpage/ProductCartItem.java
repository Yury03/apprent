package com.example.apprent.presetation.cartpage;

public class ProductCartItem {
	private String price, productName, from, to, dayCounter;
	private int imagePath;
	
	public ProductCartItem(String price, String productName, int imagePath) {
		this.price = price;
		this.productName = productName;
		this.imagePath = imagePath;
	}
	
	public String getPrice() {
		return this.price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String capital) {
		this.productName = productName;
	}
	
	public int getImagePath() {
		return this.imagePath;
	}
	
	public void setImagePath(int imagePath) {
		this.imagePath = imagePath;
	}
}
