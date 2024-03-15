package com.example.doan_music.fragment.tab_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.doan_music.R;
import com.example.doan_music.model.Song;

import java.util.ArrayList;
import java.util.List;

public class Song_Fragment extends Fragment {
    TextView txt_songname;

    ListView lv_lovesong;
    List<Song> songList;
    ArrayAdapter adapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_song_, container, false);
        addControls();

        // Lấy dữ liệu bài hát từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            Song song = (Song) bundle.getSerializable("heartSong");
            // Hiển thị thông tin bài hát trong Fragment
            // Ví dụ: update UI với thông tin bài hát
            txt_songname.setText(song.getSongName());

            songList.add(song);
            adapter.notifyDataSetChanged();
        }
        return view;
    }

    private void addControls() {
        txt_songname = view.findViewById(R.id.txt_songname);
        lv_lovesong = view.findViewById(R.id.lv_lovesong);

        songList = new ArrayList<>();

        adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, songList);

        lv_lovesong.setAdapter(adapter);
    }
}