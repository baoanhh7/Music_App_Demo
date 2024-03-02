package com.example.doan_music.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.thuvien.ThuVienAdapter;
import com.example.doan_music.model.ThuVien;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class Library_Fragment extends Fragment {
    RecyclerView recyclerView;
    ThuVienAdapter thuVienAdapter;
    ArrayList<ThuVien> arr;
    Button btnDoi;
    View view;
    ImageButton btn_thuvien_add,btn_thuvien_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_library_, container, false);
        addControl();
        loadData();
        addEvents();
        return view;
    }

    private void addEvents() {
        btn_thuvien_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenBottomSheetDialog();
            }
        });
    }

    private void clickOpenBottomSheetDialog() {
        View viewdialog = getLayoutInflater().inflate(R.layout.bottom_sheet_thuvien, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(viewdialog);
        bottomSheetDialog.show();
    }

    private void loadData() {
        arr.add(new ThuVien(R.drawable.obito, "Obito", "Nghệ sĩ"));
        arr.add(new ThuVien(R.drawable.podcastchualanh, "Viết chữa lành", "Podcast - Writing therapy"));
    }


    private void addControl() {
        recyclerView = view.findViewById(R.id.recyclerviewTV);
        btn_thuvien_add = view.findViewById(R.id.btn_thuvien_add);
        btnDoi = view.findViewById(R.id.btnDoi);
        arr = new ArrayList<>();
        thuVienAdapter = new ThuVienAdapter(this, arr);
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
    }
}