package com.example.doan_music.model;

import java.io.Serializable;

public class Artists implements Serializable {
    private int ArtistID;
    private String ArtistName;
    private byte[] ArtistImage;

    public Artists() {
    }

    public Artists(int artistID, String artistName, byte[] artistImage) {
        ArtistID = artistID;
        ArtistName = artistName;
        ArtistImage = artistImage;
    }

    public int getArtistID() {
        return ArtistID;
    }

    public void setArtistID(int artistID) {
        ArtistID = artistID;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public byte[] getArtistImage() {
        return ArtistImage;
    }

    public void setArtistImage(byte[] artistImage) {
        ArtistImage = artistImage;
    }
}
