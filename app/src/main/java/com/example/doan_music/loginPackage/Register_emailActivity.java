package com.example.doan_music.loginPackage;

import android.content.ContentValues;
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
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;

public class Register_emailActivity extends AppCompatActivity {

    EditText EdtUsername, EdtEmail, EdtPassword, EdtRepassword;
    Button btnRegister, btn_back;
    TextView tvLogin;
    DbHelper dbHelper;
    SQLiteDatabase database = null;

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
                startActivity(new Intent(Register_emailActivity.this, Login_userActivity.class));
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_emailActivity.this, LoginActivity.class));
            }
        });
    }

    private void checkCredentials() {
        String username = EdtUsername.getText().toString();
        String email = EdtEmail.getText().toString();
        String password = EdtPassword.getText().toString();
        String repassword = EdtRepassword.getText().toString();


        if (username.isEmpty()) {
            showError(EdtUsername, "Your username is not valid!");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(EdtEmail, "Your Email is not valid!");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(EdtPassword, "Your password must be at least 8 character");
        } else if (repassword.isEmpty() || !repassword.equals(password)) {
            showError(EdtRepassword, "Your password is not match");
        } else {
            database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("select * from Users", null);
            while (cursor.moveToNext()) {
                String Email = cursor.getString(2);
                if (email.equals(Email)) {
                    showError(EdtEmail, "This email has been registered");
                    break;
                } else {
                    ContentValues values = new ContentValues();
                    values.put("Username", username);
                    values.put("Email", email);
                    values.put("Password", password);
                    dbHelper = DatabaseManager.dbHelper(Register_emailActivity.this);
                    long kq = dbHelper.getReadableDatabase().insert("Users", null, values);
                    if (kq > 0) {
                        Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register_emailActivity.this, Login_userActivity.class));
                        break;
                    } else
                        Toast.makeText(Register_emailActivity.this, "Register Fail", Toast.LENGTH_SHORT);
                    break;
                }
            }
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