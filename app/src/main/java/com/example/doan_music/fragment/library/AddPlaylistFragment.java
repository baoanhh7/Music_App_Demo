package com.example.doan_music.fragment.library;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.activity.MainActivity;
import com.example.doan_music.adapter.thuvien.AddNgheSiAdapter;
import com.example.doan_music.data.DatabaseManager;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.fragment.main.Library_Fragment;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.AddNgheSi_ThuVien;

import java.util.ArrayList;


public class AddPlaylistFragment extends Fragment {

    SearchView search_thuvien_addPlaylist;
    Button btn_xong_addplaylist_thuvien, btn_xong_addplaylist_thuvien1;
    LinearLayout linear_addplayist_tv, linear_addplayist_tv1;
    RecyclerView recycler_Playlist_thuvien_add;
    AddNgheSiAdapter addNgheSiAdapter;
    EditText edt_tenPlaylist;
    ArrayList<AddNgheSi_ThuVien> arrayList;
    View view;
    SQLiteDatabase database = null;
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_playlist, container, false);
        addControl();
        addEvents();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void addEvents() {
        btn_xong_addplaylist_thuvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Fragment và đặt Bundle vào Fragment

                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    Integer maU = mainActivity.getMyVariable();
                    String name = edt_tenPlaylist.getText().toString().trim();
                    ContentValues values = new ContentValues();
                    values.put("UserID", maU);
                    values.put("Name", name);
                    dbHelper = DatabaseManager.dbHelper(requireContext());
                    long kq = dbHelper.getReadableDatabase().insert("Playlist_User", null, values);
                    // Lưu giá trị vào SharedPreferences
                    database = getActivity().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                    Cursor cursor = database.rawQuery("select * from Playlist_User", null);
                    while (cursor.moveToNext()) {
                        Integer id = cursor.getInt(0);
                        String name1 = cursor.getString(1);
                        if (name1.equals(name)) {
                            sharedPreferences = getActivity().getSharedPreferences("MyID", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("ID", id);
                            editor.apply();
                            break;
                        }
                    }
                }
                linear_addplayist_tv.setVisibility(View.GONE);
                linear_addplayist_tv1.setVisibility(View.VISIBLE);
            }
        });
        btn_xong_addplaylist_thuvien1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Fragment và đặt Bundle vào Fragment
                Library_Fragment fragment = new Library_Fragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.commit();
            }
        });
        search_thuvien_addPlaylist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addNgheSiAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                addNgheSiAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void loadData() {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            Integer maU = mainActivity.getMyVariable();
            database = getActivity().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * " +
                    "FROM Songs ", null);
//                            "WHERE Songs.SongID NOT IN (SELECT Playlist_User_Song.SongID " +
//                            "FROM Playlist_User_Song " +
//                            "WHERE Playlist_User_Song.UserID = ?)",
//                    new String[]{String.valueOf(maU)});
            arrayList.clear();
            while (cursor.moveToNext()) {
                String ten = cursor.getString(2);
                byte[] img = cursor.getBlob(3);
                AddNgheSi_ThuVien addNgheSiThuVien = new AddNgheSi_ThuVien(img, ten);
                arrayList.add(addNgheSiThuVien);
            }
            addNgheSiAdapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    private void addControl() {
        search_thuvien_addPlaylist = view.findViewById(R.id.search_thuvien_addPlaylist);
        recycler_Playlist_thuvien_add = view.findViewById(R.id.recycler_Playlist_thuvien_add);
        edt_tenPlaylist = view.findViewById(R.id.edt_tenPlaylist);
        linear_addplayist_tv = view.findViewById(R.id.linear_addplayist_tv);
        btn_xong_addplaylist_thuvien1 = view.findViewById(R.id.btn_xong_addplaylist_thuvien1);
        linear_addplayist_tv1 = view.findViewById(R.id.linear_addplayist_tv1);
        btn_xong_addplaylist_thuvien = view.findViewById(R.id.btn_xong_addplaylist_thuvien);
        arrayList = new ArrayList<>();
        addNgheSiAdapter = new AddNgheSiAdapter(requireContext(), arrayList);
        addNgheSiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {

                if (getActivity() instanceof MainActivity) {
                    Integer ID = getActivity().getSharedPreferences("MyID", MODE_PRIVATE).getInt("ID", 0);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    Integer maU = mainActivity.getMyVariable();
                    database = getActivity().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                    Cursor cursor = database.rawQuery("select * from Songs", null);
                    while (cursor.moveToNext()) {
                        String ten = cursor.getString(2);
                        Integer idSong = Integer.valueOf(cursor.getString(0) + "");
                        if (data.equals(ten)) {
                            ContentValues values = new ContentValues();
                            values.put("UserID", maU);
                            values.put("SongID", idSong);
                            values.put("ID_Playlist_User", ID);
                            dbHelper = DatabaseManager.dbHelper(requireContext());
                            long kq = dbHelper.getReadableDatabase().insert("Playlist_User_Song", null, values);
                            if (kq > 0) {
                                break;
                            }
                        }
                    }
                    cursor.close();
                }

            }
        });
        recycler_Playlist_thuvien_add.setAdapter(addNgheSiAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        recycler_Playlist_thuvien_add.setLayoutManager(gridLayoutManager);
    }
}
