package com.example.doan_music.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.activity.admin.ablum.AblumActivity;
import com.example.doan_music.activity.admin.playlist.PlayListAdminActivity;

public class AdminActivity extends AppCompatActivity {
    Button btn_add_ablum, btn_add_artists, btn_add_playlists, btn_add_types, btn_add_song, btn_add_lovesongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_add_ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AblumActivity.class);
                startActivity(intent);
            }
        });

        btn_add_playlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, PlayListAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btn_add_artists = findViewById(R.id.btn_artists);
        btn_add_lovesongs = findViewById(R.id.btn_lovesongs);
        btn_add_ablum = findViewById(R.id.btn_ablum);
        btn_add_playlists = findViewById(R.id.btn_playlists);
        btn_add_types = findViewById(R.id.btn_types);
        btn_add_song = findViewById(R.id.btn_song);

    }
}