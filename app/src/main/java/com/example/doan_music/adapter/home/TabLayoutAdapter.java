package com.example.doan_music.adapter.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doan_music.fragment.tab_home.All_Fragment;
import com.example.doan_music.fragment.tab_home.Song_Fragment;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    private FragmentChangeListener fragmentChangeListener;

    public TabLayoutAdapter(@NonNull FragmentManager fm, int behavior) {
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
            default:
                return new All_Fragment();
        }
    }

    // return số lượng item
    @Override
    public int getCount() {
        return 2;
    }

    // viewpager sử dụng với tablayout phải có hàm xử lý getPageTitle để đặt tên cho tab
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Tất cả";
            case 1:
                return "Âm nhạc yêu thích";
            default:
                return "Tất cả";
        }
    }

    public void replaceFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new All_Fragment();
                break;
            case 1:
                fragment = new Song_Fragment();
                break;
            default:
                fragment = new All_Fragment();
        }
        fragmentChangeListener.replace(fragment);
    }

    public interface FragmentChangeListener {
        void replace(Fragment fragment);
    }
}
