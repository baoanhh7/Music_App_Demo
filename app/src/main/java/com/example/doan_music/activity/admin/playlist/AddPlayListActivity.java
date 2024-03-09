package com.example.doan_music.activity.admin.playlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPlayListActivity extends AppCompatActivity {
    ImageView img_add;
    Button btn_choose_image, btn_save, btn_cancel;
    EditText edt_id_playlist, edt_name_playlist;
    final int choose_img = 1;

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
    }

    private void addControls() {
        img_add = findViewById(R.id.img_add);

        btn_choose_image = findViewById(R.id.btn_choose_image);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);

        edt_id_playlist = findViewById(R.id.edt_id_playlist);
        edt_name_playlist = findViewById(R.id.edt_name_playlist);
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
                    img_add.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}