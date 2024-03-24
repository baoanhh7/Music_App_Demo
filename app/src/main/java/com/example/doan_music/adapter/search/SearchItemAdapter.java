package com.example.doan_music.adapter.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.model.SearchItem;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.search_itemViewHolder> implements Filterable {
    private List<SearchItem> listSearchItem;
    private List<SearchItem> listSearchItemOld;

    public SearchItemAdapter(List<SearchItem> listSearchItem) {
        this.listSearchItem = listSearchItem;
        this.listSearchItemOld = listSearchItem;
    }

    @NonNull
    @Override
    public search_itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchview, parent, false);
        return new search_itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull search_itemViewHolder holder, int position) {
        SearchItem searchItem = listSearchItem.get(position);
        if (searchItem == null) {
            return;
        }
        holder.imgItem.setImageResource(searchItem.getImage());
        holder.tvName.setText(searchItem.getName());
        holder.tvDescribe.setText(searchItem.getDescribe());
    }

    @Override
    public int getItemCount() {
        if (listSearchItem != null) {
            return listSearchItem.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    listSearchItem = listSearchItemOld;
                } else {
                    List<SearchItem> list = new ArrayList<>();
                    for (SearchItem searchItem : listSearchItemOld) {
                        if (searchItem.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(searchItem);
                        }
                    }
                    listSearchItem = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listSearchItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listSearchItem = (List<SearchItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class search_itemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvName;
        private TextView tvDescribe;

        public search_itemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_search_song);
            tvName = itemView.findViewById(R.id.tv_search_song_name);
            tvDescribe = itemView.findViewById(R.id.tv_search_song_describe);
        }
    }
}
