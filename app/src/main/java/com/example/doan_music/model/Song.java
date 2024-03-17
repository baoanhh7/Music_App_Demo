package com.example.doan_music.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int SongID;
    private int AlbumID;
    private int PlaylistID;
    private String SongName;
    private byte[] SongImage;
    private int ArtistID;
    private String LinkSong;
    private int TypeID;
    private int isFavorite;

    public Song() {
    }

    public Song(int songID, int albumID, String songName, byte[] songImage) {
        SongID = songID;
        AlbumID = albumID;
        SongName = songName;
        SongImage = songImage;
    }

    public Song(int songID, String songName, byte[] songImage, String linkSong, int isFavorite) {
        SongID = songID;
        SongName = songName;
        SongImage = songImage;
        LinkSong = linkSong;
        this.isFavorite = isFavorite;
    }

    public Song(int songID, String songName, int artistID, byte[] songImage, String linkSong, int isFavorite) {
        SongID = songID;
        SongName = songName;
        SongImage = songImage;
        LinkSong = linkSong;
        ArtistID = artistID;
        this.isFavorite = isFavorite;
    }


    public int getSongID() {
        return SongID;
    }

    public void setSongID(int songID) {
        SongID = songID;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }

    public int getPlaylistID() {
        return PlaylistID;
    }

    public void setPlaylistID(int playlistID) {
        PlaylistID = playlistID;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public byte[] getSongImage() {
        return SongImage;
    }

    public void setSongImage(byte[] songImage) {
        SongImage = songImage;
    }

    public int getArtistID() {
        return ArtistID;
    }

    public void setArtistID(int artistID) {
        ArtistID = artistID;
    }

    public String getLinkSong() {
        return LinkSong;
    }

    public void setLinkSong(String linkSong) {
        LinkSong = linkSong;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int typeID) {
        TypeID = typeID;
    }

    public int getisFavorite() {
        return isFavorite;
    }

    public void setisFavorite(int stateFavorite) {
        isFavorite = stateFavorite;
    }
}
