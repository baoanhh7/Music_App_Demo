package com.example.doan_music.adapter.thuvien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.model.ThuVien;

import java.util.ArrayList;

public class ThuVienAdapter extends RecyclerView.Adapter<ThuVienAdapter.ViewHolder> implements Filterable {
    //khai báo biến
    Fragment context;
    ArrayList<ThuVien> arr,arr1;

    public ThuVienAdapter(Fragment context, ArrayList<ThuVien> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_thuvien, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThuVien tv = arr.get(position);
        holder.img.setImageResource(tv.getHinh());
        holder.txtTen.setText(tv.getTensp());
        holder.txtND.setText(tv.getNoidung());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    arr = arr1;
                }
                else {
                    ArrayList<ThuVien> arrayList = new ArrayList<>();
                    for(ThuVien thuVien : arr1){
                        if(thuVien.getTensp().toLowerCase().contains(strSearch.toLowerCase())){
                            arrayList.add(thuVien);
                        }
                    }
                    arr = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arr;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arr = (ArrayList<ThuVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTen, txtND;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtND = itemView.findViewById(R.id.txtND);
        }
    }
}
