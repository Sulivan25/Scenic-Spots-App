package com.example.doanthangcanh.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthangcanh.R;
import com.example.doanthangcanh.danhlam;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class recents_danhlamAdapter extends RecyclerView.Adapter<recents_danhlamAdapter.danhlamVH>{
    ArrayList<danhlam> danhlams;
    UserCallback userCallback;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public recents_danhlamAdapter(UserCallback userCallback, ArrayList<danhlam> danhlams) {
        this.userCallback=userCallback;
        this.danhlams = danhlams;
    }
    @NonNull
    @Override
    public danhlamVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recents_row_item, parent,false);
        return new danhlamVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull recents_danhlamAdapter.danhlamVH holder, int position) {
        danhlam Danhlam = danhlams.get(position);
        Picasso.get()
                .load(Danhlam.getAnh1())
                .fit()
                .into(holder.imganh1);
        holder.txtten.setText(Danhlam.getTen());
        holder.txtdiadiem.setText(Danhlam.getDiadiem());
        NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);
        numberFormatter.setMinimumFractionDigits(2);
        holder.itemView.setOnClickListener(view -> userCallback.onItemClick(Danhlam.getTen(),Danhlam.getMota(),Danhlam.getAnh1(),Danhlam.getMotachitiet(),Danhlam.getVideo(),Danhlam.getId(),Danhlam.getLat(),Danhlam.getLon()));
    }
    @Override
    public int getItemCount() {
        return danhlams.size();
    }
    class danhlamVH extends RecyclerView.ViewHolder{
        ImageView imganh1,imganh2;
        TextView txtten,txtmota,txtdiadiem;
        ImageButton btnthich;
        WebView videoWeb;
        public danhlamVH(@NonNull View itemView) {
            super(itemView);
            imganh1 = itemView.findViewById(R.id.imganh2);
            txtten = itemView.findViewById(R.id.txtten1);
            txtdiadiem = itemView.findViewById(R.id.txtdiadiem);
        }
    }
    public interface UserCallback{
        void onItemClick(String ten, String mota, String anh1, String motachitiet, String video, String id,String lat,String lon);
    }
}
