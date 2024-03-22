package com.example.doan_music.activity.admin.artist;

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
import com.example.doan_music.adapter.admin.ArtistAdminAdapter;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Artists;

import java.util.ArrayList;

public class ArtistActivity extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase database = null;
    DbHelper dbHelper;
    Button btn_add_artist, btn_back_artist_admin;
    ArtistAdminAdapter artistAdminAdapter;
    ArrayList<Artists> artistsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
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
        Cursor cursor = database.rawQuery("select * from Artists", null);
        artistsArrayList.clear();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            Artists artists = new Artists(ma, ten, img);
            artistsArrayList.add(artists);
        }
        artistAdminAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addEvents() {
        btn_add_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistActivity.this, AddArtistActivity.class);
                startActivity(intent);
            }
        });
        btn_back_artist_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArtistActivity.this, AdminActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        btn_add_artist = findViewById(R.id.btn_add_artist);
        btn_back_artist_admin = findViewById(R.id.btn_back_artist_admin);
        listView = findViewById(R.id.lvArtist_admin);
        artistsArrayList = new ArrayList<>();
        artistAdminAdapter = new ArtistAdminAdapter(ArtistActivity.this, artistsArrayList);
        listView.setAdapter(artistAdminAdapter);
    }
}