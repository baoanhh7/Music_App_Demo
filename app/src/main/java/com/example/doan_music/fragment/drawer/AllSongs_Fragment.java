package com.example.doan_music.fragment.drawer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.home.SongAdapter;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Song;
import com.example.doan_music.music.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;

public class AllSongs_Fragment extends Fragment {
    View view;
    RecyclerView rcv_songs;
    SongAdapter songAdapter;
    DbHelper dbHelper;
    SQLiteDatabase database = null;
    List<Song> songList;
    ArrayList<Integer> arr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_allsongs, container, false);

        addControls();
        createData();

        songAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                dbHelper = new DbHelper(requireContext());
                database = requireContext().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String songName = cursor.getString(2);

                    if (data.equals(songName)) {
                        Intent intent = new Intent(requireContext(), PlayMusicActivity.class);
                        intent.putExtra("SongID", id);
                        intent.putExtra("arrIDSongs", arr);

                        startActivity(intent);
                        break;
                    }
                }
                cursor.close();
            }
        });

//        songAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(String data) {
//                if (getActivity() instanceof MainActivity) {
//                    MainActivity mainActivity = (MainActivity) getActivity();
//                    Integer maU = mainActivity.getMyVariable();
//                    database = getActivity().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
//                    Cursor cursor = database.rawQuery("select * from Songs", null);
//                    while (cursor.moveToNext()) {
//                        String ten = cursor.getString(2);
//                        if (data.equals(ten)) {
//                            ContentValues values = new ContentValues();
//                            values.put("User_SongLove.UserID", maU);
//                            values.put("User_SongLove.SongLoveName", ten);
//                            dbHelper = DatabaseManager.dbHelper(requireContext());
//                            long kq = dbHelper.getReadableDatabase().insert("User_SongLove", null, values);
//                            if (kq > 0) {
//                                // Thêm dữ liệu mới vào Adapter
//                                Toast.makeText(requireContext(), "Yêu thích thành công", Toast.LENGTH_LONG).show();
//                                break;
//                            }
//                        }
//                    }
//                    cursor.close();
//                }
//            }
//        });

        return view;
    }

    private void createData() {
        dbHelper = new DbHelper(requireContext());
        database = requireContext().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Songs", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String songName = cursor.getString(2);
            int artist = cursor.getInt(4);
            byte[] image = cursor.getBlob(3);
            String linkSong = cursor.getString(5);
            int favorite = cursor.getInt(6);

            Song song = new Song(id, songName, artist, image, linkSong, favorite);
            songList.add(song);
            arr.add(id);
        }
        songAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addControls() {
        rcv_songs = view.findViewById(R.id.rcv_songs);
        songList = new ArrayList<>();

        songAdapter = new SongAdapter(requireContext(), songList);
        rcv_songs.setAdapter(songAdapter);

        rcv_songs.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}