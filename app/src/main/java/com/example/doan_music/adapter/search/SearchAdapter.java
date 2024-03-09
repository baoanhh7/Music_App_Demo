package com.example.doan_music.adapter.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.model.User;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int type_header = 0;
    private static final int type_bottom = 1;

    private List<User> listUser;

    public void setData(List<User> list) {
        this.listUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type_header == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_header, parent, false);
            return new UserHeaderViewHolder(view);
        } else if (type_bottom == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_bottom, parent, false);
            return new UserBottomViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = listUser.get(position);

        if (type_header == holder.getItemViewType()) {
            UserHeaderViewHolder userHeadViewHolder = (UserHeaderViewHolder) holder;
            userHeadViewHolder.img_search_header.setImageResource(user.getResourceImage());
            userHeadViewHolder.txt_search_header.setText(user.getName());
        } else if (type_bottom == holder.getItemViewType()) {
            UserBottomViewHolder userBottomViewHolder = (UserBottomViewHolder) holder;
            userBottomViewHolder.img_search_bottom.setImageResource(user.getResourceImage());
            userBottomViewHolder.txt_search_bottom.setText(user.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (listUser != null) return listUser.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        User user = listUser.get(position);
        if (user.getHeader()) return type_header;
        else return type_bottom;
    }

    public class UserHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView img_search_header;
        TextView txt_search_header;

        public UserHeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            img_search_header = itemView.findViewById(R.id.img_search_header);
            txt_search_header = itemView.findViewById(R.id.txt_search_header);
        }
    }

    public class UserBottomViewHolder extends RecyclerView.ViewHolder {
        ImageView img_search_bottom;
        TextView txt_search_bottom;

        public UserBottomViewHolder(@NonNull View itemView) {
            super(itemView);

            img_search_bottom = itemView.findViewById(R.id.img_search_bottom);
            txt_search_bottom = itemView.findViewById(R.id.txt_search_bottom);
        }
    }
}
