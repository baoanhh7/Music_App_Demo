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
    EditText edt_idPlaylist_songadmin, edt_id_songadmin, edt_name_songadmin, edt_idAlbum_songadmin, edt_idArtist_songadmin, edt_linknhac_songadmin;
    DbHelper dbHelper;
    Button btnSave, btnCancel, btn_choose_image_addSongAdmin;
    ImageButton btn_camera;
    SQLiteDatabase database = null;
    ImageView imageView;
    Spinner sp_idAlbum_songadmin, sp_idArtist_songadmin, sp_playlist_songadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        addControls();
        addEvents();

        createData();
    }

    private void createData() {
        dbHelper = DatabaseManager.dbHelper(this);
        database = dbHelper.getReadableDatabase();

        // Album
        List<String> listAlbum = new ArrayList<>();
        listAlbum.add(0, "Null");

        Cursor cursor = database.rawQuery("select * from Albums", null);
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
                if (ten.equals("Null")) {
                    edt_idAlbum_songadmin.setText("0");
                }
                Cursor cursor = database.rawQuery("select * from Albums", null);
                while (cursor.moveToNext()) {
                    int idAlbum = cursor.getInt(0);
                    String name = cursor.getString(1);
                    if (ten.equals(name)) {
                        edt_idAlbum_songadmin.setText(String.valueOf(idAlbum));
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

        Cursor cursor1 = database.rawQuery("select * from Artists", null);
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

                Cursor cursor = database.rawQuery("select * from Artists", null);
                while (cursor.moveToNext()) {
                    int idArtist = cursor.getInt(0);
                    String name = cursor.getString(1);
                    if (ten.equals(name)) {
                        edt_idArtist_songadmin.setText(String.valueOf(idArtist));
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Playlist
        List<String> listPlaylist = new ArrayList<>();
        listPlaylist.add(0, "Null");

        Cursor cursor2 = database.rawQuery("select * from Playlists", null);
        while (cursor2.moveToNext()) {
            String name = cursor2.getString(1);

            listPlaylist.add(name);
        }
        cursor2.close();

        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listPlaylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_playlist_songadmin.setAdapter(adapter2);
        sp_playlist_songadmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ten = listPlaylist.get(position);

                if (ten.equals("Null")) {
                    edt_idPlaylist_songadmin.setText("0");
                }

                Cursor cursor = database.rawQuery("select * from Playlists", null);
                while (cursor.moveToNext()) {
                    int idPlaylist = cursor.getInt(0);
                    String name = cursor.getString(1);
                    if (ten.equals(name)) {
                        edt_idPlaylist_songadmin.setText(String.valueOf(idPlaylist));
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

                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);

                ContentValues values = new ContentValues();
                values.put("SongID", edt_id_songadmin.getText().toString());
                values.put("AlbumID", edt_idAlbum_songadmin.getText().toString());
                values.put("SongName", edt_name_songadmin.getText().toString());
                values.put("ArtistID", edt_idArtist_songadmin.getText().toString());
                values.put("PlaylistID", edt_idPlaylist_songadmin.getText().toString());
                values.put("SongImage", anh);
                values.put("LinkSong", edt_linknhac_songadmin.getText().toString());

                dbHelper = DatabaseManager.dbHelper(AddSongActivity.this);
                long kq = dbHelper.getReadableDatabase().insert("Songs", null, values);
                if (kq > 0) {
                    Toast.makeText(AddSongActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(AddSongActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        edt_id_songadmin = findViewById(R.id.edt_id_songadmin);
        edt_name_songadmin = findViewById(R.id.edt_name_songadmin);

        edt_idArtist_songadmin = findViewById(R.id.edt_idArtist_songadmin);
        edt_idAlbum_songadmin = findViewById(R.id.edt_idAlbum_songadmin);
        edt_linknhac_songadmin = findViewById(R.id.edt_linknhac_songadmin);
        edt_idPlaylist_songadmin = findViewById(R.id.edt_idPlaylist_songadmin);

        imageView = findViewById(R.id.img_addSongAdmin);

        btnSave = findViewById(R.id.btn_save_songadmin);
        btnCancel = findViewById(R.id.btn_cancel_songadmin);
        btn_choose_image_addSongAdmin = findViewById(R.id.btn_choose_image_addSongAdmin);
        btn_camera = findViewById(R.id.btn_camera_Songadmin);

        sp_idAlbum_songadmin = findViewById(R.id.sp_idAlbum_songadmin);
        sp_idArtist_songadmin = findViewById(R.id.sp_idArtist_songadmin);
        sp_playlist_songadmin = findViewById(R.id.sp_playlist_songadmin);
    }
}