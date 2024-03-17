package com.example.doan_music.activity.library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.thuvien.ThuVienAlbumAdapter;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.ThuVien;
import com.example.doan_music.music.PlayMusicActivity;

import java.util.ArrayList;

public class PlaylistUserLoveActivity extends AppCompatActivity {
    ImageButton btnback, btnplay;
    RecyclerView rcv;
    ThuVienAlbumAdapter thuVienAlbumAdapter;
    ArrayList<ThuVien> arr;
    ArrayList<Integer> arr1 = new ArrayList<>();
    SQLiteDatabase database = null;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_user_love);
        addControls();
        loadData();
        addEvents();
    }

    private void addEvents() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer idSong = arr1.get(0);
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    Integer Id = cursor.getInt(0);
                    String ten = cursor.getString(2);
                    if (idSong.equals(Id)) {
                        intent = new Intent(PlaylistUserLoveActivity.this, PlayMusicActivity.class);
                        intent.putExtra("SongID", Id);
                        intent.putExtra("arrIDSongs", arr1);
                        break;
                    }
                }
                cursor.close();
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        Integer IDPlaylist = getIntent().getIntExtra("MaPlaylist", -1);
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Playlist_User_Song", null);
        arr.clear();
        while (cursor.moveToNext()) {
            Integer idplaylist = cursor.getInt(1);
            Integer idsong = cursor.getInt(3);
            if (IDPlaylist.equals(idplaylist)) {
                Cursor cursor1 = database.rawQuery("select * from Songs", null);

                while (cursor1.moveToNext()) {
                    Integer id = cursor1.getInt(0);
                    String ten = cursor1.getString(2);
                    byte[] img = cursor1.getBlob(3);
                    if (idsong.equals(id)) {
                        ThuVien thuVien = new ThuVien(img, ten);
                        arr1.add(id);
                        arr.add(thuVien);
                    }
                }
                cursor1.close();
            }
        }
        thuVienAlbumAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addControls() {
        btnback = findViewById(R.id.btn_back_playlistuser);
        btnplay = findViewById(R.id.btn_playplaylistuser);
        rcv = findViewById(R.id.rcv_playlistuser);
        arr = new ArrayList<>();
        thuVienAlbumAdapter = new ThuVienAlbumAdapter(PlaylistUserLoveActivity.this, arr);
        thuVienAlbumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {

                    Integer Id = cursor.getInt(0);
                    String ten = cursor.getString(2);
                    if (data.equals(ten)) {
                        intent = new Intent(PlaylistUserLoveActivity.this, PlayMusicActivity.class);
                        intent.putExtra("SongID", Id);
                        intent.putExtra("arrIDSongs", arr1);
                        break;
                    }
                }
                cursor.close();
                startActivity(intent);
            }
        });
        rcv.setAdapter(thuVienAlbumAdapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayout);
    }
}