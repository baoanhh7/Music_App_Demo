package com.example.doan_music.fragment.main;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.activity.MainActivity;
import com.example.doan_music.activity.library.ArtistSongActivity;
import com.example.doan_music.activity.library.PlaylistUserLoveActivity;
import com.example.doan_music.adapter.thuvien.ThuVienAdapter;
import com.example.doan_music.data.DbHelper;
import com.example.doan_music.fragment.library.AddNgheSiFragment;
import com.example.doan_music.fragment.library.AddPlaylistFragment;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.ThuVien;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class Library_Fragment extends Fragment implements OnItemClickListener {
    RecyclerView recyclerView;
    ThuVienAdapter thuVienAdapter;
    ArrayList<ThuVien> arr;
    SQLiteDatabase database = null;
    DbHelper dbHelper;
    Button btnDoi;
    View view;
    ImageButton btn_thuvien_add;
    SearchView btn_thuvien_search;
    TableRow tbr_bottom_sheet_thuvien_adddanhsachphat, tbr_bottom_sheet_thuvien_addnghesy;

    public static byte[] convertDrawableToByteArray(Context context, int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_library_, container, false);
        addControl();
        addEvents();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void addEvents() {
        btn_thuvien_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenBottomSheetDialog();
            }
        });
        btn_thuvien_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                thuVienAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                thuVienAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    private void clickOpenBottomSheetDialog() {
        View viewdialog = getLayoutInflater().inflate(R.layout.bottom_sheet_thuvien, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(viewdialog);
        // Ánh xạ tablerow trong bottom sheet
        tbr_bottom_sheet_thuvien_adddanhsachphat = viewdialog.findViewById(R.id.tbr_bottom_sheet_thuvien_adddanhsachphat);
        tbr_bottom_sheet_thuvien_addnghesy = viewdialog.findViewById(R.id.tbr_bottom_sheet_thuvien_addnghesy);
        tbr_bottom_sheet_thuvien_addnghesy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(requireContext(), AddNgheSiFragment.class);
                //startActivity(intent);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new AddNgheSiFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                // Đóng bottom sheet dialog sau khi xử lý xong
                bottomSheetDialog.dismiss();
            }
        });
        tbr_bottom_sheet_thuvien_adddanhsachphat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new AddPlaylistFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                // Đóng bottom sheet dialog sau khi xử lý xong
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void loadData() {
        // arr.add(new ThuVien(R.drawable.obito, "Obito"));
        //arr.add(new ThuVien(R.drawable.podcastchualanh, "Viết chữa lành"));
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            Integer maU = mainActivity.getMyVariable();
            // Bundle bundle = getArguments();

            //if (bundle != null) {
            // Trích xuất dữ liệu từ Bundle
            // String data = bundle.getString("key"); // Thay "key" bằng key bạn đã đặt trong Activity
            database = getActivity().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("select * " +
                    "from Artists " +
                    "JOIN User_Artist ON Artists.ArtistID =User_Artist.User_Artist_ArtistID " +
                    "WHERE User_Artist.User_Artist_UserID = ? ", new String[]{String.valueOf(maU)});
            arr.clear();
            while (cursor.moveToNext()) {
                Integer maArtist = Integer.valueOf(cursor.getString(0) + "");
                String ten = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                ThuVien thuVien = new ThuVien(img, ten);
                arr.add(thuVien);

            }

            cursor.close();
            cursor = database.rawQuery("select * " +
                    "from Playlist_User " +
                    "WHERE Playlist_User.UserID = ? ", new String[]{String.valueOf(maU)});
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(2);
                String ten = cursor.getString(1);
                byte[] byteArray = convertDrawableToByteArray(requireContext(), R.drawable.music_logo);
                if (maU.equals(id)) {
                    ThuVien thuVien = new ThuVien(byteArray, ten);
                    arr.add(thuVien);
                }
            }
            cursor.close();
            thuVienAdapter.notifyDataSetChanged();
        }
    }

    private void addControl() {
        btn_thuvien_search = view.findViewById(R.id.btn_thuvien_search);

        EditText editTextSearch = btn_thuvien_search.findViewById(androidx.appcompat.R.id.search_src_text);
        editTextSearch.setTextColor(getResources().getColor(R.color.white));
        recyclerView = view.findViewById(R.id.recyclerviewTV);
        btn_thuvien_add = view.findViewById(R.id.btn_thuvien_add);
        btnDoi = view.findViewById(R.id.btnDoi);
        arr = new ArrayList<>();
        thuVienAdapter = new ThuVienAdapter(this, arr);
        thuVienAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                database = getActivity().openOrCreateDatabase("doanmusic.db", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("select * from Artists", null);
                while (cursor.moveToNext()) {
                    Integer Id = cursor.getInt(0);
                    String ten = cursor.getString(1);
                    if (data.equals(ten)) {
                        Intent intent = new Intent(requireContext(), ArtistSongActivity.class);
                        intent.putExtra("MaArtist", Id);
                        startActivity(intent);
                        break;
                    }
                }
                cursor.close();
                cursor = database.rawQuery("select * from Playlist_User", null);
                while (cursor.moveToNext()) {
                    Integer Id = cursor.getInt(0);
                    String ten = cursor.getString(1);
                    if (data.equals(ten)) {
                        Intent intent = new Intent(requireContext(), PlaylistUserLoveActivity.class);
                        intent.putExtra("MaPlaylist", Id);
                        startActivity(intent);
                        break;
                    }
                }
                cursor.close();
            }
        });
        recyclerView.setAdapter(thuVienAdapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(requireContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        // recyclerViewNV.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayout);
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getLayoutManager() == linearLayout)
                    recyclerView.setLayoutManager(gridLayoutManager);
                else
                    recyclerView.setLayoutManager(linearLayout);
            }
        });
        //recyclerViewNV.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
        // Lấy Bundle từ Fragment
    }

    @Override
    public void onItemClick(String data) {

    }
}