package com.example.doan_music.admin.ablum;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.album.AlbumAdapter;
import com.example.doan_music.model.Ablum;

import java.util.ArrayList;


public class AblumActivity extends AppCompatActivity {

    RecyclerView reycAlbum;
    SQLiteDatabase database = null;
    Button btn_add_ablum;
    AlbumAdapter albumAdapter;
    ArrayList<Ablum> arrayAdapterAblum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablum);
        addControls();
        addEvents();
        xulaycapnhat();
        loadData();
    }

    private void loadData() {
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery("select * from Albums", null);
        arrayAdapterAblum.clear();
        while (cursor.moveToNext()) {
            Integer ma = Integer.parseInt(cursor.getString(0));
            String ten = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            //Integer artist = Integer.parseInt(cursor.getString(3));
            Ablum ablum = new Ablum(ma, ten, img, 0);
            arrayAdapterAblum.add(ablum);
        }
        cursor.close();
    }

    private void xulaycapnhat() {
    }

    private void addEvents() {
        btn_add_ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AblumActivity.this, AddAlbumActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btn_add_ablum = findViewById(R.id.btn_add_ablum);
        reycAlbum = findViewById(R.id.recAblum);
        arrayAdapterAblum = new ArrayList<>();
        albumAdapter = new AlbumAdapter(this, arrayAdapterAblum);
        reycAlbum.setAdapter(albumAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        reycAlbum.setLayoutManager(gridLayoutManager);
    }
}