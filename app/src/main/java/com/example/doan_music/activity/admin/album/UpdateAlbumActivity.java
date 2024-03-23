package com.example.doan_music.activity.admin.album;

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

import androidx.activity.EdgeToEdge;
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

public class UpdateAlbumActivity extends AppCompatActivity {
    EditText edtMa, edtTen, edtMaArtist;
    DbHelper dbHelper;
    Button btnSave, btncancel, btn_choose_image_addAblumAdmin;
    ImageButton btn_camera;
    SQLiteDatabase database = null;
    ImageView imageView;
    Spinner sp_id_artist_updatealbumadmin;
    List<String> listIDArtist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_album);
        addControls();
        createDataSpinner();
        addEvents();
    }

    private void createDataSpinner() {
        // Artist

        Cursor cursor1 = database.rawQuery("select * from Artists", null);
        while (cursor1.moveToNext()) {
            String name = cursor1.getString(1);

            listIDArtist.add(name);
        }
        cursor1.close();

        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listIDArtist);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_id_artist_updatealbumadmin.setAdapter(adapter1);
        sp_id_artist_updatealbumadmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ten = listIDArtist.get(position);

                Cursor cursor = database.rawQuery("select * from Artists", null);
                while (cursor.moveToNext()) {
                    int idArtist = cursor.getInt(0);
                    String name = cursor.getString(1);
                    if (ten.equals(name)) {
                        edtMaArtist.setText(String.valueOf(idArtist));
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
                ContentValues values = new ContentValues();
                values.put("AlbumID", edtMa.getText().toString() + "");
                values.put("AlbumName", edtTen.getText().toString());
                values.put("ArtistID", edtMaArtist.getText().toString() + "");
                values.put("AlbumImage", anh);
                dbHelper = DatabaseManager.dbHelper(UpdateAlbumActivity.this);
                Integer id = Integer.valueOf(edtMa.getText().toString());
                long kq = dbHelper.getReadableDatabase().update("Albums", values, "AlbumID=?", new String[]{id + ""});
                if (kq > 0) {
                    Toast.makeText(UpdateAlbumActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(UpdateAlbumActivity.this, "Update thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTen.setText("");
                edtMaArtist.setText("");
                startActivity(new Intent(UpdateAlbumActivity.this, AlbumActivity.class));
            }
        });
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        btn_choose_image_addAblumAdmin.setOnClickListener(new View.OnClickListener() {
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

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
        if (ActivityCompat.checkSelfPermission(UpdateAlbumActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateAlbumActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, 99);
        //}
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
        edtMa = findViewById(R.id.edt_id_updatealbumadmin);
        edtTen = findViewById(R.id.edt_name_updatealbumadmin);
        imageView = findViewById(R.id.img_updateAblumAdmin);
        edtMaArtist = findViewById(R.id.edt_idArtist_updatealbumadmin);
        sp_id_artist_updatealbumadmin = findViewById(R.id.sp_id_artist_updatealbumadmin);
        btnSave = findViewById(R.id.btn_save_updatealbumadmin);
        btncancel = findViewById(R.id.btn_cancel_updatealbumadmin);
        btn_choose_image_addAblumAdmin = findViewById(R.id.btn_choose_image_updateAblumAdmin);
        btn_camera = findViewById(R.id.btn_camera_updatealbumadmin);
        edtMa.setText(getIntent().getIntExtra("id", -1) + "");
        edtTen.setText(getIntent().getStringExtra("name"));
        edtMaArtist.setText(getIntent().getIntExtra("IDArtist", -1) + "");
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM Albums where AlbumID=?"
                , new String[]{getIntent().getIntExtra("id", -1) + ""});
        cursor.moveToFirst();
        byte[] img = cursor.getBlob(2);

        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        // set dữ liệu bên update
        imageView.setImageBitmap(bitmap);
        edtMa.setEnabled(false);
    }
}