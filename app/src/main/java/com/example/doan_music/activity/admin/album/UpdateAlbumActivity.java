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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class UpdateAlbumActivity extends AppCompatActivity {
    EditText edtMa, edtTen, edtMaArtist;
    DbHelper dbHelper;
    Button btnSave, btncancel, btn_choose_image_addAblumAdmin;
    ImageButton btn_camera;
    SQLiteDatabase database = null;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_album);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] anh = getByteArrayFromImageView(imageView);
                String edtMaArtist1 = edtMaArtist.getText().toString();
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Artists", null);
                while (cursor.moveToNext()) {
                    Integer maArtist = Integer.valueOf(cursor.getString(0) + "");
                    if (Integer.valueOf(edtMaArtist1) == maArtist) {
                        ContentValues values = new ContentValues();
                        values.put("AlbumID", edtMa.getText().toString() + "");
                        values.put("AlbumName", edtTen.getText().toString());
                        values.put("Ablum_ArtistID", edtMaArtist.getText().toString() + "");
                        values.put("AlbumImage", anh);
                        dbHelper = DatabaseManager.dbHelper(UpdateAlbumActivity.this);
                        long kq = dbHelper.getReadableDatabase().insert("Albums", null, values);
                        if (kq > 0)
                            break;
                        else
                            Toast.makeText(UpdateAlbumActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT);
                    }
                }
                finish();

            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMa.setText("");
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