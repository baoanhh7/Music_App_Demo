package com.example.doan_music.activity.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.home.PlayListAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Playlists;

import java.util.ArrayList;
import java.util.List;

public class PlayListActivity extends AppCompatActivity {
    RecyclerView rcv_playlist;
    PlayListAdapter playListAdapter;
    Button btn_back;
    TextView txt_playlist;
    Playlists playlists;
    DbHelper dbHelper;
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        addControls();
        addEvents();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_playlist.setLayoutManager(linearLayoutManager);

    }

    private List<Playlists> getPlaylists() {
        List<Playlists> list = new ArrayList<>();

        DbHelper dbHelper = DatabaseManager.dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from Playlists", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] img = cursor.getBlob(2);

            playlists = new Playlists(id, name, img);
            list.add(playlists);
        }
        cursor.close();
        db.close();

        return list;
    }

    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        playListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                dbHelper = DatabaseManager.dbHelper(PlayListActivity.this);
                database = dbHelper.getReadableDatabase();

                Cursor cursor = database.rawQuery("select * from Playlists", null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String songPlayList = cursor.getString(1);

                    if (data.equals(songPlayList)) {
                        Intent intent = new Intent(PlayListActivity.this, SongsPlayListActivity.class);
                        intent.putExtra("PlayListID", id);

                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    private void addControls() {
        rcv_playlist = findViewById(R.id.rcv_playlist);
        playListAdapter = new PlayListAdapter(this, getPlaylists());
        rcv_playlist.setAdapter(playListAdapter);

        txt_playlist = findViewById(R.id.txt_playlist);
        btn_back = findViewById(R.id.btn_back);

        Intent intent = getIntent();
        txt_playlist.setText(intent.getStringExtra("c"));
    }
}