package com.example.doan_music.LoginPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.activity.MainActivity;

public class login_user extends AppCompatActivity {
    EditText EdtEmail, EdtPassword;
    TextView tvForgotPass, tvSignup;
    Button btnLogin, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        AddControl();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_user.this, register_email.class));
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_user.this, Login.class));
            }
        });
    }

    private void checkCrededentials() {
        String email = EdtEmail.getText().toString();
        String password = EdtPassword.getText().toString();
        if (email.isEmpty() || !email.contains("@")) {
            showError(EdtEmail, "Your email is not valid");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(EdtPassword, "Your password must be 7 character");
        } else {
            Toast.makeText(login_user.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(login_user.this, MainActivity.class));
        }
    }

    private void showError(EditText Edt, String s) {
        Edt.setError(s);
        Edt.requestFocus();
    }

    public void AddControl() {
        EdtEmail = findViewById(R.id.EdtEmail);
        EdtPassword = findViewById(R.id.EdtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvSignup = findViewById(R.id.tvSignup);
        btn_back = findViewById(R.id.btn_back);
    }
}