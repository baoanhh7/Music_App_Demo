package com.example.doan_music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doan_music.adapter.home.SongListAdapter;
import com.example.doan_music.model.User;

import java.util.ArrayList;
import java.util.List;

public class SongListActivity extends AppCompatActivity {

    RecyclerView rcv_songlist;
    SongListAdapter songListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        addControls();

        songListAdapter.setData(getList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcv_songlist.setLayoutManager(linearLayoutManager);

    }

    private List<User> getList() {
        List<User> list = new ArrayList<>();
        list.add(new User(R.drawable.avt_vu,"Radio.",true));
        list.add(new User(R.drawable.avt_vu,"Radio.",true));
        list.add(new User(R.drawable.avt_vu,"Radio.",true));
        list.add(new User(R.drawable.avt_vu,"Radio.",true));
        list.add(new User(R.drawable.avt_vu,"Radio.",true));
        list.add(new User(R.drawable.avt_vu,"Radio.",true));

        return list;
    }

    private void addControls() {
        rcv_songlist = findViewById(R.id.rcv_songlist);
        songListAdapter = new SongListAdapter();
        rcv_songlist.setAdapter(songListAdapter);
    }
}