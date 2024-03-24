package com.example.doan_music.activity.admin.playlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPlayListActivity extends AppCompatActivity {
    final int choose_img = 1;
    final int photo_img = 2;
    ImageView img_add;
    Button btn_choose_image, btn_save, btn_cancel, btn_camera;
    EditText edt_id_playlist, edt_name_playlist;
    DbHelper dbHelper;
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_play_list);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edt_id_playlist.getText().toString();
                String ten = edt_name_playlist.getText().toString();

                byte[] anh = getByteArrayFromImageView(img_add);

                ContentValues values = new ContentValues();
                // key phải trùng với tên cột trong table
                values.put("PlaylistID", ma);
                values.put("PlaylistName", ten);
                values.put("PlaylistImage", anh);

                dbHelper = DatabaseManager.dbHelper(AddPlayListActivity.this);
                database = dbHelper.getWritableDatabase();

                long kq = database.insert("Playlists", null, values);

                if (kq > 0) {
                    Toast.makeText(AddPlayListActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(AddPlayListActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private void addControls() {
        img_add = findViewById(R.id.img_add);

        btn_choose_image = findViewById(R.id.btn_choose_image);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_camera = findViewById(R.id.btn_camera);

        edt_id_playlist = findViewById(R.id.edt_id_playlist);
        edt_name_playlist = findViewById(R.id.edt_name_playlist);
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, choose_img);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, photo_img);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == choose_img) {
                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    img_add.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (requestCode == photo_img) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                img_add.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}