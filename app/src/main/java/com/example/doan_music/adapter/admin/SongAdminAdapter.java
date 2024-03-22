package com.example.doan_music.adapter.admin;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.doan_music.activity.admin.song.UpdateSongActivity;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Song;

import java.util.ArrayList;

public class SongAdminAdapter extends BaseAdapter {
    ArrayList<Song> arr;
    Activity context;
    ImageView img;
    View view;
    String songLink;
    TextView txt_id_song_admin, txt_name_song_admin, txt_idAlbum_song_admin, txt_idArtist_song_admin;
    Button btn_update, btn_delete;

    public SongAdminAdapter(Activity context, ArrayList<Song> arr) {
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
        view = LayoutInflater.from(context).inflate(R.layout.items_song_admin, parent, false);
        addControls();

        Song song = arr.get(position);
        // Hiển thị hình ảnh từ byte array
        byte[] hinhAlbumByteArray = song.getSongImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        img.setImageBitmap(bitmap);
        txt_id_song_admin.setText(song.getSongID() + "");
        txt_name_song_admin.setText(song.getSongName());
        txt_idArtist_song_admin.setText(song.getArtistID() + "");
        txt_idAlbum_song_admin.setText(song.getAlbumID() + "");
        songLink = song.getLinkSong();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateSongActivity.class);
                intent.putExtra("id", song.getSongID());

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
                        delete(song.getAlbumID());
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


    private void addControls() {
        btn_update = view.findViewById(R.id.btn_update_adminsong);
        btn_delete = view.findViewById(R.id.btn_delete_adminsong);

        img = view.findViewById(R.id.img_song_admin);

        txt_id_song_admin = view.findViewById(R.id.txt_id_song_admin);
        txt_name_song_admin = view.findViewById(R.id.txt_name_song_admin);
        txt_idArtist_song_admin = view.findViewById(R.id.txt_idArtist_song_admin);
        txt_idAlbum_song_admin = view.findViewById(R.id.txt_idAlbum_song_admin);
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

            //arr.add(new Album(id, ten, anh, idArtist));
        }
        notifyDataSetChanged();
    }
}
