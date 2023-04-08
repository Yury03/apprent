package com.example.apprent.domain.models;

public class CategoryItem {
    private String imagePath;
    private String description;

    public CategoryItem(String imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getDescription() {
        return this.description;
    }

}
