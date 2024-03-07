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
import com.example.doan_music.model.Category;

public class PlayListActivity extends AppCompatActivity {
    RecyclerView rcv_playlist;
    PlayListAdapter playListAdapter;
    Button btn_back;
    TextView txt_playlist;
    Category category;

    Intent i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        addControls();
        addEvents();

        playListAdapter.setData(category.getList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_playlist.setLayoutManager(linearLayoutManager);

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

        i = getIntent();
        category = (Category) i.getSerializableExtra("c");
        txt_playlist.setText(category.getName());

    }
}