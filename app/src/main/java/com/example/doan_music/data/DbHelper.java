package com.example.doan_music.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "doanmusic.db";
    private static final int DB_VERSION = 1;
    private final Context context;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cập nhật cơ sở dữ liệu khi phiên bản thay đổi
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // createDatabase(db);
    }

    public void copyDatabaseFromAssets() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = context.getDatabasePath(DB_NAME).getPath();
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDatabase() throws IOException {
        boolean isDatabaseExist = checkDatabase();

        if (!isDatabaseExist) {
            this.getReadableDatabase();
            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }
}
