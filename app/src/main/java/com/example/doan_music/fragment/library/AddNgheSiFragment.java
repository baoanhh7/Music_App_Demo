package com.example.doan_music.fragment.library;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

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
import android.widget.SearchView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.thuvien.AddNgheSiAdapter;
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
        database = getActivity().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from Artists", null);
        arrayList.clear();
        while (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            byte[] img = cursor.getBlob(2);
            AddNgheSi_ThuVien addNgheSiThuVien = new AddNgheSi_ThuVien(img,ten);
            arrayList.add(addNgheSiThuVien);
        }
        addNgheSiAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addControl() {
        search_thuvien_addArtist = view.findViewById(R.id.search_thuvien_addArtist);
        recycler_Artists_thuvien_add = view.findViewById(R.id.recycler_Artists_thuvien_add);

        arrayList = new ArrayList<>();
        addNgheSiAdapter = new AddNgheSiAdapter(requireContext(),arrayList);
        addNgheSiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                // Tạo đối tượng Bundle và đính kèm dữ liệu
                Bundle bundle = new Bundle();
                bundle.putString("key", data); // Thay "key" bằng key bạn muốn đặt cho dữ liệu

                // Tạo Fragment và đặt Bundle vào Fragment
                Library_Fragment fragment = new Library_Fragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,fragment);
                fragmentTransaction.commit();
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