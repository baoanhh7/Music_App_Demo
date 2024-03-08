package com.example.doan_music.model;

import java.io.Serializable;

public class Playlists implements Serializable {
    private int PlaylistID;
    private String PlaylistName;
    private byte[] PlaylistImage;

    public Playlists(int playlistID, String playlistName, byte[] playlistImage) {
        PlaylistID = playlistID;
        PlaylistName = playlistName;
        PlaylistImage = playlistImage;
    }

    public int getPlaylistID() {
        return PlaylistID;
    }

    public void setPlaylistID(int playlistID) {
        PlaylistID = playlistID;
    }

    public String getPlaylistName() {
        return PlaylistName;
    }

    public void setPlaylistName(String playlistName) {
        PlaylistName = playlistName;
    }

    public byte[] getPlaylistImage() {
        return PlaylistImage;
    }

    public void setPlaylistImage(byte[] playlistImage) {
        PlaylistImage = playlistImage;
    }
}
