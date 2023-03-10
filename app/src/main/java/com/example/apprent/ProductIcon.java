package com.example.apprent;

public class ProductIcon {
	private String priceLeft;
	private String productNameLeft;
	private int imagePathLeft;

	public ProductIcon(String price, String productName, int imagePath){
		this.imagePathLeft=imagePath;
		this.productNameLeft=productName;
		this.priceLeft=price;
	}

	public String getPrice() {
		return this.priceLeft;
	}
	
	public void setPrice(String price) {
		this.priceLeft = price;
	}
	
	public String getProductName() {
		return this.productNameLeft;
	}
	
	public void setProductName(String capital) {
		this.productNameLeft = productNameLeft;
	}
	
	public int getImagePath() {
		return this.imagePathLeft;
	}
	
	public void setImagePath(int imagePath) {
		this.imagePathLeft = imagePath;
	}
}
