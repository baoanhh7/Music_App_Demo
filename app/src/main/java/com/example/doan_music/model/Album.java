package com.example.doan_music.model;

import java.io.Serializable;

public class Album implements Serializable {
    private Integer AlbumID;
    private String AlbumName;
    private byte[] AlbumImage;
    private Integer ArtistID;

    public Album() {
    }

    public Album(Integer albumID, String albumName, byte[] albumImage, Integer artistID) {
        AlbumID = albumID;
        AlbumName = albumName;
        AlbumImage = albumImage;
        ArtistID = artistID;
    }

    public Album(Integer albumID, String albumName) {
        AlbumID = albumID;
        AlbumName = albumName;
    }

    public Integer getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(Integer albumID) {
        AlbumID = albumID;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public byte[] getAlbumImage() {
        return AlbumImage;
    }

    public void setAlbumImage(byte[] albumImage) {
        AlbumImage = albumImage;
    }

    public Integer getArtistID() {
        return ArtistID;
    }

    public void setArtistID(Integer artistID) {
        ArtistID = artistID;
    }
}
