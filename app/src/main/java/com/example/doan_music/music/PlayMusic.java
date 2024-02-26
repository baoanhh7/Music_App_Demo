package com.example.doan_music.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.doan_music.MainActivity;
import com.example.doan_music.R;

public class PlayMusic extends AppCompatActivity {

    ImageButton btn_play, btn_pause, btn_back;
    Boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        btn_play = findViewById(R.id.btn_play);
//        btn_pause = findViewById(R.id.btn_pause);
        btn_back = findViewById(R.id.btn_back);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Khai báo Intent công khai để khởi động Service
                Intent intent1 = new Intent(PlayMusic.this, MyService_Music.class);
                startService(intent1);

                if (flag == true) {
                    // Chuyển hình ảnh play sang pause
                    btn_play.setImageResource(R.drawable.ic_pause);
                    flag = false;
                } else {
                    // Và ngược lại
                    btn_play.setImageResource(R.drawable.ic_play);
                    flag = true;
                }
            }
        });

//        btn_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Khai báo Intent công khai để khởi động Service
//                Intent intent2 = new Intent(PlayMusic.this, MyService_Music.class);
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
    }
}