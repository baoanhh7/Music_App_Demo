package com.example.doan_music.admin.ablum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.admin.AlbumAdminAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Ablum;

import java.util.ArrayList;


public class AblumActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase database = null;
    DbHelper dbHelper;
    Button btn_add_ablum;
    AlbumAdminAdapter albumAdapter;
    ArrayList<Ablum> arrayAblum;
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
        arrayAblum.clear();
        while (cursor.moveToNext()) {
            Integer ma = Integer.parseInt(cursor.getString(0));
            String ten = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            Integer maartist = Integer.parseInt(cursor.getString(3));
            Ablum ablum = new Ablum(ma, ten, img,maartist);
            arrayAblum.add(ablum);
        }
        albumAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void xulaycapnhat() {
    }

    private void addEvents() {
        btn_add_ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AblumActivity.this,AddAlbumActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btn_add_ablum = findViewById(R.id.btn_add_ablum);
        listView = findViewById(R.id.lvAblum_admin);
        arrayAblum = new ArrayList<>();
        albumAdapter = new AlbumAdminAdapter(AblumActivity.this,arrayAblum);
        listView.setAdapter(albumAdapter);
    }
}