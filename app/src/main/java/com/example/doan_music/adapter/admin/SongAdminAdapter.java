package com.example.doan_music.adapter.admin;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.doan_music.R;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Song;

import java.util.ArrayList;

public class SongAdminAdapter extends BaseAdapter {
    ArrayList<Song> arr;
    Activity context;

    ImageView img;
    SeekBar sbTime;
    View view;
    String Linknhac;
    MediaPlayer musicPlayer;
    TextView txtID, txtTen, txtIDArtist, txtIDAlbum;
    Button btn_update, btn_delete;
    ImageButton btn_play;

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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.items_song_admin, parent, false);
        addControls();
        Song song = arr.get(position);
        // Hiển thị hình ảnh từ byte array
        byte[] hinhAlbumByteArray = song.getSongImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        img.setImageBitmap(bitmap);
        txtID.setText(song.getSongID() + "");
        txtTen.setText(song.getSongName());
        txtIDArtist.setText(song.getArtistID() + "");
        txtIDAlbum.setText(song.getAlbumID() + "");
        Linknhac = song.getLinkSong();
//        btn_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UpdateAlbumActivity.class);
//                intent.putExtra("id", album.getAlbumID());
//                intent.putExtra("name", album.getAlbumName());
//                intent.putExtra("Image", album.getAlbumImage());
//                intent.putExtra("IDArtist", album.getArtistID());
//                context.startActivity(intent);
//            }
//        });
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Xác nhận xóa");
//                builder.setMessage("Bạn có muốn xóa không ?");
//
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        delete(album.getAlbumID());
//                    }
//                });
//                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();
//            }
//        });
        return view;
    }


    private void addControls() {
        btn_update = view.findViewById(R.id.btn_update_adminsong);
        btn_delete = view.findViewById(R.id.btn_delete_adminsong);
        img = view.findViewById(R.id.img_song_admin);
        txtTen = view.findViewById(R.id.txtTen_song_admin);
        txtID = view.findViewById(R.id.txtID_song_admin);
        txtIDArtist = view.findViewById(R.id.txtIDArtist_song_admin);
        txtIDAlbum = view.findViewById(R.id.txtIDAlbum_song_admin);
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
