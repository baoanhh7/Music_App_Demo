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
import com.example.doan_music.activity.admin.artist.UpdateArtistActivity;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Artists;

import java.util.ArrayList;

public class ArtistAdminAdapter extends BaseAdapter {
    ArrayList<Artists> arr;
    Context context;


    public ArtistAdminAdapter(Context context, ArrayList<Artists> arr) {
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
        View view = layoutInflater.inflate(R.layout.item_artist_admin, parent, false);
        ImageView img;
        TextView txtID, txtTen, txtIDArtist;
        Button btn_update, btn_delete;
        btn_update = view.findViewById(R.id.btn_update_artistalbum);
        btn_delete = view.findViewById(R.id.btn_delete_artistalbum);
        img = view.findViewById(R.id.img_artist_admin);
        txtTen = view.findViewById(R.id.txtTen_artist_admin);
        txtID = view.findViewById(R.id.txtID_artist_admin);
        Artists artists = arr.get(position);
        // Hiển thị hình ảnh từ byte array
        byte[] hinhAlbumByteArray = artists.getArtistImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        img.setImageBitmap(bitmap);
        txtID.setText(artists.getArtistID() + "");
        txtTen.setText(artists.getArtistName());
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateArtistActivity.class);
                intent.putExtra("id", artists.getArtistID());
                intent.putExtra("name", artists.getArtistName());
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
                        delete(artists.getArtistID());
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
        dbHelper.getWritableDatabase().delete("Artists", "ArtistID=?"
                , new String[]{ID + ""});
        arr.clear();
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery("select * from Artists", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] anh = cursor.getBlob(2);

            arr.add(new Artists(id, ten, anh));
        }
        notifyDataSetChanged();
    }
}
