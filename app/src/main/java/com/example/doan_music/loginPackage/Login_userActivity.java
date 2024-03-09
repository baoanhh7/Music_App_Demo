package com.example.doan_music.loginPackage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.activity.MainActivity;
import com.example.doan_music.activity.admin.AdminActivity;
import com.example.doan_music.model.Ablum;

public class Login_userActivity extends AppCompatActivity {
    EditText EdtEmail, EdtPassword;
    TextView tvForgotPass, tvSignup;
    Button btnLogin, btn_back;
    SQLiteDatabase database = null;

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
                startActivity(new Intent(Login_userActivity.this, Register_emailActivity.class));
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_userActivity.this, LoginActivity.class));
            }
        });

    }

    private void checkCrededentials() {
        String email = EdtEmail.getText().toString();
        String password = EdtPassword.getText().toString();
         if (password.isEmpty() || password.length() < 7) {
            showError(EdtPassword, "Your password must be 7 character");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(EdtEmail, "Your email is not valid");
        } else {
            database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("select * from Users", null);
            while (cursor.moveToNext()) {

                String Email = cursor.getString(2);
                String Password = cursor.getString(3);
                String Role = cursor.getString(4);
                if (email.equals(Email) && password.equals(Password) && Role.equals("admin")) {
                    Toast.makeText(Login_userActivity.this, "Login Admin Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_userActivity.this, AdminActivity.class));
                    break;
                }
                else if(email.equals(Email) && password.equals(Password));
                {
                    Toast.makeText(Login_userActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_userActivity.this, MainActivity.class));
                    break;
                }
            }
            cursor.close();
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