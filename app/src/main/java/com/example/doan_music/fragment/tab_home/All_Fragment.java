package com.example.doan_music.fragment.tab_home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.activity.home.PlayListActivity;
import com.example.doan_music.activity.home.SongsAlbumActivity;
import com.example.doan_music.adapter.home.CategoryAdapter;
import com.example.doan_music.adapter.home.HomeAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.m_interface.IClickItemCategory;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.Album;
import com.example.doan_music.model.Category;
import com.example.doan_music.model.Playlists;
import com.example.doan_music.model.Song;

import java.util.ArrayList;
import java.util.List;

public class All_Fragment extends Fragment {
    private RecyclerView rcv_all_header, rcv_all_bottom;
    private HomeAdapter allAdapter_header;
    private CategoryAdapter allCateAdapter_bottom;
    DbHelper dbHelper;
    SQLiteDatabase database = null;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_, container, false);

        addControls();

        // set layout của recyclerView thành 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcv_all_header.setLayoutManager(gridLayoutManager);

        // set layout của recyclerView theo hướng ngang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcv_all_bottom.setLayoutManager(linearLayoutManager);

        // set data cho recyclerView
        allCateAdapter_bottom.setData(getlistuserBottom());

        addEvents();

        return view;
    }

    private void addEvents() {
        allAdapter_header.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                dbHelper = DatabaseManager.dbHelper(requireContext());
                database = dbHelper.getReadableDatabase();

                Cursor cursor = database.rawQuery("select * from Albums", null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    if (name.equals(data)) {
                        Intent intent = new Intent(requireContext(), SongsAlbumActivity.class);
                        intent.putExtra("albumID", id);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    @NonNull
    private List<Album> getlistuserHeader() {
        List<Album> list = new ArrayList<>();

        dbHelper = DatabaseManager.dbHelper(requireContext());
        database = dbHelper.getReadableDatabase();

        list.clear();
        Cursor cursor = database.rawQuery("select * from Albums", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] anh = cursor.getBlob(2);
            int idArtist = cursor.getInt(3);

            list.add(new Album(id, ten, anh, idArtist));
        }
        cursor.close();
        database.close();

        return list;
    }

    @NonNull
    private List<Category> getlistuserBottom() {

        // list 0
        List<Playlists> list = new ArrayList<>();

        dbHelper = DatabaseManager.dbHelper(requireContext());
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("Select * from Playlists", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] img = cursor.getBlob(2);

            Playlists playlists = new Playlists(id, name, img);
            list.add(playlists);
        }
        cursor.close();
        allCateAdapter_bottom.notifyDataSetChanged();

        // list 1
        dbHelper = new DbHelper(requireContext());
        database = dbHelper.getReadableDatabase();
        Cursor cursor1 = database.rawQuery("select * from Songs", null);

        ArrayList<Integer> arr = new ArrayList<>();
        List<Song> songList = new ArrayList<>();
//        songList.clear();

        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String songName = cursor1.getString(2);
            byte[] image = cursor1.getBlob(3);
            String linkSong = cursor1.getString(5);
            int favorite = cursor1.getInt(6);

            if (favorite == 1) {
                Song song = new Song(id, songName, image, linkSong, favorite);
                songList.add(song);
                arr.add(id);
            }
        }
        cursor1.close();
        allCateAdapter_bottom.notifyDataSetChanged();

        // list 2
        dbHelper = new DbHelper(requireContext());
        database = requireContext().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor2 = database.rawQuery("select * from Songs", null);

        List<Song> allSong = new ArrayList<>();

        while (cursor2.moveToNext()) {
            int id = cursor2.getInt(0);
            String songName = cursor2.getString(2);
            int artist = cursor2.getInt(4);
            byte[] image = cursor2.getBlob(3);
            String linkSong = cursor2.getString(5);
            int favorite = cursor2.getInt(6);

            Song song = new Song(id, songName, artist, image, linkSong, favorite);
            allSong.add(song);
            arr.add(id);
        }
        allCateAdapter_bottom.notifyDataSetChanged();
        cursor2.close();

        // list 3
        dbHelper = new DbHelper(requireContext());
        database = requireContext().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor3 = database.rawQuery("select * from Songs order by 8 DESC", null);

        List<Song> hotSong = new ArrayList<>();

        while (cursor3.moveToNext()) {
            int id = cursor3.getInt(0);
            String songName = cursor3.getString(2);
            int artist = cursor3.getInt(4);
            byte[] image = cursor3.getBlob(3);
            String linkSong = cursor3.getString(5);
            int favorite = cursor3.getInt(6);

            Song song = new Song(id, songName, artist, image, linkSong, favorite);
            allSong.add(song);
            arr.add(id);
        }
        allCateAdapter_bottom.notifyDataSetChanged();
        cursor3.close();

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Danh sách phát dành cho bạn", list, null));
        categoryList.add(new Category("Bài hát được yêu thích", null, songList));
        categoryList.add(new Category("Lựa chọn của Spotify", null, allSong));
        categoryList.add(new Category("Những bài hát nhiều người nghe", null, hotSong));

        return categoryList;
    }

    private void addControls() {
        rcv_all_header = view.findViewById(R.id.rcv_all_header);
        rcv_all_bottom = view.findViewById(R.id.rcv_all_bottom);

        allAdapter_header = new HomeAdapter(requireContext(), getlistuserHeader());

        allCateAdapter_bottom = new CategoryAdapter(new IClickItemCategory() {
            @Override
            public void onClickItemCategory(Category category) {
                Intent i = new Intent(requireContext(), PlayListActivity.class);
                i.putExtra("c", category.getName());
                startActivity(i);
            }
        });

        rcv_all_header.setAdapter(allAdapter_header);
        rcv_all_bottom.setAdapter(allCateAdapter_bottom);
    }
}