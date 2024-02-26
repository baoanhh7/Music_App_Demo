package com.example.doan_music.fragment.tab_home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan_music.R;
import com.example.doan_music.adapter.HomeAdapter;
import com.example.doan_music.adapter.SearchAdapter;
import com.example.doan_music.model.User;

import java.util.ArrayList;
import java.util.List;

public class All_Fragment extends Fragment {
    private RecyclerView rcv_all_header, rcv_all_bottom;
    private HomeAdapter allAdapter_header, allAdapter_bottom;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_, container, false);

        addControls();

        // set layout của recyclerView thành 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcv_all_header.setLayoutManager(gridLayoutManager);

        // set layout của recyclerView theo hướng ngang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        rcv_all_bottom.setLayoutManager(linearLayoutManager);

        // set data cho recyclerView
        allAdapter_header.setData(getlistuserHeader());
        allAdapter_bottom.setData(getlistuserBottom());

        return view;
    }

    private List<User> getlistuserHeader() {
        List<User> list = new ArrayList<>();

        list.add(new User(R.drawable.avt_vu, "Vũ.Radio", true));
        list.add(new User(R.drawable.avt_ronboogz, "Ronboogz", true));
        list.add(new User(R.drawable.avt_gducky, "GDucky", true));
        list.add(new User(R.drawable.avt_hoangdung, "Hoàng Dũng", true));

        list.add(new User(R.drawable.avt_mck, "RPT MCK Radio", true));
        list.add(new User(R.drawable.avt_lowg, "Low G Radio", true));
        list.add(new User(R.drawable.avt_obito, "Obito", true));
        list.add(new User(R.drawable.avt_dalab, "Dalab Radio", true));

        return list;
    }

    private List<User> getlistuserBottom() {
        List<User> list = new ArrayList<>();

        list.add(new User(R.drawable.avt_vu, "Vũ.Radio", false));
        list.add(new User(R.drawable.avt_ronboogz, "Ronboogz", false));
        list.add(new User(R.drawable.avt_gducky, "GDucky", false));
        list.add(new User(R.drawable.avt_hoangdung, "Hoàng Dũng", false));

        list.add(new User(R.drawable.avt_mck, "RPT MCK Radio", false));
        list.add(new User(R.drawable.avt_lowg, "Low G Radio", false));
        list.add(new User(R.drawable.avt_obito, "Obito", false));
        list.add(new User(R.drawable.avt_dalab, "Dalab Radio", false));

        return list;
    }

    private void addControls() {
        rcv_all_header = view.findViewById(R.id.rcv_all_header);
        rcv_all_bottom = view.findViewById(R.id.rcv_all_bottom);

        allAdapter_header = new HomeAdapter();
        allAdapter_bottom = new HomeAdapter();

        rcv_all_header.setAdapter(allAdapter_header);
        rcv_all_bottom.setAdapter(allAdapter_bottom);

    }
}