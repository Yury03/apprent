package com.example.apprent.listClasses;

public class ProductItem {
	private String price;
	private String productName;
	private int imagePath;
	
	public ProductItem(String price, String productName, int imagePath) {
		this.imagePath = imagePath;
		this.productName = productName;
		this.price = price;
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
