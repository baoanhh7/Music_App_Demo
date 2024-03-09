package com.example.doan_music.admin.playlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.adapter.admin.PlayListAdminAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Playlists;

import java.util.ArrayList;
import java.util.List;

public class PlayListAdminActivity extends AppCompatActivity {

    ListView lv_playlist_admin;
    DbHelper dbHelper;
    SQLiteDatabase database = null;
    Button btn_add;

    PlayListAdminAdapter playListAdminAdapter;
    List<Playlists> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_admin);

        addControl();
        createData();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void createData() {
//        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);

        dbHelper = DatabaseManager.dbHelper(this);
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from Playlists", null);

        list.clear();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            Playlists playlists = new Playlists(id, name, image);
            list.add(playlists);
        }
        playListAdminAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addControl() {
        lv_playlist_admin = findViewById(R.id.lv_playlist_admin);

        list = new ArrayList<>();

        playListAdminAdapter = new PlayListAdminAdapter(this, list);
        lv_playlist_admin.setAdapter(playListAdminAdapter);

        btn_add = findViewById(R.id.btn_add);

    }
}