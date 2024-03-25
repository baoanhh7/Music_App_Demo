package com.example.doan_music.adapter.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
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

        if (song.getIsFavorite() == 1) {
            holder.btn_heartFav.setImageResource(R.drawable.ic_red_heart);
        } else {
            holder.btn_heartFav.setImageResource(R.drawable.ic_heart);
        }

        holder.btn_heartFav.setOnClickListener(v -> {

            DbHelper dbHelper = DatabaseManager.dbHelper(context);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from Songs where SongID=?", new String[]{song.getSongID() + ""});

            int newState = song.getIsFavorite();

            if (newState == 1) {
                newState = 0;
                song.setIsFavorite(newState);

                removeSongFromLoveList(song.getSongID());
            } else {
                newState = 1;
                song.setIsFavorite(newState);

                ContentValues values = new ContentValues();
                values.put("StateFavorite", song.getIsFavorite());
                database.update("Songs", values, "SongID=?", new String[]{song.getSongID() + ""});

                addSongToLoveList(song.getSongID());
            }
            song.setIsFavorite(newState);

            notifyItemChanged(position); // Cập nhật lại giao diện
            cursor.close();
        });

    }

    private void addSongToLoveList(int songID) {
        SharedPreferences preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        int maU = preferences.getInt("maU1", -1);

        DbHelper dbHelper = DatabaseManager.dbHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        try {
            // Lặp qua các bản ghi trong bảng Songs
            Cursor cursor = database.rawQuery("SELECT * FROM Songs" + " where SongID=?", new String[]{String.valueOf(songID)});
            while (cursor.moveToNext()) {
                int favorite = cursor.getInt(6);

                // Xây dựng ContentValues mới
                ContentValues values = new ContentValues();

                // Đặt giá trị cho cột Favorite
                values.put("Favorite", favorite);
                // Đặt giá trị cho cột UserID
                values.put("UserID", maU);
                // Đặt giá trị cho cột SongID
                values.put("SongID", songID);

                // Thực hiện thêm dòng vào bảng User_SongLove
                long kq = database.insert("User_SongLove", null, values);
                if (kq > 0) {
                    break;
                }
            }
            cursor.close();

            // Đánh dấu giao dịch thành công nếu không có lỗi xảy ra
            database.setTransactionSuccessful();
        } finally {
            // Kết thúc giao dịch, đảm bảo rằng dữ liệu được lưu trữ an toàn
            database.endTransaction();
        }
    }

    private void removeSongFromLoveList(int songID) {
        SharedPreferences preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        int maU = preferences.getInt("maU1", -1);

        DbHelper dbHelper = DatabaseManager.dbHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        try {
            // Xóa bài hát khỏi bảng User_SongLove dựa trên UserID và SongID
            int kq = database.delete("User_SongLove", "UserID = ? AND SongID = ?", new String[]{String.valueOf(maU), String.valueOf(songID)});

            if (kq > 0) {
                // Cập nhật trạng thái yêu thích của bài hát trong bảng Songs
                ContentValues values = new ContentValues();
                values.put("StateFavorite", 0);

                // Cập nhật bảng Songs dựa trên SongID
                database.update("Songs", values, "SongID = ?", new String[]{String.valueOf(songID)});
            }

            // Đánh dấu giao dịch thành công nếu không có lỗi xảy ra
            database.setTransactionSuccessful();
        } finally {
            // Kết thúc giao dịch, đảm bảo rằng dữ liệu được lưu trữ an toàn
            database.endTransaction();
        }
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

