package com.example.doan_music.LoginPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doan_music.R;

public class Login extends AppCompatActivity {

    Button LoginID, LoginPhoneID, RegisterID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        LoginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, login_user.class);
                startActivity(intent);
            }
        });
        RegisterID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, register_email.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        LoginID = findViewById(R.id.LoginID);
        RegisterID = findViewById(R.id.RegisterID);
    }
}