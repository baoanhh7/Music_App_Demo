package com.example.doan_music.loginPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button LoginID, RegisterID, RegisterGmail;
    DbHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello World");

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
        RegisterGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Register_GmailActivity.class));
            }
        });
    }

    private void addControls() {
        LoginID = findViewById(R.id.LoginID);
        RegisterID = findViewById(R.id.RegisterID);
        RegisterGmail = findViewById(R.id.RegisterGmail);

        // tạo db trong device
        dbHelper = DatabaseManager.dbHelper(this);
    }
}