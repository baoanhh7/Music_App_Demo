package com.example.doan_music.model;

import java.io.Serializable;

public class Spotifyitem implements Serializable {
    private String Name;
    private String info_1;
    private String info_2;

    public Spotifyitem() {
    }

    public Spotifyitem(String name, String info_1, String info_2) {
        Name = name;
        this.info_1 = info_1;
        this.info_2 = info_2;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInfo_1() {
        return info_1;
    }

    public void setInfo_1(String info_1) {
        this.info_1 = info_1;
    }

    public String getInfo_2() {
        return info_2;
    }

    public void setInfo_2(String info_2) {
        this.info_2 = info_2;
    }
}
