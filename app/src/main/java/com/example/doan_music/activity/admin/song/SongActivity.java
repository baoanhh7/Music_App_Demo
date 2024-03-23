package com.example.doan_music.activity.admin.song;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.activity.admin.AdminActivity;
import com.example.doan_music.adapter.admin.SongAdminAdapter;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Song;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase database = null;
    DbHelper dbHelper;
    Button btn_add_song, btn_back_add_song_admin;
    SongAdminAdapter songAdminAdapter;
    ArrayList<Song> songArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Songs", null);
        songArrayList.clear();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(2);
            int maAlbum = cursor.getInt(1);
            byte[] img = cursor.getBlob(3);
            int maartist = cursor.getInt(4);
            int maPlaylist = cursor.getInt(7);

            String linknhac = cursor.getString(5);
            Song song = new Song(ma, maAlbum, ten, img, maartist, linknhac, maPlaylist);
            songArrayList.add(song);
        }
        songAdminAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addEvents() {
        btn_add_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SongActivity.this, AddSongActivity.class);
                startActivity(intent);
            }
        });
        btn_back_add_song_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SongActivity.this, AdminActivity.class));
            }
        });
    }

    private void addControls() {
        btn_add_song = findViewById(R.id.btn_add_song);
        btn_back_add_song_admin = findViewById(R.id.btn_back_song_admin);
        listView = findViewById(R.id.lvSong_admin);
        songArrayList = new ArrayList<>();
        songAdminAdapter = new SongAdminAdapter(SongActivity.this, songArrayList);
        listView.setAdapter(songAdminAdapter);
    }
}