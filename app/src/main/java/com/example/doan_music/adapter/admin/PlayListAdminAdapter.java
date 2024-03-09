package com.example.doan_music.adapter.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan_music.R;
import com.example.doan_music.admin.playlist.AddPlayListActivity;
import com.example.doan_music.admin.playlist.PlayListAdminActivity;
import com.example.doan_music.admin.playlist.UpdatePlayListActivity;
import com.example.doan_music.model.Playlists;

import java.util.List;

public class PlayListAdminAdapter extends BaseAdapter {
    private Context context;
    private List<Playlists> list;

    public PlayListAdminAdapter(Context context, List<Playlists> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist_admin, parent, false);

        ImageView img_playlist_admin = view.findViewById(R.id.img_playlist_admin);
        TextView txt_id_playlist_admin = view.findViewById(R.id.txt_id_playlist_admin);
        TextView txt_name_playplist_admin = view.findViewById(R.id.txt_name_playplist_admin);

        Button btn_update = view.findViewById(R.id.btn_update);
        Button btn_delete = view.findViewById(R.id.btn_delete);

        Playlists playlists = list.get(position);
        txt_id_playlist_admin.setText(playlists.getPlaylistID() + "");
        txt_name_playplist_admin.setText(playlists.getPlaylistName());

        Bitmap bitmap = BitmapFactory.decodeByteArray(playlists.getPlaylistImage(), 0, playlists.getPlaylistImage().length);
        img_playlist_admin.setImageBitmap(bitmap);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatePlayListActivity.class);
                intent.putExtra("id",playlists.getPlaylistID());
                intent.putExtra("name",playlists.getPlaylistName());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
