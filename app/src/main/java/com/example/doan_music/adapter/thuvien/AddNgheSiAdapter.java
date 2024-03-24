package com.example.doan_music.adapter.thuvien;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.AddNgheSi_ThuVien;

import java.util.ArrayList;

public class AddNgheSiAdapter extends RecyclerView.Adapter<AddNgheSiAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<AddNgheSi_ThuVien> arr, arr1;
    private OnItemClickListener onItemClickListener;

    public AddNgheSiAdapter(Context context, ArrayList<AddNgheSi_ThuVien> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    arr = arr1;
                } else {
                    ArrayList<AddNgheSi_ThuVien> arrayList = new ArrayList<>();
                    for (AddNgheSi_ThuVien addNgheSiThuVien : arr1) {
                        if (addNgheSiThuVien.getTensp().toLowerCase().contains(strSearch.toLowerCase())) {
                            arrayList.add(addNgheSiThuVien);
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
                arr = (ArrayList<AddNgheSi_ThuVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_addnghesy_thuvien, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddNgheSi_ThuVien tv = arr.get(position);
        byte[] hinhAlbumByteArray = tv.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        holder.img.setImageBitmap(bitmap);
        holder.txtTen.setText(tv.getTensp());
        // Gán giá trị cho item trong RecyclerView và xử lý sự kiện nhấn
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(arr.get(position).getTensp());
                    int position = holder.getAdapterPosition();
                    arr.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arr.size()); // Cập nhật lại chỉ số của các item sau
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.Img_NgheSy_ThuVien);
            txtTen = itemView.findViewById(R.id.txtTen_NgheSy_ThuVien);
        }
    }
}
