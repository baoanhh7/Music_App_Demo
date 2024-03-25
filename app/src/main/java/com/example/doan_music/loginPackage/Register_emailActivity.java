package com.example.doan_music.loginPackage;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Register_emailActivity extends AppCompatActivity {

    EditText EdtUsername, EdtEmail, EdtPassword, EdtRepassword, EdtPhone;
    Button btnRegister, btn_back;
    TextView tvLogin;
    DbHelper dbHelper;
    SQLiteDatabase database = null;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        auth = FirebaseAuth.getInstance();
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
        String phone = EdtPhone.getText().toString();
        String password = EdtPassword.getText().toString();
        String repassword = EdtRepassword.getText().toString();


        if (username.isEmpty()) {
            showError(EdtUsername, "Your username is not valid!");
        } else if (email.isEmpty() || !email.contains("@gmail.com")) {
            showError(EdtEmail, "Your Email is not valid!");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(EdtPassword, "Your password must be at least 8 character");
        } else if (repassword.isEmpty() || !repassword.equals(password)) {
            showError(EdtRepassword, "Your password is not match");
        } else if (phone.isEmpty() || (phone.length() < 12 && phone.length() > 12)) {
            showError(EdtPhone, "Your phone is not valid!");
        } else {
            database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("select * from Users", null);
            while (cursor.moveToNext()) {
                String Email = cursor.getString(2);
                if (email.equals(Email)) {
                    showError(EdtEmail, "This email has been registered");
                    return;
                }
            }
            cursor.close();
            onClickverifyPhone(phone);
        }
    }


    private void onClickverifyPhone(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                // This callback will be invoked in two situations:
                                // 1 - Instant verification. In some cases the phone number can be instantly
                                //     verified without needing to send or enter a verification code.
                                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                                //     detect the incoming verification SMS and perform verification without
                                //     user action.

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Register_emailActivity.this, "Verification Fail ", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                gotoOTP(phone, verificationId);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void gotoOTP(String phone, String verificationId) {
        Intent intent = new Intent(this, OTPActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("verification_Id", verificationId);
        intent.putExtra("Username", EdtUsername.getText().toString());
        intent.putExtra("Email", EdtEmail.getText().toString());
        intent.putExtra("Password", EdtPassword.getText().toString());
        startActivity(intent);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToLogin();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Register_emailActivity.this, "/ The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToLogin() {
        ContentValues values = new ContentValues();
        values.put("Username", EdtUsername.getText().toString());
        values.put("Email", EdtEmail.getText().toString());
        values.put("Phone", EdtPhone.getText().toString());
        values.put("Password", EdtPassword.getText().toString());
        dbHelper = DatabaseManager.dbHelper(Register_emailActivity.this);
        long kq = dbHelper.getReadableDatabase().insert("Users", null, values);
        if (kq > 0) {
            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Register_emailActivity.this, Login_userActivity.class));
        } else
            Toast.makeText(Register_emailActivity.this, "Register Fail", Toast.LENGTH_SHORT);
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
        EdtPhone = findViewById(R.id.EdtPhone);
        tvLogin = findViewById(R.id.tvLogin);
        btn_back = findViewById(R.id.btn_back);
    }
}