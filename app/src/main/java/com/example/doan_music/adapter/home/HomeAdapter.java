package com.example.doan_music.adapter.home;

import android.content.Context;
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
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Album;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.UserHeaderViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<Album> listAlbum;
    private Context context;

    public HomeAdapter(Context context, List<Album> songList) {
        this.context = context;
        this.listAlbum = songList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public UserHeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_header, parent, false);
        return new UserHeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHeaderViewHolder holder, int position) {
        Album album = listAlbum.get(position);

        holder.txt_home_header.setText(album.getAlbumName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(album.getAlbumImage(), 0, album.getAlbumImage().length);
        holder.img_home_album.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(listAlbum.get(position).getAlbumName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listAlbum != null) return listAlbum.size();
        return 0;
    }

    public class UserHeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_home_album;
        private TextView txt_home_header;

        public UserHeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            img_home_album = itemView.findViewById(R.id.img_home_album);
            txt_home_header = itemView.findViewById(R.id.txt_home_header);

        }
    }
}
