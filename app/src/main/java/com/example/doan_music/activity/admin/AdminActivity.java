package com.example.doan_music.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.activity.admin.album.AlbumActivity;
import com.example.doan_music.activity.admin.artist.ArtistActivity;
import com.example.doan_music.activity.admin.playlist.PlayListActivity;
import com.example.doan_music.activity.admin.song.SongActivity;
import com.example.doan_music.loginPackage.Login_userActivity;

public class AdminActivity extends AppCompatActivity {
    Button btn_add_album, btn_add_artists, btn_add_playlists, btn_add_types, btn_add_song, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_add_artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ArtistActivity.class);
                startActivity(intent);
            }
        });
        btn_add_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AlbumActivity.class);
                startActivity(intent);
            }
        });
        btn_add_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, SongActivity.class);
                startActivity(intent);
            }
        });

        btn_add_playlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, PlayListActivity.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, Login_userActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        btn_add_artists = findViewById(R.id.btn_artists);
        btn_add_album = findViewById(R.id.btn_ablum);
        btn_add_playlists = findViewById(R.id.btn_playlists);
        btn_add_types = findViewById(R.id.btn_types);
        btn_add_song = findViewById(R.id.btn_song);

        btn_logout = findViewById(R.id.btn_logout);

    }
}