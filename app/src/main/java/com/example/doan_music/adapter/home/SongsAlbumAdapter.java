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

public class SongsAlbumAdapter extends RecyclerView.Adapter<SongsAlbumAdapter.UserViewHolder> {

    List<User> list;

    public void setData(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songs_album, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = list.get(position);

        holder.img_sList.setImageResource(user.getResourceImage());
        holder.txt_sList.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView img_sList;
        TextView txt_sList;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            img_sList = itemView.findViewById(R.id.img_songlist);
            txt_sList = itemView.findViewById(R.id.txt_songlist);
        }
    }
}
