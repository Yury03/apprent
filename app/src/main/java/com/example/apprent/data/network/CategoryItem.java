package com.example.apprent.data.network;

public class CategoryItem {
    private String imageUrl;

    public CategoryItem() {
        // Пустой конструктор, необходимый для Firebase
    }

    public CategoryItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
