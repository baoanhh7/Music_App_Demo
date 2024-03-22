package com.example.doan_music.activity.admin.song;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddSongActivity extends AppCompatActivity {
    EditText edtMa, edtTen, edtMaArtist, edtMaAlbum, Linknhac;
    DbHelper dbHelper;
    Button btnSave, btncancel, btn_choose_image_addSongAdmin;
    ImageButton btn_camera;
    SQLiteDatabase database = null;
    ImageView imageView;
    Spinner sp_idAlbum_songadmin, sp_idArtist_songadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        addControls();
        addEvents();

        createData();
    }

    private void createData() {
        DbHelper dbHelper1 = DatabaseManager.dbHelper(this);
        SQLiteDatabase database1 = dbHelper1.getReadableDatabase();

        // Album
        List<String> listAlbum = new ArrayList<>();
        listAlbum.add("null");

        Cursor cursor = database1.rawQuery("select * from Albums", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);

            listAlbum.add(name);
        }
        cursor.close();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listAlbum);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_idAlbum_songadmin.setAdapter(adapter);
        sp_idAlbum_songadmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ten = listAlbum.get(position);
                if(ten == "null")
                {
                    edtMaAlbum.setText("null");
                }
                Cursor cursor = database1.rawQuery("select * from Albums", null);
                while (cursor.moveToNext()) {
                    int id1 = cursor.getInt(0);
                    String name = cursor.getString(1);
                    if (ten.equals(name)) {
                        edtMaAlbum.setText(String.valueOf(id1));
                        break;
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Artist
        List<String> listArtist = new ArrayList<>();

        Cursor cursor1 = database1.rawQuery("select * from Artists", null);
        while (cursor1.moveToNext()) {
            String name = cursor1.getString(1);

            listArtist.add(name);
        }
        cursor1.close();

        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listArtist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_idArtist_songadmin.setAdapter(adapter1);
        sp_idArtist_songadmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ten = listArtist.get(position);

                Cursor cursor = database1.rawQuery("select * from Artists", null);
                while (cursor.moveToNext()) {
                    int id1 = cursor.getInt(0);
                    String name = cursor.getString(1);
                    if (ten.equals(name)) {
                        edtMaArtist.setText(String.valueOf(id1));
                        break;
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] anh = getByteArrayFromImageView(imageView);
                String edtMaArtist1 = edtMaArtist.getText().toString();
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
//                Cursor cursor = database.rawQuery("select * from Artists", null);
                //while (cursor.moveToNext()) {
                // Integer maArtist = Integer.valueOf(cursor.getString(0) + "");
                ContentValues values = new ContentValues();
                values.put("SongID", edtMa.getText().toString() + "");
                values.put("AlbumID", edtMaAlbum.getText().toString() + "");
                values.put("SongName", edtTen.getText().toString());
                values.put("ArtistID", edtMaArtist.getText().toString() + "");
                values.put("SongImage", anh);
                values.put("LinkSong", Linknhac.getText().toString());
//                        values.put("StateFavorite",0);
//                        values.put("PlaylistID",0);
//                        values.put("View",0);
                dbHelper = DatabaseManager.dbHelper(AddSongActivity.this);
                long kq = dbHelper.getReadableDatabase().insert("Songs", null, values);
                if (kq > 0) {
                    Toast.makeText(AddSongActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(AddSongActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMa.setText("");
                edtTen.setText("");
                edtMaArtist.setText("");
                startActivity(new Intent(AddSongActivity.this, SongActivity.class));
            }
        });
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        btn_choose_image_addSongAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
    }

    private byte[] getByteArrayFromImageView(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
        if (ActivityCompat.checkSelfPermission(AddSongActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddSongActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, 99);
        //}
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            // Xử lý ảnh đã chụp tại đây (nếu cần)
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Hiển thị ảnh hoặc thực hiện các xử lý khác theo nhu cầu của bạn
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addControls() {
        edtMa = findViewById(R.id.edt_id_songadmin);
        edtTen = findViewById(R.id.edt_name_songadmin);
        imageView = findViewById(R.id.img_addSongAdmin);
        edtMaArtist = findViewById(R.id.edt_idArtist_songadmin);
        edtMaAlbum = findViewById(R.id.edt_idAlbum_songadmin);
        Linknhac = findViewById(R.id.edt_linknhac_songadmin);
        btnSave = findViewById(R.id.btn_save_songadmin);
        btncancel = findViewById(R.id.btn_cancel_songadmin);
        btn_choose_image_addSongAdmin = findViewById(R.id.btn_choose_image_addSongAdmin);
        btn_camera = findViewById(R.id.btn_camera_Songadmin);

        sp_idAlbum_songadmin = findViewById(R.id.sp_idAlbum_songadmin);
        sp_idArtist_songadmin = findViewById(R.id.sp_idArtist_songadmin);
    }
}