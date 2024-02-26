package com.example.doan_music.fragment.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan_music.adapter.Tab_ViewPagerAdapter;
import com.example.doan_music.R;
import com.google.android.material.tabs.TabLayout;

public class Home_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager home_viewpager;
    Tab_ViewPagerAdapter adapter;
    // Trong Fragment khi muốn ánh xạ thì khai báo qua đối tượng trung gian là View
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Sau khi tạo đối tượng trung gian là là View thì gán vào dòng return, sau đó return đối tượng View để sử dụng
        mView = inflater.inflate(R.layout.fragment_home_, container, false);

        addControls();

        // Quản lý hiển thị các trang (Fragment) theo TabLayout
        tabLayout.setupWithViewPager(home_viewpager);

        // Đặt màu sắc cho các tab
        tabLayout.setTabTextColors(Color.WHITE, Color.parseColor("#FF4081")); // Màu sắc chuyển đổi ở đây
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF4081")); // Màu sắc cho dấu chỉ mục khi tab được chọn

        return mView;
    }

    private void addControls() {
        tabLayout = mView.findViewById(R.id.tab_layout);
        home_viewpager = mView.findViewById(R.id.home_viewpager);

        // Khác với khai báo Adapter trong Activity, trong Fragment khai báo Adapter theo kiểu sau
        adapter = new Tab_ViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // Cung cấp dữ liệu cho viewpager bằng ViewPagerAdapter như bth
        home_viewpager.setAdapter(adapter);
    }
}