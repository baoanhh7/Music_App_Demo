package com.example.doan_music.activity.library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.thuvien.ThuVienAlbumAdapter;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.ThuVien;
import com.example.doan_music.music.PlayMusicActivity;

import java.util.ArrayList;

public class ArtistSongActivity extends AppCompatActivity implements OnItemClickListener {
    ImageButton btnback, btnplay;
    ImageView imgHinh;
    RecyclerView rcv;
    ThuVienAlbumAdapter thuVienAlbumAdapter;
    ArrayList<ThuVien> arr;
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablum_song);
        addControls();
        loadData();
        loadImgArtist();
        addEvents();
    }

    private void addEvents() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadImgArtist() {
        Integer IDArtist = getIntent().getIntExtra("MaArtist", -1);
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select *  from Artists", null);
//                "from Artists " +
//                "JOIN Songs ON Artists.ArtistID =Songs.ArtistID " +
//                "WHERE Songs.ArtistID = ? ", new String[]{String.valueOf(IDArtist)});
        while (cursor.moveToNext()) {
            Integer idArtist = cursor.getInt(0);
            byte[] img = cursor.getBlob(2);
            if (idArtist.equals(IDArtist)) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                imgHinh.setImageBitmap(bitmap);
            }
        }
        cursor.close();
    }

    private void loadData() {
        Integer IDArtist = getIntent().getIntExtra("MaArtist", -1);
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Songs", null);
        arr.clear();
        while (cursor.moveToNext()) {
            Integer IdArtist = cursor.getInt(4);
            String ten = cursor.getString(2);
            byte[] img = cursor.getBlob(3);
            if (IdArtist.equals(IDArtist)) {
                ThuVien thuVien = new ThuVien(img, ten);
                arr.add(thuVien);
            }
        }
        thuVienAlbumAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addControls() {
        btnback = findViewById(R.id.btn_back_album);
        btnplay = findViewById(R.id.btn_playalbum);
        imgHinh = findViewById(R.id.img_song_album);
        rcv = findViewById(R.id.rcv_songlalbum);
        arr = new ArrayList<>();
        thuVienAlbumAdapter = new ThuVienAlbumAdapter(ArtistSongActivity.this, arr);
        thuVienAlbumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    Integer Id = cursor.getInt(0);
                    String ten = cursor.getString(2);
                    if (data.equals(ten)) {
                        Intent intent = new Intent(ArtistSongActivity.this,PlayMusicActivity.class);
                        intent.putExtra("SongID", Id);
                        startActivity(intent);
                        break;
                    }
                }
                cursor.close();
            }
        });
        rcv.setAdapter(thuVienAlbumAdapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayout);
    }

    @Override
    public void onItemClick(String data) {

    }
}