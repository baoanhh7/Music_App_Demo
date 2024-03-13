package com.example.doan_music.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.adapter.search.SearchAdapter;
import com.example.doan_music.adapter.search.SearchItemAdapter;
import com.example.doan_music.model.SearchItem;
import com.example.doan_music.model.User;

import java.util.ArrayList;
import java.util.List;

public class Search_Fragment extends Fragment {
    private RecyclerView rcv_search_header, rcv_search_bottom;
    private SearchAdapter searchAdapter_header, searchAdapter_bottom;
    private RecyclerView rcvSearchItem;
    private SearchItemAdapter searchItemAdapter;
    private SearchView searchView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_, container, false);

        addControls();

        //set layout của recyclerView theo hướng ngang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        rcv_search_header.setLayoutManager(linearLayoutManager);

        // set layout của recyclerView thành 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcv_search_bottom.setLayoutManager(gridLayoutManager);

        // set data cho recyclerView
        searchAdapter_header.setData(getlistuserHeader());
        searchAdapter_bottom.setData(getlistuserBottom());

        rcvSearchItem = view.findViewById(R.id.rcv_searchItem);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(requireContext());
        rcvSearchItem.setLayoutManager((linearLayoutManager1));

        searchItemAdapter = new SearchItemAdapter(getListSearchItem());
        rcvSearchItem.setAdapter(searchItemAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        rcvSearchItem.addItemDecoration(itemDecoration);

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItemAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchItemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    private List<SearchItem> getListSearchItem() {
        List<SearchItem> list = new ArrayList<>();
        list.add(new SearchItem(R.drawable.avt_lowg, "LowG", "Nghe Si"));
        list.add(new SearchItem(R.drawable.avt_dalab, "DaLab", "Nghe Si"));
        list.add(new SearchItem(R.drawable.avt_mck, "MCK", "Nghe Si"));
        list.add(new SearchItem(R.drawable.avt_obito, "Obito", "Nghe Si"));
        list.add(new SearchItem(R.drawable.avt_gducky, "Gducky", "Nghe Si"));

        return list;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.search_menu, menu);
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_search){
//            Toast.makeText(getActivity(),"Search",Toast.LENGTH_SHORT).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private List<User> getlistuserHeader() {
        List<User> list = new ArrayList<>();

        list.add(new User(R.drawable.nhac, "Nhạc", true));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", true));
        list.add(new User(R.drawable.podcast, "Podcast", true));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", true));

        list.add(new User(R.drawable.nhac, "Nhạc", true));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", true));
        list.add(new User(R.drawable.podcast, "Podcast", true));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", true));

        list.add(new User(R.drawable.nhac, "Nhạc", true));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", true));
        list.add(new User(R.drawable.podcast, "Podcast", true));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", true));

        list.add(new User(R.drawable.nhac, "Nhạc", true));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", true));
        list.add(new User(R.drawable.podcast, "Podcast", true));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", true));

        return list;
    }

    private List<User> getlistuserBottom() {
        List<User> list = new ArrayList<>();

        list.add(new User(R.drawable.nhac, "Nhạc", false));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", false));
        list.add(new User(R.drawable.podcast, "Podcast", false));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", false));

        list.add(new User(R.drawable.nhac, "Nhạc", false));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", false));
        list.add(new User(R.drawable.podcast, "Podcast", false));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", false));

        list.add(new User(R.drawable.nhac, "Nhạc", false));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", false));
        list.add(new User(R.drawable.podcast, "Podcast", false));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", false));

        list.add(new User(R.drawable.nhac, "Nhạc", false));
        list.add(new User(R.drawable.sukientructiep, "Sự kiện trực tiếp", false));
        list.add(new User(R.drawable.podcast, "Podcast", false));
        list.add(new User(R.drawable.danhchoban, "Dành cho bạn", false));

        return list;
    }

    private void addControls() {
        rcv_search_header = view.findViewById(R.id.rcv_search_header);
        rcv_search_bottom = view.findViewById(R.id.rcv_search_bottom);

        // Đối với fragment thì Context sẽ là requireContext()
        searchAdapter_header = new SearchAdapter();
        searchAdapter_bottom = new SearchAdapter();

        rcv_search_header.setAdapter(searchAdapter_header);
        rcv_search_bottom.setAdapter(searchAdapter_bottom);


    }
}