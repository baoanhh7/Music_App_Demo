package com.example.doan_music.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doan_music.fragment.tab_home.All_Fragment;
import com.example.doan_music.fragment.tab_home.Podcasts_Fragment;
import com.example.doan_music.fragment.tab_home.Song_Fragment;

public class Tab_ViewPagerAdapter extends FragmentStatePagerAdapter {
    public Tab_ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    // Hàm tạo id cho các fragment, hàm trả về là một fragment, tron đó position đóng vai trò là một id của item (item.getId())
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new All_Fragment();
            case 1:
                return new Song_Fragment();
            case 2:
                return new Podcasts_Fragment();
            default:
                return new All_Fragment();
        }
    }

    // return số lượng item
    @Override
    public int getCount() {
        return 3;
    }

    // viewpager sử dụng với tablayout phải có hàm xử lý getPageTitle để đặt tên cho tab
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Tất cả";
            case 1:
                return "Nhạc";
            case 2:
                return "Podcasts";
            default:
                return "Tab 1";
        }
    }
}
