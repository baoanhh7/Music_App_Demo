package com.example.doan_music.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.spotify.SpotifyAdapter;
import com.example.doan_music.model.Spotifyitem;

import java.util.ArrayList;

public class Spotify_Fragment extends Fragment {
    RecyclerView recyclerViewSpot;
    SpotifyAdapter spotifyAdapter;
    ArrayList<Spotifyitem> arr_spot;
    private RecyclerView premiumscrollview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spotify_, container, false);

        // addControls();
        // loadData();
    }

    private void addControls() {
        recyclerViewSpot = getView().findViewById(R.id.recyclerSpotify);

    }

    private void loadData() {
        arr_spot.add(new Spotifyitem("Individual", "1 tài khoản premium", "Hủy bất cứ lúc nào"));
        arr_spot.add(new Spotifyitem("Student", "1 tài khoản premium", "Giảm giá cho sinh viên đủ điều kiện"));
    }

}