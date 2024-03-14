package com.example.doan_music.music;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_music.R;
import com.example.doan_music.model.LrcLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicActivity extends AppCompatActivity {

    ImageButton btn_play, btn_pause, btn_back, btn_next, btn_pre;
    SeekBar seekBar;
    TextView txt_time, txt_time_first;
    SQLiteDatabase database = null;
    MediaPlayer myMusic;
    TextView txtLoibaihat;
    List<LrcLine> lrcLines = new ArrayList<>();
    ArrayList<Integer> arr = new ArrayList<>();
    ImageView imageView_songs;
    TextView txt_artist_song, txt_name_song;
    Integer currentPosition;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        addControls();
        arr = (ArrayList<Integer>) getIntent().getSerializableExtra("arrIDSongs");
        Integer IDSong = getIntent().getIntExtra("SongID", -1);
        currentPosition = arr.indexOf(IDSong);
        myMusic = new MediaPlayer();
        //myMusic = MediaPlayer.create(this, R.raw.nhung_loi_hua_bo_quen);
        loadData();


        myMusic.setLooping(true);
        myMusic.seekTo(0);

        myMusic.start();

        // tạo biến duration để lưu thời gian bài hát
        String duration = timeSeekbar(myMusic.getDuration());
        txt_time.setText(duration);
        loadNameArtist();
        addEvents();
        // Bắt đầu cập nhật lời bài hát

    }

    private void loadNameArtist() {
        Integer IDSong = getIntent().getIntExtra("SongID", -1);
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * " +
                "from Artists " +
                "JOIN Songs ON Artists.ArtistID =Songs.ArtistID " +
                "WHERE Songs.SongID = ? ", new String[]{String.valueOf(IDSong)});
        while (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            txt_artist_song.setText(ten);
        }
    }

    private void loadData() {
        Integer IDSong = getIntent().getIntExtra("SongID", -1);
        database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Songs", null);
        while (cursor.moveToNext()) {
            Integer Id = cursor.getInt(0);
            String ten = cursor.getString(2);
            byte[] img = cursor.getBlob(3);
            String linkSong = cursor.getString(5);
            if (IDSong.equals(Id)) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                imageView_songs.setImageBitmap(bitmap);
                try {
                    myMusic.setDataSource(linkSong);
                    myMusic.prepare();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                txt_name_song.setText(ten);
            }
        }
        cursor.close();
    }

    public String timeSeekbar(int time) {
        String mTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        mTime = minutes + ":";
        if (seconds < 10) {
            mTime += "0";
        }
        mTime += seconds;
        return mTime;

    }

    private void addEvents() {
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMusic.isPlaying()) {
                    myMusic.pause();
                    btn_play.setImageResource(R.drawable.ic_play);

                } else {
                    myMusic.start();
                    btn_play.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMusic != null) {
                    btn_play.setImageResource(R.drawable.ic_pause);
                }
                if (currentPosition < arr.size() - 1) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
                }
                if (myMusic.isPlaying()) {
                    myMusic.stop();
                    myMusic.reset();
                }
                Integer idSong = arr.get(currentPosition);
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    Integer Id = cursor.getInt(0);
                    String ten = cursor.getString(2);
                    byte[] img = cursor.getBlob(3);
                    String linkSong = cursor.getString(5);
                    if (idSong.equals(Id)) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                        imageView_songs.setImageBitmap(bitmap);
                        try {
                            myMusic.setDataSource(linkSong);
                            myMusic.prepare();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        txt_name_song.setText(ten);
                    }
                }
                cursor.close();
                myMusic.start();
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor1 = database.rawQuery("select * " +
                        "from Artists " +
                        "JOIN Songs ON Artists.ArtistID =Songs.ArtistID " +
                        "WHERE Songs.SongID = ? ", new String[]{String.valueOf(idSong)});
                while (cursor.moveToNext()) {
                    String ten = cursor1.getString(1);
                    txt_artist_song.setText(ten);
                    break;
                }
            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMusic != null) {
                    btn_play.setImageResource(R.drawable.ic_pause);
                }
                if (currentPosition > 0) {
                    currentPosition--;
                } else {
                    currentPosition = arr.size() - 1;
                }
                if (myMusic.isPlaying()) {
                    myMusic.stop();
                    myMusic.reset();
                }
                Integer idSong = arr.get(currentPosition);
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    Integer Id = cursor.getInt(0);
                    String ten = cursor.getString(2);
                    byte[] img = cursor.getBlob(3);
                    String linkSong = cursor.getString(5);
                    if (idSong.equals(Id)) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                        imageView_songs.setImageBitmap(bitmap);
                        try {
                            myMusic.setDataSource(linkSong);
                            myMusic.prepare();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        txt_name_song.setText(ten);
                    }
                }
                cursor.close();
                myMusic.start();
                database = openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor1 = database.rawQuery("select * " +
                        "from Artists " +
                        "JOIN Songs ON Artists.ArtistID =Songs.ArtistID " +
                        "WHERE Songs.SongID = ? ", new String[]{String.valueOf(idSong)});
                while (cursor.moveToNext()) {
                    String ten = cursor1.getString(1);
                    txt_artist_song.setText(ten);
                    break;
                }
            }
        });

//        btn_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Khai báo Intent công khai để khởi động MyService_Music
//                Intent intent2 = new Intent(PlayMusicActivity.this, MyService_Music.class);
//                stopService(intent2);
//                // Nhấn vào Stop chuyển ảnh của play sang pause
//                btn_play.setImageResource(R.drawable.ic_play);
//                flag = true;
//            }
//        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMusic.isPlaying()) {
                    myMusic.stop();
                    myMusic.reset();
                }
                finish();
            }
        });

        // set giới hạn Max cho thanh seekBar
        seekBar.setMax(myMusic.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    myMusic.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (myMusic != null) {
                    if (myMusic.isPlaying()) {
                        try {
                            final double current = myMusic.getCurrentPosition();
                            final String time = timeSeekbar((int) current);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt_time_first.setText(time);
                                    seekBar.setProgress((int) current);
                                }
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }).start();
    }

    private void addControls() {
        btn_play = findViewById(R.id.btn_play);
        txtLoibaihat = findViewById(R.id.txtLoibaihat);
//        btn_pause = findViewById(R.id.btn_pause);
        btn_back = findViewById(R.id.btn_back);
        txt_artist_song = findViewById(R.id.txt_artist_song);
        txt_name_song = findViewById(R.id.txt_name_song);
        seekBar = findViewById(R.id.seekBar);
        imageView_songs = findViewById(R.id.imageView_songs);
        txt_time = findViewById(R.id.txt_time);
        txt_time_first = findViewById(R.id.txt_time_first);
        btn_pre = findViewById(R.id.btn_pre);
        btn_next = findViewById(R.id.btn_next);
    }
}