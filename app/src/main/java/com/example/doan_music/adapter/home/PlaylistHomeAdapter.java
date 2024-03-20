package com.example.doan_music.adapter.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.model.Playlists;

import java.util.List;

public class PlaylistHomeAdapter extends RecyclerView.Adapter<PlaylistHomeAdapter.PlaylistViewHolder> {
    List<Playlists> list;

    public void setData(List<Playlists> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_bottom, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlists playlists = list.get(position);
        holder.txt_home_bottom.setText(playlists.getPlaylistName());

        Bitmap bitmap = BitmapFactory.decodeByteArray(playlists.getPlaylistImage(), 0, playlists.getPlaylistImage().length);
        holder.img_home_bottom.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        ImageView img_home_bottom;
        TextView txt_home_bottom;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);

            img_home_bottom = itemView.findViewById(R.id.img_home_bottom);
            txt_home_bottom = itemView.findViewById(R.id.txt_home_bottom);
        }
    }
}
