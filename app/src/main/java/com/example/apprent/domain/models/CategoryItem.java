package com.example.apprent.domain.models;

public class CategoryItem {
    private final String imagePath;
    private final String name;
    private final String path;
    private final Boolean hasChild;

    public CategoryItem(String imagePath, String name, String path, Boolean hasChild) {
        this.imagePath = imagePath;
        this.name = name;
        this.path = path;
        this.hasChild=hasChild;
    }

    public String getPath() {
        return this.path;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getHasChild() {
        return hasChild;
    }
}
