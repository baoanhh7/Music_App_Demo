package com.example.doan_music.activity.library;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.thuvien.AddNgheSiAdapter;
import com.example.doan_music.model.AddNgheSi_ThuVien;

import java.util.ArrayList;

public class AddNgheSiActivity extends AppCompatActivity {

    SearchView search_thuvien_addArtist;
    RecyclerView recycler_Artists_thuvien_add;
    AddNgheSiAdapter addNgheSiAdapter;
    ArrayList<AddNgheSi_ThuVien> arrayList;
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_nghe_si);
        addControls();
        loadData();
        addEvents();
    }

    private void addEvents() {
        search_thuvien_addArtist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addNgheSiAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                addNgheSiAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void loadData() {
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Artists", null);
        arrayList.clear();
        while (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            AddNgheSi_ThuVien addNgheSiThuVien = new AddNgheSi_ThuVien(img, ten);
            arrayList.add(addNgheSiThuVien);
        }
        addNgheSiAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addControls() {
        search_thuvien_addArtist = findViewById(R.id.search_thuvien_addArtist);
        recycler_Artists_thuvien_add = findViewById(R.id.recycler_Artists_thuvien_add);
        arrayList = new ArrayList<>();
        addNgheSiAdapter = new AddNgheSiAdapter(this, arrayList);
        recycler_Artists_thuvien_add.setAdapter(addNgheSiAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler_Artists_thuvien_add.setLayoutManager(gridLayoutManager);
    }
}