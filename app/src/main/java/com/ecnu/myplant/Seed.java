package com.ecnu.myplant;

/**
 * Created by Type1551 on 2018/6/7.
 */
public class Seed {
    private String name;

    private int imageId;

    public Seed(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

}
