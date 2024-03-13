package com.example.doan_music.model;

public class LrcLine {
    private float time;
    private String lyrics;

    public LrcLine(float time, String lyrics) {
        this.time = time;
        this.lyrics = lyrics;
    }

    public float getTime() {
        return time;
    }

    public String getLyrics() {
        return lyrics;
    }
}
