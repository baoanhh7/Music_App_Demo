package com.example.doan_music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.home.PlayListAdapter;
import com.example.doan_music.model.User;

import java.util.ArrayList;
import java.util.List;

public class PlayListActivity extends AppCompatActivity {
    RecyclerView rcv_playlist;
    PlayListAdapter playListAdapter;
    Button btn_back;
    TextView txt_playlist;

    Intent i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        addControls();
        addEvents();

        playListAdapter.setData(getList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_playlist.setLayoutManager(linearLayoutManager);

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

    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        rcv_playlist = findViewById(R.id.rcv_playlist);
        playListAdapter = new PlayListAdapter();
        rcv_playlist.setAdapter(playListAdapter);

        txt_playlist = findViewById(R.id.txt_playlist);
        btn_back = findViewById(R.id.btn_back);

//        lỗi
//        i = getIntent();
//        Category category = (Category) i.getSerializableExtra("c");
//        txt_playlist.setText(category.getName());
//
//        songListAdapter.setData(category.getList());
//        rcv_songlist.setAdapter(songListAdapter);
    }
}