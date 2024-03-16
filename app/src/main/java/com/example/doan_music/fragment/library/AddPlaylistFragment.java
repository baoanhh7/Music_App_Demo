package com.example.doan_music.fragment.library;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

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
    Button btn_xong_addplaylist_thuvien;
    RecyclerView recycler_Playlist_thuvien_add;
    AddNgheSiAdapter addNgheSiAdapter;
    EditText edt_tenPlaylist;
    ArrayList<AddNgheSi_ThuVien> arrayList;
    View view;
    SQLiteDatabase database = null;
    DbHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_playlist, container, false);
        addControl();
        loadData();
        addEvents();
        // Inflate the layout for this fragment
        return view;
    }

    private void addEvents() {
        btn_xong_addplaylist_thuvien.setOnClickListener(new View.OnClickListener() {
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
                            "FROM Songs " +
                            "WHERE Songs.SongID NOT IN (SELECT PlayList_User_Song.SongID " +
                            "FROM PlayList_User_Song " +
                            "WHERE PlayList_User_Song.UserID = ?)",
                    new String[]{String.valueOf(maU)});
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
        btn_xong_addplaylist_thuvien = view.findViewById(R.id.btn_xong_addplaylist_thuvien);
        arrayList = new ArrayList<>();
        String name = edt_tenPlaylist.getText().toString().trim();
        addNgheSiAdapter = new AddNgheSiAdapter(requireContext(), arrayList);
        addNgheSiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                // Tạo đối tượng Bundle và đính kèm dữ liệu
                //Bundle bundle = new Bundle();
                // bundle.putString("key", data); // Thay "key" bằng key bạn muốn đặt cho dữ liệu
                if (getActivity() instanceof MainActivity) {
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
                            values.put("Name", name);
                            dbHelper = DatabaseManager.dbHelper(requireContext());
                            long kq = dbHelper.getReadableDatabase().insert("PlayList_User_Song", null, values);
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
