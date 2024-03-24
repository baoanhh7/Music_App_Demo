package com.example.doan_music.loginPackage;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ConfirmGmailActivity extends AppCompatActivity {

    TextView txt_myGmail, txt_resendEmail;
    Button btn_confirm;

    DbHelper dbHelper;
    SQLiteDatabase database = null;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_gmail);

        addControls();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(ConfirmGmailActivity.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait...");

                intent = getIntent();
                String email = intent.getStringExtra("email");
                String name = intent.getStringExtra("name");
                String password = intent.getStringExtra("password");

                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                    progressDialog.hide();
                    if (task.isSuccessful()) {
                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                            ContentValues values = new ContentValues();
                            // key phải trùng với tên cột trong table
                            values.put("Username", name);
                            values.put("Email", email);
                            values.put("Password", password);

                            dbHelper = DatabaseManager.dbHelper(ConfirmGmailActivity.this);
                            database = dbHelper.getWritableDatabase();

                            database.insert("Users", null, values);

                            Toast.makeText(ConfirmGmailActivity.this, "Verify your email successfully !", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ConfirmGmailActivity.this, Login_userActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ConfirmGmailActivity.this, "Please verify your email !", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ConfirmGmailActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        txt_resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(ConfirmGmailActivity.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Resend email...");

                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                intent = getIntent();
                String email = intent.getStringExtra("email");

                progressDialog.show();

                firebaseAuth.getCurrentUser().updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ConfirmGmailActivity.this, "Resent email verification link", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ConfirmGmailActivity.this, "Failed to resend email verification link", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ConfirmGmailActivity.this, "Failed to update email address", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void addControls() {
        txt_myGmail = findViewById(R.id.txt_myGmail);
        btn_confirm = findViewById(R.id.btn_confirm);
        txt_resendEmail = findViewById(R.id.txt_resendEmail);

        intent = getIntent();
        String email = intent.getStringExtra("email");
        txt_myGmail.setText(email);
    }
}