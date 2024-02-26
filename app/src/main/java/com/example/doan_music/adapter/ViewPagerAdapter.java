package com.example.doan_music.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doan_music.fragment.main.Home_Fragment;
import com.example.doan_music.fragment.main.Library_Fragment;
import com.example.doan_music.fragment.main.Search_Fragment;
import com.example.doan_music.fragment.main.Spotify_Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    //Hàm tạo id cho các fragment, hàm trả về là một fragment, tron đó position đóng vai trò là một id của item (item.getId())
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Home_Fragment();
            case 1:
                return new Search_Fragment();
            case 2:
                return new Library_Fragment();
            case 3:
                return new Spotify_Fragment();
            default:
                return new Home_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
