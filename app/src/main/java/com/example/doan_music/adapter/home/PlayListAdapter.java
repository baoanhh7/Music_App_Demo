package com.example.doan_music.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.model.User;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    List<User> list;

    public void setData(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {

        User user = list.get(position);
        holder.img_playlist.setImageResource(user.getResourceImage());
        holder.txt_playlist.setText(user.getName());

    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        ImageView img_playlist;
        TextView txt_playlist;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);

            img_playlist = itemView.findViewById(R.id.img_playlist);
            txt_playlist = itemView.findViewById(R.id.txt_playlist);
        }
    }
}
