package com.example.doan_music.LoginPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_music.R;

public class register_email extends AppCompatActivity {

    EditText EdtUsername, EdtEmail, EdtPassword, EdtRepassword;
    Button btnRegister, btn_back;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        addControls();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_email.this, login_user.class));
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_email.this, Login.class));
            }
        });
    }

    private void checkCredentials() {
        String username=EdtUsername.getText().toString();
        String email=EdtEmail.getText().toString();
        String password=EdtPassword.getText().toString();
        String repassword=EdtRepassword.getText().toString();
        if(username.isEmpty()) {
            showError(EdtUsername,"Your username is not valid!");
        }
        else if(email.isEmpty() || !email.contains("@")) {
            showError(EdtEmail,"Your Email is not valid!");
        }
        else if (password.isEmpty() || password.length()<7) {
            showError(EdtPassword,"Your password must be at least 8 character");
        }
        else if (repassword.isEmpty() || !repassword.equals(password)) {
            showError(EdtRepassword,"Your password is not match");
        }
        else {
            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(register_email.this, login_user.class));
        }
    }

    private void showError(EditText Edt, String s) {
        Edt.setError(s);
        Edt.requestFocus();
    }

    private void addControls() {
        EdtUsername = findViewById(R.id.EdtUsername);
        EdtEmail = findViewById(R.id.EdtEmail);
        EdtPassword = findViewById(R.id.EdtPassword);
        EdtRepassword = findViewById(R.id.EdtRepassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        btn_back = findViewById(R.id.btn_back);
    }
}