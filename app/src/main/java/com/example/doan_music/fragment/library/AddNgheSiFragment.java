package com.example.doan_music.fragment.library;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link// Fragment} subclass.
 * Use the {@link// AddNgheSiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNgheSiFragment extends Fragment implements OnItemClickListener {

    SearchView search_thuvien_addArtist;
    RecyclerView recycler_Artists_thuvien_add;
    AddNgheSiAdapter addNgheSiAdapter;
    ArrayList<AddNgheSi_ThuVien> arrayList;
    View view;
    SQLiteDatabase database = null;
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_nghe_si, container, false);
        addControl();
        loadData();
        addEvents();
        // Inflate the layout for this fragment
        return view;
    }

    private void addEvents() {
        search_thuvien_addArtist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                            "FROM Artists " +
                            "WHERE Artists.ArtistID NOT IN (SELECT User_Artist_ArtistID " +
                            "FROM User_Artist " +
                            "WHERE User_Artist_UserID = ?)",
                    new String[]{String.valueOf(maU)});
            arrayList.clear();
            while (cursor.moveToNext()) {
                String ten = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                AddNgheSi_ThuVien addNgheSiThuVien = new AddNgheSi_ThuVien(img, ten);
                arrayList.add(addNgheSiThuVien);
            }
            addNgheSiAdapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    private void addControl() {
        search_thuvien_addArtist = view.findViewById(R.id.search_thuvien_addArtist);
        recycler_Artists_thuvien_add = view.findViewById(R.id.recycler_Artists_thuvien_add);

        arrayList = new ArrayList<>();
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
                    Cursor cursor = database.rawQuery("select * from Artists", null);
                    while (cursor.moveToNext()) {
                        String ten = cursor.getString(1);
                        Integer maArtist = Integer.valueOf(cursor.getString(0) + "");
                        if (data.equals(ten)) {
                            ContentValues values = new ContentValues();
                            values.put("User_Artist_UserID", maU);
                            values.put("User_Artist_ArtistID", maArtist);
                            dbHelper = DatabaseManager.dbHelper(requireContext());
                            long kq = dbHelper.getReadableDatabase().insert("User_Artist", null, values);
                            if (kq > 0) {
                                break;
                            }
                        }
                    }
                    cursor.close();

                    // Tạo Fragment và đặt Bundle vào Fragment
                    Library_Fragment fragment = new Library_Fragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        recycler_Artists_thuvien_add.setAdapter(addNgheSiAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        recycler_Artists_thuvien_add.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(String data) {

    }
}
