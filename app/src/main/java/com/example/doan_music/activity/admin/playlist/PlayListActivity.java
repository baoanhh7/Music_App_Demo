package com.example.doan_music.activity.admin.playlist;

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
import com.example.doan_music.adapter.admin.PlayListAdminAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Playlists;

import java.util.ArrayList;
import java.util.List;

public class PlayListActivity extends AppCompatActivity {

    static SQLiteDatabase database = null;
    ListView lv_playlist_admin;
    DbHelper dbHelper;
    Button btn_add, btn_back;

    PlayListAdminAdapter playListAdminAdapter;
    List<Playlists> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_admin);

        addControl();
        addEvents();

    }

    private void addEvents() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayListActivity.this, AddPlayListActivity.class);
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayListActivity.this, AdminActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        createData();
    }

    private void createData() {
        dbHelper = DatabaseManager.dbHelper(this);
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from Playlists", null);

        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
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
        btn_back = findViewById(R.id.btn_back);
        btn_add = findViewById(R.id.btn_add);

        list = new ArrayList<>();

        playListAdminAdapter = new PlayListAdminAdapter(this, list);
        lv_playlist_admin.setAdapter(playListAdminAdapter);

    }
}