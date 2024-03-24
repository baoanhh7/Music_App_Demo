package com.example.doan_music.adapter.thuvien;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_music.R;
import com.example.doan_music.m_interface.OnItemClickListener;
import com.example.doan_music.model.ThuVien;

import java.util.ArrayList;

public class ThuVienAlbumAdapter extends RecyclerView.Adapter<ThuVienAlbumAdapter.ViewHolder> {
    //khai báo biến
    Context context;
    ArrayList<ThuVien> arr, arr1;
    private OnItemClickListener onItemClickListener;

    public ThuVienAlbumAdapter(Context context, ArrayList<ThuVien> arr) {
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
        byte[] hinhAlbumByteArray = tv.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        holder.img.setImageBitmap(bitmap);
        holder.txtTen.setText(tv.getTensp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(arr.get(position).getTensp());
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
            img = itemView.findViewById(R.id.img_thuvien);
            txtTen = itemView.findViewById(R.id.txtTen_thuvien);
        }
    }
}
