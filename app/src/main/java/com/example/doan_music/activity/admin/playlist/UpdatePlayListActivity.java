package com.example.doan_music.activity.admin.playlist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;

public class UpdatePlayListActivity extends AppCompatActivity {
    ImageView img_update;
    Button btn_choose_image, btn_update, btn_cancel;
    EditText edt_id_playlist, edt_name_playlist;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_play_list);

        addControls();
        addEvent();

        updateData();
    }

    private void addEvent() {
//        btn_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ContentValues values = new ContentValues();
//                values.put("id", edt_id_playlist.getText().toString());
//                values.put("name", edt_name_playlist.getText().toString());
//                int id = intent.getIntExtra("id", -1);
//
//                long result = PlayListAdminActivity.database.update("Playlists", values, "PlaylistID=?"
//                        , new String[]{id + ""});
//                if (result > 0) finish();
//                else {
//                    Toast.makeText(UpdatePlayListActivity.this, "Update fail", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateData() {
        DbHelper dbHelper = DatabaseManager.dbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int id = intent.getIntExtra("id", -1);
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