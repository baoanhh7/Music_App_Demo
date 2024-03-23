package com.example.doan_music.activity.admin.album;

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
import com.example.doan_music.adapter.admin.AlbumAdminAdapter;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Album;

import java.util.ArrayList;


public class AlbumActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase database = null;
    DbHelper dbHelper;
    Button btn_add_ablum, btn_back_add_ablum_admin;
    AlbumAdminAdapter albumAdapter;
    ArrayList<Album> arrayAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablum);
        addControls();
        addEvents();
    }


    private void loadData() {
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Albums", null);
        arrayAlbum.clear();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            int maartist = cursor.getInt(3);
            Album album = new Album(ma, ten, img, maartist);
            arrayAlbum.add(album);
        }
        albumAdapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    private void addEvents() {
        btn_add_ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumActivity.this, AddAlbumActivity.class);
                startActivity(intent);
            }
        });
        btn_back_add_ablum_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlbumActivity.this, AdminActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        btn_add_ablum = findViewById(R.id.btn_add_ablum);
        btn_back_add_ablum_admin = findViewById(R.id.btn_back_add_ablum_admin);
        listView = findViewById(R.id.lvAblum_admin);
        arrayAlbum = new ArrayList<>();
        albumAdapter = new AlbumAdminAdapter(AlbumActivity.this, arrayAlbum);
        listView.setAdapter(albumAdapter);
    }
}