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
import com.example.doan_music.activity.admin.playlist.UpdatePlayListActivity;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.model.Playlists;

import java.util.List;

public class PlayListAdminAdapter extends BaseAdapter {
    ImageView img_playlist_admin;
    TextView txt_id_playlist_admin, txt_name_playplist_admin;
    Button btn_update, btn_delete;
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

        img_playlist_admin = view.findViewById(R.id.img_playlist_admin);
        txt_id_playlist_admin = view.findViewById(R.id.txt_id_playlist_admin);
        txt_name_playplist_admin = view.findViewById(R.id.txt_name_playplist_admin);

        btn_update = view.findViewById(R.id.btn_update);
        btn_delete = view.findViewById(R.id.btn_delete);

        // add dữ liệu từ db vào list
        Playlists playlists = list.get(position);

        txt_id_playlist_admin.setText(playlists.getPlaylistID() + "");
        txt_name_playplist_admin.setText(playlists.getPlaylistName());

        Bitmap bitmap = BitmapFactory.decodeByteArray(playlists.getPlaylistImage(), 0, playlists.getPlaylistImage().length);
        img_playlist_admin.setImageBitmap(bitmap);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatePlayListActivity.class);
                intent.putExtra("id", playlists.getPlaylistID());
                intent.putExtra("name", playlists.getPlaylistName());

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
                        delete(playlists.getPlaylistID());
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


    private void delete(int playlistID) {
        DbHelper dbHelper = DatabaseManager.dbHelper(context);
        dbHelper.getWritableDatabase().delete("Playlists", "PlaylistID=?"
                , new String[]{playlistID + ""});
        list.clear();
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery("select * from Playlists", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] anh = cursor.getBlob(2);

            list.add(new Playlists(id, ten, anh));
        }
        notifyDataSetChanged();
    }
}
