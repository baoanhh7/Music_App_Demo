package com.example.doan_music.adapter.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.fragment.tab_home.Song_Fragment;
import com.example.doan_music.m_interface.IClickItemSong;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    // Khai báo một interface để gửi thông báo khi nhấn vào nút thả tim
    public interface OnHeartClickListener {
        void onHeartClicked(Song song);
    }

    private List<Song> songList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private IClickItemSong iClickItemSong;

    public SongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.txt_id.setText(song.getSongID() + "");
        holder.txt_song.setText(song.getSongName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(song.getSongImage(), 0, song.getSongImage().length);
        holder.img_song.setImageBitmap(bitmap);

        // Nhấn vào tên bài hát sẽ chuyển qua phát bài hát
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(songList.get(position).getSongName());
                }
            }
        });


        // Đặt trạng thái cho nút thả tim dựa trên isFavorite
        if (song.getisFavorite() == 1) {
            holder.btn_heart.setImageResource(R.drawable.ic_red_heart); // Đổi màu thành đỏ
        } else {
            holder.btn_heart.setImageResource(R.drawable.ic_heart); // Màu xám
        }

        holder.btn_heart.setOnClickListener(v -> {
            // Xử lý sự kiện khi nhấn vào nút thả tim
            int newState = song.getisFavorite() == 1 ? 0 : 1;
            song.setisFavorite(newState);
            notifyItemChanged(position); // Cập nhật lại giao diện

            Song_Fragment songFragment = new Song_Fragment();

            // Truyền dữ liệu của bài hát tới SongFragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("heartSong", song);
            songFragment.setArguments(bundle);

        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setIClickItemSong(IClickItemSong listener) {
        this.iClickItemSong = listener;
    }

    @Override
    public int getItemCount() {
        if (songList != null) return songList.size();
        return 0;
    }


    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView txt_song, txt_id;
        ImageButton btn_heart;
        ImageView img_song;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_song = itemView.findViewById(R.id.txt_song);
            txt_id = itemView.findViewById(R.id.txt_id);
            btn_heart = itemView.findViewById(R.id.btn_heart);
            img_song = itemView.findViewById(R.id.img_song);
        }
    }
}

