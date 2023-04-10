package com.example.apprent.domain.models;

public class CategoryItem {
    private String imagePath;
    private String name;

    public CategoryItem(String imagePath) {
        this.imagePath = imagePath;
    }

    public CategoryItem(String imagePath, String name) {
        this.imagePath = imagePath;
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getImagePath() {
        return this.imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
