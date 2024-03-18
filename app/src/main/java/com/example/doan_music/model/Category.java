package com.example.doan_music.model;

import android.content.ClipData;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String name;
    private List<Playlists> list;
    private List<Song> songList;
    private List<? extends ClipData.Item> items;

    public Category(String name, List<Playlists> list, List<Song> songList) {
        this.name = name;
        this.list = list;
        this.songList = songList;
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
