package com.example.doan_music.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String name;
    private List<User> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public Category(String name, List<User> list) {
        this.name = name;
        this.list = list;
    }
}
