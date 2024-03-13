package com.example.doan_music.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;

import java.io.IOException;

public class PlayMusicActivity extends AppCompatActivity {

    ImageButton btn_play, btn_pause, btn_back;
    SeekBar seekBar;
    TextView txt_time, txt_time_first;

    MediaPlayer myMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        addControls();
        myMusic = new MediaPlayer();
        //myMusic = MediaPlayer.create(this, R.raw.nhung_loi_hua_bo_quen);

        try {
            myMusic.setDataSource("https://musiclink666.000webhostapp.com/anhnhora-Vu.mp3");
            myMusic.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        myMusic.setLooping(true);
        myMusic.seekTo(0);

        myMusic.start();

        // tạo biến duration để lưu thời gian bài hát
        String duration = timeSeekbar(myMusic.getDuration());
        txt_time.setText(duration);

        addEvents();
    }

    public String timeSeekbar(int time) {
        String mTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        mTime = minutes + ":";
        if (seconds < 10) {
            mTime += "0";
        }
        mTime += seconds;
        return mTime;

    }

    private void addEvents() {
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMusic.isPlaying()) {
                    myMusic.pause();
                    btn_play.setImageResource(R.drawable.ic_play);

                } else {
                    myMusic.start();
                    btn_play.setImageResource(R.drawable.ic_pause);
                }
            }
        });

//        btn_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Khai báo Intent công khai để khởi động MyService_Music
//                Intent intent2 = new Intent(PlayMusicActivity.this, MyService_Music.class);
//                stopService(intent2);
//                // Nhấn vào Stop chuyển ảnh của play sang pause
//                btn_play.setImageResource(R.drawable.ic_play);
//                flag = true;
//            }
//        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set giới hạn Max cho thanh seekBar
        seekBar.setMax(myMusic.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    myMusic.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (myMusic != null) {
                    if (myMusic.isPlaying()) {
                        try {
                            final double current = myMusic.getCurrentPosition();
                            final String time = timeSeekbar((int) current);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt_time_first.setText(time);
                                    seekBar.setProgress((int) current);
                                }
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }).start();
    }

    private void addControls() {
        btn_play = findViewById(R.id.btn_play);
//        btn_pause = findViewById(R.id.btn_pause);
        btn_back = findViewById(R.id.btn_back);

        seekBar = findViewById(R.id.seekBar);

        txt_time = findViewById(R.id.txt_time);
        txt_time_first = findViewById(R.id.txt_time_first);
    }
}