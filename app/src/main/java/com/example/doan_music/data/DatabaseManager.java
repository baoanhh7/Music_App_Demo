package com.example.doan_music.data;

import android.content.Context;

import java.io.IOException;

public class DatabaseManager {

    private static DbHelper dbHelper;

    public static DbHelper dbHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
            try {
                dbHelper.createDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dbHelper;
    }
}

