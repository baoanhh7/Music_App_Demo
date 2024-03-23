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
import com.example.doan_music.adapter.home.SongsPlayListAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Song;
import com.example.doan_music.music.PlayMusicActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongsPlayListActivity extends AppCompatActivity {
    RecyclerView rcv_songPlayList;
    SongsPlayListAdapter songsPlayListAdapter;
    ImageButton btn_back, btn_play;
    ImageView img_songPlayList;
    TextView txt_songPlayList;
    Intent intent = null;
    DbHelper dbHelper;
    SQLiteDatabase database = null;
    ArrayList<Integer> arr = new ArrayList<>();
    List<Song> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_songs_play_list);

        addControls();

        loadInfoPlayList();

        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        createData();
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
                        intent = new Intent(SongsPlayListActivity.this, PlayMusicActivity.class);
                        intent.putExtra("SongID", Id);
                        intent.putExtra("arrIDSongs", arr);
                        break;
                    }
                }
                cursor.close();
                startActivity(intent);
            }
        });

        songsPlayListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);

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

                        Intent intent = new Intent(SongsPlayListActivity.this, PlayMusicActivity.class);
                        intent.putExtra("SongID", id);
                        intent.putExtra("arrIDSongs", arr);
                        startActivity(intent);
                        break;
                    }
                }

            }
        });
    }

    private void loadInfoPlayList() {
        int albumID = getIntent().getIntExtra("PlayListID", -1);

        dbHelper = DatabaseManager.dbHelper(this);
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from PlayLists", null);
        while (cursor.moveToNext()) {
            Integer idAlbum = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            if (idAlbum.equals(albumID)) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                txt_songPlayList.setText(name);
                img_songPlayList.setImageBitmap(bitmap);

            }
        }
    }

    private void createData() {
        int PlayListID = getIntent().getIntExtra("PlayListID", -1);
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Songs", null);
        list.clear();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            Integer idPlayList = cursor.getInt(7);
            String ten = cursor.getString(2);
            byte[] img = cursor.getBlob(3);
            int fav = cursor.getInt(6);

            int view = cursor.getInt(8);

            if (idPlayList.equals(PlayListID)) {
                Song song = new Song(id, null, idPlayList, ten, img, view, fav);

                arr.add(id);
                list.add(song);
            }
        }
        songsPlayListAdapter.notifyDataSetChanged();
        cursor.close();

        // Sắp xếp danh sách theo thứ tự giảm dần của trường view
        // Comparator cho phép bạn xác định cách sắp xếp các đối tượng Song trong danh sách
        Collections.sort(list, new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                return Integer.compare(s2.getView(), s1.getView());
            }
        });
    }

    private void addControls() {
        rcv_songPlayList = findViewById(R.id.rcv_songPlayList);
        songsPlayListAdapter = new SongsPlayListAdapter(SongsPlayListActivity.this, list);
        rcv_songPlayList.setAdapter(songsPlayListAdapter);
        rcv_songPlayList.setLayoutManager(new LinearLayoutManager(this));

        btn_back = findViewById(R.id.btn_back);
        btn_play = findViewById(R.id.btn_play);
        img_songPlayList = findViewById(R.id.img_songPlayList);
        txt_songPlayList = findViewById(R.id.txt_songPlayList);

    }
}