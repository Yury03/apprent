package com.example.apprent.domain.models;

import java.io.Serializable;
import java.util.List;

public class ProductItem implements Serializable {
    private final String name;
    private final String description;
    private final String minPrice;
    private final String mainImagePath;

    public List<String> getImagesPath() {
        return imagesPath;
    }

    private List<String> imagesPath;
    private final String fullPath;


    public ProductItem(String imagePath, String name, String description, String minPrice, String fullPath) {
        this.name = name;
        this.description = description;
        this.minPrice = minPrice;
        this.fullPath = fullPath;
        this.mainImagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public String getMainImagePath() {
        return imagesPath.get(imagesPath.size() - 1);
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setImagesPath(List<String> imagePath) {
        this.imagesPath = imagePath;
        this.imagesPath.add(mainImagePath);
    }
}
