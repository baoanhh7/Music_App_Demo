package com.example.doan_music.activity.home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.home.SongAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Song;
import com.example.doan_music.music.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;

public class SongsAlbumActivity extends AppCompatActivity {

    RecyclerView rcv_songlist;
    SongAdapter songAdapter;
    ImageButton btn_back, btn_play;
    ImageView img_songlist;
    TextView txt_songlist, txt_album_view;
    Intent intent = null;
    ArrayList<Integer> arr = new ArrayList<>();
    SQLiteDatabase database = null;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_album);
        addControls();

        loadInfoAlbum();

        addEvents();
    }

    private void loadInfoAlbum() {
        int albumID = getIntent().getIntExtra("albumID", -1);

        dbHelper = DatabaseManager.dbHelper(this);
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from Albums", null);
        while (cursor.moveToNext()) {
            Integer idAlbum = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            int view = cursor.getInt(4);

            if (idAlbum.equals(albumID)) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                txt_songlist.setText(name);
                img_songlist.setImageBitmap(bitmap);
                txt_album_view.setText(view + "");

            }
        }
    }

    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idSong = arr.get(0);
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    Integer Id = cursor.getInt(0);

                    if (idSong.equals(Id)) {
                        intent = new Intent(SongsAlbumActivity.this, PlayMusicActivity.class);
                        intent.putExtra("SongID", Id);
                        intent.putExtra("arrIDSongs", arr);
                        break;
                    }
                }
                cursor.close();
                startActivity(intent);
            }
        });

        songAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                dbHelper = DatabaseManager.dbHelper(SongsAlbumActivity.this);
                database = dbHelper.getReadableDatabase();

                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String songName = cursor.getString(2);
                    int view = cursor.getInt(8);

                    if (data.equals(songName)) {

                        view++;
                        ContentValues values = new ContentValues();
                        values.put("View", view);
                        database.update("Songs", values, "SongID=?", new String[]{String.valueOf(id)});

                        Intent intent = new Intent(SongsAlbumActivity.this, PlayMusicActivity.class);
                        intent.putExtra("SongID", id);
                        intent.putExtra("arrIDSongs", arr);

                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    private List<Song> getList() {
        List<Song> list = new ArrayList<>();

        int albumID = getIntent().getIntExtra("albumID", -1);
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Songs", null);
        list.clear();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            Integer IDalbum = cursor.getInt(1);
            String ten = cursor.getString(2);
            byte[] img = cursor.getBlob(3);
            int fav = cursor.getInt(6);

            int view = cursor.getInt(8);

            if (IDalbum.equals(albumID)) {
                Song song = new Song(id, IDalbum, null, ten, img, view, fav);

                arr.add(id);
                list.add(song);
            }
        }
        cursor.close();

        return list;
    }

    private void addControls() {
        rcv_songlist = findViewById(R.id.rcv_songlist);

        songAdapter = new SongAdapter(this, getList());
        rcv_songlist.setAdapter(songAdapter);

        rcv_songlist.setLayoutManager(new LinearLayoutManager(this));

        img_songlist = findViewById(R.id.img_songlist);
        txt_songlist = findViewById(R.id.txt_songlist);
        txt_album_view = findViewById(R.id.txt_album_view);

        btn_back = findViewById(R.id.btn_back);
        btn_play = findViewById(R.id.btn_play);
    }
}