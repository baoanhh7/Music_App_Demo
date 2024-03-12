package com.example.doan_music.loginPackage;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Intent;
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

public class OTPActivity extends AppCompatActivity {
    EditText otpEditText;
    Button verifyButton;
    TextView sendOtpAgain;
    DbHelper dbHelper;
    String phone, verificationId, username, email, password;
    FirebaseAuth auth;
    SQLiteDatabase database = null;
    PhoneAuthProvider.ForceResendingToken mforceResendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        auth = FirebaseAuth.getInstance();
        getDataIntent();
        addControls();
        addEvents();
    }

    private void addEvents() {
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOTP = otpEditText.getText().toString().trim();
                onClickSendOTPCode(strOTP);
            }
        });
        sendOtpAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendOTPAgain();
            }
        });
    }

    private void onClickSendOTPAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)// (optional) Activity for callback binding
                        .setForceResendingToken(mforceResendingToken)
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
                                Toast.makeText(OTPActivity.this, "Verification Fail ", Toast.LENGTH_SHORT);
                            }

                            @Override
                            public void onCodeSent(@NonNull String mverificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                verificationId = mverificationId;
                                mforceResendingToken = forceResendingToken;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void onClickSendOTPCode(String strOTP) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, strOTP);
        signInWithPhoneAuthCredential(credential);
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
                                Toast.makeText(OTPActivity.this, "/ The verification code entered was invalid", Toast.LENGTH_SHORT);
                            }
                        }
                    }
                });
    }

    private void goToLogin() {
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Email", email);
        values.put("Phone", phone);
        values.put("Password", password);
        dbHelper = DatabaseManager.dbHelper(OTPActivity.this);
        long kq = dbHelper.getReadableDatabase().insert("Users", null, values);
        if (kq > 0) {
            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(OTPActivity.this, Login_userActivity.class));
        } else
            Toast.makeText(OTPActivity.this, "Register Fail", Toast.LENGTH_SHORT);
    }

    private void addControls() {
        otpEditText = findViewById(R.id.otpEditText);
        verifyButton = findViewById(R.id.verifyButton);
        sendOtpAgain = findViewById(R.id.txt_sendOtpAgain);

    }

    private void getDataIntent() {
        phone = getIntent().getStringExtra("phone");
        verificationId = getIntent().getStringExtra("verification_Id");
        username = getIntent().getStringExtra("Username");
        email = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("Password");
    }
}


