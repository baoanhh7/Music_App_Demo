package com.example.doan_music.activity.admin.ablum;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;

public class AddAlbumActivity extends AppCompatActivity{

    EditText edtMa, edtTen, edtMaArtist;
    DbHelper dbHelper;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("AlbumID",edtMa.getText().toString()+"");
                values.put("AlbumName",edtTen.getText().toString());
                values.put("ArtistID",edtMaArtist.getText().toString()+"");
                dbHelper = DatabaseManager.dbHelper(AddAlbumActivity.this);
                long kq = dbHelper.getReadableDatabase().insert("Albums",null,values);
                if(kq>0)
                    finish();
                else
                    Toast.makeText(AddAlbumActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT);
            }
        });
    }

    private void addControls() {
        edtMa = findViewById(R.id.edt_IDAblum_admin);
        edtTen = findViewById(R.id.edt_TenAblum_admin);
        edtMaArtist = findViewById(R.id.edt_IDArtist_Ablum_Admin);
        btnSave = findViewById(R.id.btn_Save_Ablum_Admin);
    }
}