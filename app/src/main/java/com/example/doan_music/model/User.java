package com.example.doan_music.model;

public class User {
    private int resourceImage;
    private String name;
    private Boolean header;

    public User(int resourceImage, String name, Boolean header) {
        this.resourceImage = resourceImage;
        this.name = name;
        this.header = header;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
