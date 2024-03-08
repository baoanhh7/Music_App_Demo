package com.example.doan_music.admin.playlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.admin.PlayListAdminAdapter;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Playlists;

import java.util.ArrayList;
import java.util.List;

public class PlayListAdminActivity extends AppCompatActivity {

    RecyclerView rcv_playlist_admin;
    DbHelper dbHelper = null;
    SQLiteDatabase database = null;
    Button btn_add;

    PlayListAdminAdapter playListAdminAdapter;
    List<Playlists> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_admin);

        addControl();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setData();
    }

    private void setData() {
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);

//        dbHelper = DatabaseManager.dbHelper(this);
//        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from Playlists", null);

        list.clear();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            Playlists playlists = new Playlists(id, name, image);
            list.add(playlists);
        }
        cursor.close();
    }

    private void addControl() {
        rcv_playlist_admin = findViewById(R.id.rcv_playlist_admin);

        list = new ArrayList<>();

        playListAdminAdapter = new PlayListAdminAdapter();
        playListAdminAdapter.setData(list);
        rcv_playlist_admin.setAdapter(playListAdminAdapter);

        btn_add = findViewById(R.id.btn_add);

    }
}