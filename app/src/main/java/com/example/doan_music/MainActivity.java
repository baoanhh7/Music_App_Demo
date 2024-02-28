package com.example.doan_music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.doan_music.fragment.drawer.ListenedContent_Fragment;
import com.example.doan_music.fragment.drawer.NewContent_Fragment;
import com.example.doan_music.fragment.drawer.Settings_Fragment;
import com.example.doan_music.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

//public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    BottomNavigationView bottom_navigation;
    NavigationView navigationView;
    ViewPager view_pager;

    // Tạo class ViewPagerAdapter đã làm trước đó
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        // Drawer
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        // Tạo 1 adapter theo viewpager
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // Cung cấp dữ liệu cho viewpager bằng ViewPagerAdapter
        view_pager.setAdapter(adapter);

        // Quản lý hiển thị các trang (viewPager)
        // 1. Vuốt
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottom_navigation.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1:
                        bottom_navigation.getMenu().findItem(R.id.menu_search).setChecked(true);
                        break;
                    case 2:
                        bottom_navigation.getMenu().findItem(R.id.menu_library).setChecked(true);
                        break;
                    case 3:
                        bottom_navigation.getMenu().findItem(R.id.menu_spotify).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 2.Click
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {
                    view_pager.setCurrentItem(0);
                } else if (itemId == R.id.menu_search) {
                    view_pager.setCurrentItem(1);
                } else if (itemId == R.id.menu_library) {
                    view_pager.setCurrentItem(2);
                } else if (itemId == R.id.menu_spotify) {
                    view_pager.setCurrentItem(3);
                }
                return true;
            }
        });

        // Select item in Drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.newContent) replace(new NewContent_Fragment());
                else if (id == R.id.listenedContent) replace(new ListenedContent_Fragment());
                else if (id == R.id.settings) replace(new Settings_Fragment());
                else if (id == R.id.home) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);

                    finish();
                }
                // Xử lý xong sẽ đóng Drawer
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    private void addControls() {
        bottom_navigation = findViewById(R.id.bottomNavigationView);
        view_pager = findViewById(R.id.view_pager);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer);
    }

    // Nhấn nút back device để trở về(sử dụng nút trong device)
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    public void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    }
}
