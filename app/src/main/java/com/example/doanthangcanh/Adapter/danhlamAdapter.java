package com.example.doanthangcanh.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthangcanh.DetailActivity;
import com.example.doanthangcanh.Fragment.SearchFragment;
import com.example.doanthangcanh.R;
import com.example.doanthangcanh.danhlam;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
public class danhlamAdapter  extends RecyclerView.Adapter<danhlamAdapter.danhlamVH> {
    ArrayList<danhlam> danhlams;
    UserCallback userCallback;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public danhlamAdapter(UserCallback userCallback,ArrayList<danhlam> danhlams) {
        this.userCallback=userCallback;
        this.danhlams = danhlams;
    }
    @NonNull
    @Override
    public danhlamVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_danhlam, parent,false);
        return new danhlamVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull danhlamVH holder, int position) {
        danhlam Danhlam = danhlams.get(position);
        Picasso.get()
                .load(Danhlam.getAnh1())
                .fit()
                .into(holder.imganh1);
        Picasso.get()
                .load(Danhlam.getAnh1())
                .fit()
                .into(holder.imganh2);
        if(Danhlam.isThich()==true){
            holder.btnthich.setColorFilter(Color.RED);
        }
        else if (Danhlam.isThich()==false) {
            holder.btnthich.setColorFilter(Color.GRAY);
        }
        holder.txtten.setText(Danhlam.getTen());
        holder.txtdiadiem.setText(Danhlam.getDiadiem());
//      holder.txtmota.setText(Danhlam.getMota());
        NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);
        numberFormatter.setMinimumFractionDigits(2);

        holder.itemView.setOnClickListener(view -> userCallback.onItemClick(Danhlam.getTen(),Danhlam.getMota(),Danhlam.getAnh1(),Danhlam.getMotachitiet(),Danhlam.getVideo(),Danhlam.getId(),Danhlam.getLat(),Danhlam.getLon()));
        holder.btnthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map=new HashMap<>();
                map.put("thich",true);
                map.put("anh1",Danhlam.getAnh1());
                map.put("ten",Danhlam.getTen());
                map.put("diadiem",Danhlam.getDiadiem());
                map.put("mota",Danhlam.getMota());
                map.put("motachitiet",Danhlam.getMotachitiet());
                map.put("id",Danhlam.getId());
                map.put("video",Danhlam.getVideo());
                map.put("idtp",Danhlam.getIdtp());
                map.put("lat",Danhlam.getLat());
                map.put("lon",Danhlam.getLon());
                db.collection("danhlam").document(Danhlam.getId())
                        .set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                        notifyDataSetChanged();
            }
        });
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
            imganh2 = itemView.findViewById(R.id.imganh3);
           txtten = itemView.findViewById(R.id.txtten1);
            txtmota = itemView.findViewById(R.id.txtmota);
            txtdiadiem = itemView.findViewById(R.id.txtdiadiem);
            btnthich=itemView.findViewById(R.id.btnthich);
        }
    }
    public interface UserCallback{
        void onItemClick(String ten, String mota, String anh1, String motachitiet, String video, String id,String lat,String lon);
    }
}

