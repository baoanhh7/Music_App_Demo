package com.example.doan_music.fragment.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.doan_music.R;
import com.example.doan_music.adapter.home.TabLayoutAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class Home_Fragment extends Fragment implements TabLayoutAdapter.FragmentChangeListener {

    TabLayout tabLayout;
    NavigationView navigationView;
    ViewPager home_viewpager;
    TabLayoutAdapter tabLayoutAdapter;
    // Trong Fragment khi muốn ánh xạ thì khai báo qua đối tượng trung gian là View
    View mView;
    TextView txt_nameUser;

    public Home_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Sau khi tạo đối tượng trung gian là là View thì gán vào dòng return, sau đó return đối tượng View để sử dụng
        mView = inflater.inflate(R.layout.fragment_home_, container, false);
        addControls();

//        MainActivity mainActivity = (MainActivity) getActivity();
//        String value = mainActivity.getName();

        SharedPreferences preferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String value = preferences.getString("ten", "Loading..");
        txt_nameUser.setText(value);

        // Quản lý hiển thị các trang (Fragment) theo TabLayout
        tabLayout.setupWithViewPager(home_viewpager);

        // Đặt màu sắc cho các tab
        tabLayout.setTabTextColors(Color.WHITE, Color.parseColor("#FF4081")); // Màu sắc chuyển đổi ở đây
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF4081")); // Màu sắc cho dấu chỉ mục khi tab được chọn

        return mView;
    }

    @Override
    public void replace(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.frame_home, fragment).commit();
    }

    private void addControls() {
        tabLayout = mView.findViewById(R.id.tab_layout);
        home_viewpager = mView.findViewById(R.id.home_viewpager);
        navigationView = mView.findViewById(R.id.navigation_drawer);

        txt_nameUser = mView.findViewById(R.id.txt_nameUser);

        // Khác với khai báo Adapter trong Activity, trong Fragment khai báo Adapter theo kiểu sau

        tabLayoutAdapter = new TabLayoutAdapter(getChildFragmentManager(), TabLayoutAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        home_viewpager.setAdapter(tabLayoutAdapter);
    }
}