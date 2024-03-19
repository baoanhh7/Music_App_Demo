package com.example.doan_music.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String name;
    private List<Playlists> list;
    private List<Song> songList;

    public Category(String name, List<Playlists> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Playlists> getList() {
        return list;
    }

    public void setList(List<Playlists> list) {
        this.list = list;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

}
