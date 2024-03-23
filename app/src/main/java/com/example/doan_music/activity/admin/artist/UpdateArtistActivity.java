package com.example.doan_music.activity.admin.artist;

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

public class UpdateArtistActivity extends AppCompatActivity {

    EditText edtMa, edtTen;
    DbHelper dbHelper;
    Button btnSave, btncancel, btn_choose_image_updateArtistAdmin;
    ImageButton btn_camera;
    SQLiteDatabase database = null;
    ImageView imageView;
    List<Integer> listIDArtist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_artist);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] anh = getByteArrayFromImageView(imageView);
                ContentValues values = new ContentValues();
                values.put("ArtistName", edtTen.getText().toString());
                values.put("ArtistImage", anh);
                dbHelper = DatabaseManager.dbHelper(UpdateArtistActivity.this);
                Integer id = Integer.valueOf(edtMa.getText().toString());
                long kq = dbHelper.getReadableDatabase().update("Artists", values, "ArtistID=?", new String[]{id + ""});
                if (kq > 0) {
                    Toast.makeText(UpdateArtistActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(UpdateArtistActivity.this, "Update thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMa.setText("");
                edtTen.setText("");
                startActivity(new Intent(UpdateArtistActivity.this, ArtistActivity.class));
            }
        });
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        btn_choose_image_updateArtistAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
    }


    private void addControls() {
        edtMa = findViewById(R.id.edt_id_updateartistadmin);
        edtTen = findViewById(R.id.edt_name_updateartistadmin);
        imageView = findViewById(R.id.img_updateArtistAdmin);
        btnSave = findViewById(R.id.btn_save_updateartistadmin);
        btncancel = findViewById(R.id.btn_cancel_updateartistadmin);
        btn_choose_image_updateArtistAdmin = findViewById(R.id.btn_choose_image_updateArtistAdmin);
        btn_camera = findViewById(R.id.btn_camera_updateartistadmin);
        edtMa.setText(getIntent().getIntExtra("id", -1) + "");
        edtTen.setText(getIntent().getStringExtra("name"));
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM Artists where ArtistID=?"
                , new String[]{getIntent().getIntExtra("id", -1) + ""});
        cursor.moveToFirst();
        byte[] img = cursor.getBlob(2);

        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        // set dữ liệu bên update
        imageView.setImageBitmap(bitmap);
        edtMa.setEnabled(false);
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
        if (ActivityCompat.checkSelfPermission(UpdateArtistActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateArtistActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
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
}