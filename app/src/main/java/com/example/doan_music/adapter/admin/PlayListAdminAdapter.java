package com.example.doan_music.adapter.admin;

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

public class PlayListAdminAdapter extends RecyclerView.Adapter<PlayListAdminAdapter.PlayListViewHolder> {
    private List<Playlists> list;

    public void setData(List<Playlists> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_admin, parent, false);

        return new PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        Playlists playlist = list.get(position);

        holder.txt_id_playlist_admin.setText(playlist.getPlaylistID() + "");
        holder.txt_name_playplist_admin.setText(playlist.getPlaylistName());

        Bitmap bitmap = BitmapFactory.decodeByteArray(playlist.getPlaylistImage(), 0, playlist.getPlaylistImage().length);
        holder.img_playlist_admin.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        ImageView img_playlist_admin;
        TextView txt_id_playlist_admin, txt_name_playplist_admin;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            img_playlist_admin = itemView.findViewById(R.id.img_playlist_admin);
            txt_id_playlist_admin = itemView.findViewById(R.id.txt_id_playlist_admin);
            txt_name_playplist_admin = itemView.findViewById(R.id.txt_name_playplist_admin);
        }
    }
}
