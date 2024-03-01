package com.example.doan_music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan_music.adapter.home.SongListAdapter;
import com.example.doan_music.model.User;

import java.util.ArrayList;
import java.util.List;

public class SongListActivity extends AppCompatActivity {

    RecyclerView rcv_songlist;
    SongListAdapter songListAdapter;
    ImageButton btn_back;

    ImageView img_songlist;
    TextView txt_songlist;

    Intent i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

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
    }

    private List<User> getList() {
        List<User> list = new ArrayList<>();
        list.add(new User(R.drawable.avt_vu, "Radio.", true));
        list.add(new User(R.drawable.avt_vu, "Radio.", true));
        list.add(new User(R.drawable.avt_vu, "Radio.", true));
        list.add(new User(R.drawable.avt_vu, "Radio.", true));
        list.add(new User(R.drawable.avt_vu, "Radio.", true));
        list.add(new User(R.drawable.avt_vu, "Radio.", true));

        return list;
    }

    private void addControls() {
        rcv_songlist = findViewById(R.id.rcv_songlist);
        songListAdapter = new SongListAdapter();
        rcv_songlist.setAdapter(songListAdapter);

        img_songlist = findViewById(R.id.img_songlist);
        txt_songlist = findViewById(R.id.txt_songlist);

        btn_back = findViewById(R.id.btn_back);

        //get dữ liệu từ All_Fragment
        i = getIntent();
        User user = (User) i.getSerializableExtra("u");
        img_songlist.setImageResource(user.getResourceImage());
        txt_songlist.setText(user.getName());


    }
}