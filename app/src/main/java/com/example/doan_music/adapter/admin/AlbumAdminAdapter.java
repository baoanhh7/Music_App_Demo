package com.example.doan_music.adapter.admin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan_music.R;
import com.example.doan_music.model.Ablum;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdminAdapter extends BaseAdapter {
    ArrayList<Ablum> arr;
    Context context;

    public AlbumAdminAdapter(Context context, ArrayList<Ablum> arr) {
        this.context = context;
        this.arr = arr;
    }


    @Override
    public int getCount() {
        return arr.size();
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.items_album_admin, parent, false);
        ImageView img;
        TextView txtID, txtTen, txtIDArtist;
        img = view.findViewById(R.id.img_album_admin);
        txtTen = view.findViewById(R.id.txtTen_album_admin);
        txtID = view.findViewById(R.id.txtID_album_admin);
        txtIDArtist = view.findViewById(R.id.txtIDArtist_album_admin);
        Ablum album = arr.get(position);
        // Hiển thị hình ảnh từ byte array
        byte[] hinhAlbumByteArray = album.getAlbumImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        img.setImageBitmap(bitmap);
        txtID.setText(album.getAlbumID()+"");
        txtTen.setText(album.getAlbumName());
        txtIDArtist.setText(album.getArtistID()+"");
        return view;
    }


}

