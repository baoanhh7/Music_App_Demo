package com.example.doan_music.activity.admin.playlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

public class UpdatePlayListActivity extends AppCompatActivity {
    final int choose_img = 1;
    final int photo_img = 2;
    ImageView img_update;
    Button btn_choose_image, btn_camera, btn_update, btn_cancel;
    EditText edt_id_playlist, edt_name_playlist;
    Intent intent = null;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_play_list);

        addControls();
        addEvent();

        getData();
    }

    private void addEvent() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edt_id_playlist.getText().toString();
                String ten = edt_name_playlist.getText().toString();

                byte[] anh = getByteArrayFromImageView(img_update);

                ContentValues values = new ContentValues();
                // key phải trùng với tên cột trong table
                values.put("PlaylistID", ma);
                values.put("PlaylistName", ten);
                values.put("PlaylistImage", anh);

                DbHelper dbHelper = DatabaseManager.dbHelper(UpdatePlayListActivity.this);
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                id = intent.getIntExtra("id", -1);
                long kq = database.update("Playlists", values, "PlaylistID=?", new String[]{id + ""});
                if (kq > 0) {
                    Toast.makeText(UpdatePlayListActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(UpdatePlayListActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
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
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, photo_img);
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
        startActivityForResult(intent, choose_img);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == choose_img) {
                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    img_update.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (requestCode == photo_img) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                img_update.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData() {
        DbHelper dbHelper = DatabaseManager.dbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        id = intent.getIntExtra("id", -1);
        Cursor cursor = database.rawQuery("SELECT * FROM Playlists where PlaylistID=?"
                , new String[]{id + ""});
        cursor.moveToFirst();
        int ma = cursor.getInt(0);
        String ten = cursor.getString(1);
        byte[] img = cursor.getBlob(2);

        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        // set dữ liệu bên update
        img_update.setImageBitmap(bitmap);
        edt_id_playlist.setText(ma + "");
        edt_name_playlist.setText(ten);
    }

    private void addControls() {
        img_update = findViewById(R.id.img_update);

        btn_choose_image = findViewById(R.id.btn_choose_image);
        btn_camera = findViewById(R.id.btn_camera);
        btn_update = findViewById(R.id.btn_update);
        btn_cancel = findViewById(R.id.btn_cancel);

        edt_id_playlist = findViewById(R.id.edt_id_playlist);
        edt_name_playlist = findViewById(R.id.edt_name_playlist);

        intent = getIntent();
        edt_id_playlist.setText(intent.getIntExtra("id", -1) + "");
        edt_name_playlist.setText(intent.getStringExtra("name"));

        edt_id_playlist.setEnabled(false);
    }
}