package com.example.doan_music.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.home.SongsAlbumAdapter;
import com.example.doan_music.model.User;

import java.util.ArrayList;
import java.util.List;

public class SongsAlbumActivity extends AppCompatActivity {

    RecyclerView rcv_songlist;
    SongsAlbumAdapter songListAdapter;
    ImageButton btn_back, btn_play;

    ImageView img_songlist;
    TextView txt_songlist;
    Boolean flag = true;

    Intent i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_album);

        addControls();
        addEvents();

        songListAdapter.setData(getList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_songlist.setLayoutManager(linearLayoutManager);
    }

    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    btn_play.setImageResource(R.drawable.ic_pause);
                    flag = false;
                } else {
                    btn_play.setImageResource(R.drawable.ic_play);
                    flag = true;
                }
            }
        });
    }

    private List<User> getList() {
        List<User> list = new ArrayList<>();
        list.add(new User(R.drawable.avt_vu, "Những lời hứa bỏ quên - Vũ Radio."));
        list.add(new User(R.drawable.avt_vu, "Những lời hứa bỏ quên - Vũ Radio."));
        list.add(new User(R.drawable.avt_vu, "Những lời hứa bỏ quên - Vũ Radio."));
        list.add(new User(R.drawable.avt_vu, "Những lời hứa bỏ quên - Vũ Radio."));
        list.add(new User(R.drawable.avt_vu, "Những lời hứa bỏ quên - Vũ Radio."));
        list.add(new User(R.drawable.avt_vu, "Những lời hứa bỏ quên - Vũ Radio."));

        return list;
    }

    private void addControls() {
        rcv_songlist = findViewById(R.id.rcv_songlist);
        songListAdapter = new SongsAlbumAdapter();
        rcv_songlist.setAdapter(songListAdapter);

        img_songlist = findViewById(R.id.img_songlist);
        txt_songlist = findViewById(R.id.txt_songlist);

        btn_back = findViewById(R.id.btn_back);
        btn_play = findViewById(R.id.btn_play);

        //get dữ liệu từ All_Fragment
        i = getIntent();
        User user = (User) i.getSerializableExtra("u");
        img_songlist.setImageResource(user.getResourceImage());
        txt_songlist.setText(user.getName());
    }
}