package com.example.doan_music.adapter.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.doan_music.activity.admin.album.UpdateAlbumActivity;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Album;

import java.util.ArrayList;

public class AlbumAdminAdapter extends BaseAdapter {
    ArrayList<Album> arr;
    Context context;


    public AlbumAdminAdapter(Context context, ArrayList<Album> arr) {
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
        Button btn_update, btn_delete;
        btn_update = view.findViewById(R.id.btn_update_adminalbum);
        btn_delete = view.findViewById(R.id.btn_delete_adminalbum);
        img = view.findViewById(R.id.img_album_admin);
        txtTen = view.findViewById(R.id.txtTen_album_admin);
        txtID = view.findViewById(R.id.txtID_album_admin);
        txtIDArtist = view.findViewById(R.id.txtIDArtist_album_admin);
        Album album = arr.get(position);
        // Hiển thị hình ảnh từ byte array
        byte[] hinhAlbumByteArray = album.getAlbumImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        img.setImageBitmap(bitmap);
        txtID.setText(album.getAlbumID() + "");
        txtTen.setText(album.getAlbumName());
        txtIDArtist.setText(album.getArtistID() + "");
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateAlbumActivity.class);
                intent.putExtra("id", album.getAlbumID());
                intent.putExtra("name", album.getAlbumName());
                intent.putExtra("IDArtist", album.getArtistID());
                context.startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có muốn xóa không ?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(album.getAlbumID());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        return view;
    }

    private void delete(int ID) {
        DbHelper dbHelper = DatabaseManager.dbHelper(context);
        dbHelper.getWritableDatabase().delete("Albums", "AlbumID=?"
                , new String[]{ID + ""});
        arr.clear();
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery("select * from Albums", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] anh = cursor.getBlob(2);
            int idArtist = cursor.getInt(3);

            arr.add(new Album(id, ten, anh, idArtist));
        }
        notifyDataSetChanged();
    }


}

