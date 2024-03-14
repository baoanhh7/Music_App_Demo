package com.example.doan_music.fragment.tab_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doan_music.R;
import com.example.doan_music.adapter.home.SongAdapter;
import com.example.doan_music.model.Song;

public class Song_Fragment extends Fragment implements SongAdapter.OnHeartClickListener {

    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_song_, container, false);

        return mView;
    }

    @Override
    public void onHeartClicked(Song song) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_viewpager, new Song_Fragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}