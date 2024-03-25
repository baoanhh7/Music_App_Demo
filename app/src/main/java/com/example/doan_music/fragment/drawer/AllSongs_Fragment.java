package com.example.doan_music.fragment.drawer;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.activity.MainActivity;
import com.example.doan_music.adapter.home.SongAdapter;
import com.example.doan_music.data.DatabaseManager;
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
    SearchView allSong_searchView;
    DbHelper dbHelper;
    SQLiteDatabase database = null;
    List<Song> songList, filterSongList;
    ArrayList<Integer> arr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_allsongs, container, false);

        addControls();
        addEvents();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        createData();
    }

    private void addEvents() {
        allSong_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Lọc dữ liệu khi người dùng nhập văn bản vào SearchView
                filter(newText);
                return false;
            }
        });

        songAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                dbHelper = new DbHelper(requireContext());
                database = requireContext().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Songs", null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String songName = cursor.getString(2);
                    int view = cursor.getInt(8);

                    if (data.equals(songName)) {

                        view++;
                        ContentValues values = new ContentValues();
                        values.put("View", view);
                        database.update("Songs", values, "SongID=?", new String[]{String.valueOf(id)});

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
    }

    // Hàm lọc dữ liệu
    private void filter(String text) {
        filterSongList.clear();
        for (Song song : songList) {
            if (song.getSongName().toLowerCase().contains(text.toLowerCase())) {
                filterSongList.add(song);
            }
        }
        songAdapter.filterList(filterSongList); // Cập nhật dữ liệu đã lọc cho Adapter
    }

    private void createData() {

        MainActivity mainActivity = (MainActivity) getActivity();
        int maU = mainActivity.getMyVariable();

        dbHelper = DatabaseManager.dbHelper(requireContext());
        database = dbHelper.getReadableDatabase();

        List<Integer> listFav = new ArrayList<>();
        listFav.clear();

        Cursor cursor1 = database.rawQuery("select * from User_SongLove where UserID=?", new String[]{maU + ""});
        while (cursor1.moveToNext()) {
            int songID = cursor1.getInt(2);
            listFav.add(songID);
        }
        songAdapter.notifyDataSetChanged();
        cursor1.close();


        Cursor cursor = database.rawQuery("select * from Songs", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String songName = cursor.getString(2);
            int artist = cursor.getInt(4);
            byte[] image = cursor.getBlob(3);
            String linkSong = cursor.getString(5);

            int fav = 0;

            if (listFav.contains(id)) {
                fav = 1;
            }
            Song song = new Song(id, songName, artist, image, linkSong, fav);

            songList.add(song);
            arr.add(id);
        }
        songAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addControls() {
        rcv_songs = view.findViewById(R.id.rcv_songs);
        allSong_searchView = view.findViewById(R.id.allSong_searchView);

        // đổi màu editText
        EditText editText = allSong_searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setHintTextColor(Color.WHITE);

        songList = new ArrayList<>();
        filterSongList = new ArrayList<>();

        songAdapter = new SongAdapter(requireContext(), songList);
        rcv_songs.setAdapter(songAdapter);

        rcv_songs.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}