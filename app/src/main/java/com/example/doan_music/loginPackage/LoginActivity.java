package com.example.doan_music.loginPackage;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;

public class LoginActivity extends AppCompatActivity {

    Button LoginID, RegisterID;
    DbHelper dbHelper = null;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        LoginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Login_userActivity.class);
                startActivity(intent);
            }
        });
        RegisterID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register_emailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        LoginID = findViewById(R.id.LoginID);
        RegisterID = findViewById(R.id.RegisterID);

        // tạo db
        dbHelper = DatabaseManager.dbHelper(this);
        // mở cơ sở dữ liệu thực hiên thao tác
        database = dbHelper.getWritableDatabase();
    }
}