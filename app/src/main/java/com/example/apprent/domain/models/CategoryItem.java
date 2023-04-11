package com.example.apprent.domain.models;

public class CategoryItem {
    private String imagePath;
    private String name;
    private String path;//example: subcategory_1/

    public CategoryItem(String imagePath) {
        this.imagePath = imagePath;
    }

    public CategoryItem(String imagePath, String name, String path) {
        this.imagePath = imagePath;
        this.name = name;
        this.path = path;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPath() {
        return this.path;
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
