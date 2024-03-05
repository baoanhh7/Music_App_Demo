package com.example.doan_music.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.doan_music.R;

public class MyService_Music extends Service {
    MediaPlayer myMusic;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");


    }

    // Hàm khởi tạo đối tượng mà service quản lý
    @Override
    public void onCreate() {
        super.onCreate();
        myMusic = MediaPlayer.create(MyService_Music.this, R.raw.nhung_loi_hua_bo_quen);
        myMusic.setLooping(true);
    }

    // Hàm dùng để khởi động đối tượng mà service quản lý
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (myMusic.isPlaying())
            myMusic.pause();
        else
            myMusic.start();
        return super.onStartCommand(intent, flags, startId);
    }

    // Hàm dùng để dừng đối tượng mà service quản lý
    @Override
    public void onDestroy() {
        super.onDestroy();
        myMusic.stop();
    }
}