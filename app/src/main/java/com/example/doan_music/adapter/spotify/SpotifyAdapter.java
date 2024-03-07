package com.example.doan_music.adapter.spotify;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.model.Spotifyitem;

import java.util.ArrayList;

public class SpotifyAdapter extends RecyclerView.Adapter<SpotifyAdapter.ViewHolder> {
    Activity context;
    ArrayList<Spotifyitem> arr_spotify;

    public SpotifyAdapter(Activity context, ArrayList<Spotifyitem> arr_spotify) {
        this.context = context;
        this.arr_spotify = arr_spotify;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewSpotify = layoutInflater.inflate(R.layout.items_spotify, parent, false);
        ViewHolder viewHolderSpot = new ViewHolder(viewSpotify);
        return viewHolderSpot;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Spotifyitem spot = arr_spotify.get(position);
        holder.txtName_spotify.setText(spot.getName());
        holder.txtinfo_spotify_1.setText(spot.getInfo_1());
        holder.txtinfo_spotify_2.setText(spot.getInfo_2());

    }

    @Override
    public int getItemCount() {
        return arr_spotify.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName_spotify, txtinfo_spotify_1, txtinfo_spotify_2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName_spotify = itemView.findViewById(R.id.txtName_spotify);
            txtinfo_spotify_1 = itemView.findViewById(R.id.txtinfo_spotify_1);
            txtinfo_spotify_2 = itemView.findViewById(R.id.txtinfo_spotify_2);
        }
    }
}
