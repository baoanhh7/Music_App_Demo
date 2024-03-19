package com.example.doan_music.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.m_interface.IClickItemCategory;
import com.example.doan_music.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<Category> list;
    private IClickItemCategory iClickItemCategory;

    public CategoryAdapter(IClickItemCategory iClickItemCategory) {
        this.iClickItemCategory = iClickItemCategory;
    }

    public void setData(List<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_cate, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = list.get(position);

        holder.txt_home_cate.setText(category.getName());

        PlaylistHomeAdapter playlistHomeAdapter = new PlaylistHomeAdapter();
        playlistHomeAdapter.setData(category.getList());
        holder.rcv_home_cate.setAdapter(playlistHomeAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rcv_home_cate.setLayoutManager(linearLayoutManager);

        holder.txt_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemCategory.onClickItemCategory(category);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txt_home_cate, txt_xemthem;
        RecyclerView rcv_home_cate;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_home_cate = itemView.findViewById(R.id.txt_home_cate);
            rcv_home_cate = itemView.findViewById(R.id.rcv_home_cate);

            txt_xemthem = itemView.findViewById(R.id.txt_xemthem);
        }
    }
}
