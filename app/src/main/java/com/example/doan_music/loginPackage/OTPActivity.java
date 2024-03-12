package com.example.doan_music.loginPackage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan_music.R;
import com.example.doan_music.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPActivity extends AppCompatActivity {
    int code;
    EditText otpEditText;
    Button verifyButton;
    String email;
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        addControls();
        a();
        addEvents();
    }

    private void addEvents() {
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputcode = otpEditText.getText().toString();
                if (inputcode.equals(String.valueOf(code))) {
                    database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                    Cursor cursor = database.rawQuery("select * from Users", null);
                    while (cursor.moveToNext()) {
                        Integer ma = Integer.valueOf(cursor.getString(0) + "");
                        String Email = cursor.getString(2);
                        if (Email.equals(email)) {
                            Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                            intent.putExtra("maU", ma);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    private void addControls() {
        otpEditText = findViewById(R.id.otpEditText);
        verifyButton = findViewById(R.id.verifyButton);
        // Lấy Intent đã được chuyển từ Login_userActivity
        Intent intent = getIntent();

        // Kiểm tra xem có dữ liệu "maU" được chuyển không
        if (intent.hasExtra("emailU")) {
            // Lấy dữ liệu từ Intent
            email = intent.getStringExtra("emailU");
        }
    }

    private void a() {
        Random random = new Random();
        code = random.nextInt(8999) + 1000;
        String url = "https://doanmusic.000webhostapp.com/sendEmail.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(OTPActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(OTPActivity.this, "" + volleyError, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("code", String.valueOf(code));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
