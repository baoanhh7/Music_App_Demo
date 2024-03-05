package com.example.doan_music.fragment.tab_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.SongListActivity;
import com.example.doan_music.adapter.home.CategoryAdapter;
import com.example.doan_music.adapter.home.HomeAdapter;
import com.example.doan_music.m_interface.IClickItemUser;
import com.example.doan_music.model.Category;
import com.example.doan_music.model.User;

import java.util.ArrayList;
import java.util.List;

public class All_Fragment extends Fragment {
    private RecyclerView rcv_all_header, rcv_all_bottom;
    private HomeAdapter allAdapter_header;
    private CategoryAdapter allCateAdapter_bottom;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcv_all_bottom.setLayoutManager(linearLayoutManager);

        // set data cho recyclerView
        allAdapter_header.setData(getlistuserHeader());
        allCateAdapter_bottom.setData(getlistuserBottom());

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

    private List<Category> getlistuserBottom() {
        List<User> list = new ArrayList<>();

        list.add(new User(R.drawable.avt_vu, "Vũ.Radio", false));
        list.add(new User(R.drawable.avt_ronboogz, "Ronboogz", false));
        list.add(new User(R.drawable.avt_gducky, "GDucky", false));
        list.add(new User(R.drawable.avt_hoangdung, "Hoàng Dũng", false));

        list.add(new User(R.drawable.avt_mck, "RPT MCK Radio", false));
        list.add(new User(R.drawable.avt_lowg, "Low G Radio", false));
        list.add(new User(R.drawable.avt_obito, "Obito", false));
        list.add(new User(R.drawable.avt_dalab, "Dalab Radio", false));

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Radio phổ biến", list));
        categoryList.add(new Category("Tuyển tập hàng đầu của bạn", list));
        categoryList.add(new Category("Lựa chọn của Spotify", list));
        categoryList.add(new Category("Tâm trạng", list));
        categoryList.add(new Category("Tập dành cho bạn", list));
        categoryList.add(new Category("Khám phá đề xuất dành cho bạn", list));
        categoryList.add(new Category("Tập được yêu thích", list));
        categoryList.add(new Category("Hoài niệm", list));

        return categoryList;
    }

    private void addControls() {
        rcv_all_header = view.findViewById(R.id.rcv_all_header);
        rcv_all_bottom = view.findViewById(R.id.rcv_all_bottom);


        //put dữ liệu header có key là u, place get SongListActivity
        allAdapter_header = new HomeAdapter(new IClickItemUser() {
            @Override
            public void onClickItemUser(User user) {
                Intent i = new Intent(requireContext(), SongListActivity.class);
                i.putExtra("u", user);
                startActivity(i);
            }
        });

        allCateAdapter_bottom = new CategoryAdapter();

//        allCateAdapter_bottom = new CategoryAdapter(new IClickItemCategory() {
//            @Override
//            public void onClickItemCategory(Category category) {
//                Intent i = new Intent(requireContext(), SongListActivity.class);
//                i.putExtra("c", category);
//                startActivity(i);
//            }
//        });

        rcv_all_header.setAdapter(allAdapter_header);
        rcv_all_bottom.setAdapter(allCateAdapter_bottom);
    }
}