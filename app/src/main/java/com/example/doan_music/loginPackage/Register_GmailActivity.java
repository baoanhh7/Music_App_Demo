package com.example.doan_music.loginPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Register_GmailActivity extends AppCompatActivity {

    EditText edt_name, edt_email;
    TextInputEditText edt_password, edt_confirm_password;
    Button btn_dangky;
    TextView txt_dangnhap;
    int code;
    DbHelper dbHelper = DatabaseManager.dbHelper(this);
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_gmail);

        addControls();
        addEvents();

        final ProgressDialog progressDialog = new ProgressDialog(Register_GmailActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...");
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String conf_password = edt_confirm_password.getText().toString();

                if (name.isEmpty()) {
                    edt_name.setError("Your name is not valid!");
                } else if (email.isEmpty() || !email.contains("@gmail.com")) {
                    edt_email.setError("Your Email is not valid!");
                } else if (password.isEmpty() || password.length() < 7) {
                    edt_password.setError("Your password must be at least 8 character");
                } else if (conf_password.isEmpty() || !conf_password.equals(password)) {
                    edt_confirm_password.setError("Your password is not match");
                } else {
                    database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                    Cursor cursor = database.rawQuery("select * from Users", null);
                    while (cursor.moveToNext()) {
                        String Email = cursor.getString(2).trim();

                        if (email.equals(Email)) {
                            edt_email.setError("Your email has been registered");
                            break;
                        } else {
                            progressDialog.show();

                            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {

                                progressDialog.hide();
                                if (task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Intent intent = new Intent(Register_GmailActivity.this, ConfirmGmailActivity.class);
                                                intent.putExtra("email", email);
                                                intent.putExtra("password", password);
                                                intent.putExtra("name", name);

                                                startActivity(intent);

                                                Toast.makeText(Register_GmailActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(Register_GmailActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(Register_GmailActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            });
                            break;
                        }
                    }
                    cursor.close();
                }
            }
        });

    }

    private void addEvents() {
        txt_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_GmailActivity.this, Login_userActivity.class));
            }
        });
    }

    private void addControls() {
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        btn_dangky = findViewById(R.id.btn_dangky);
        txt_dangnhap = findViewById(R.id.txt_dangnhap);
    }
}