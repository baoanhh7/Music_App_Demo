package com.example.doan_music.model;

import java.io.Serializable;

public class SearchItem implements Serializable {
    private int image;
    private String name;
    private String describe;

    public SearchItem() {
    }

    public SearchItem(int image, String name, String describe) {
        this.image = image;
        this.name = name;
        this.describe = describe;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
