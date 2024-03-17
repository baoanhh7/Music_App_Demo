package com.example.doan_music.adapter.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Song;

import java.util.List;

public class FavoriteSongAdapter extends RecyclerView.Adapter<FavoriteSongAdapter.SongViewHolder> {

    private List<Song> songList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public FavoriteSongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fav_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.txt_nameFav.setText(song.getSongName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(song.getSongImage(), 0, song.getSongImage().length);
        holder.img_songFav.setImageBitmap(bitmap);

        // Nhấn vào tên bài hát sẽ chuyển qua phát bài hát
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(songList.get(position).getSongName());
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        if (songList != null) return songList.size();
        return 0;
    }


    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nameFav, txt_artistFav;
        ImageView img_songFav;
        ImageButton btn_heartFav;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameFav = itemView.findViewById(R.id.txt_nameFav);
            txt_artistFav = itemView.findViewById(R.id.txt_artistFav);
            img_songFav = itemView.findViewById(R.id.img_songFav);
            btn_heartFav = itemView.findViewById(R.id.btn_heartFav);
        }
    }
}

