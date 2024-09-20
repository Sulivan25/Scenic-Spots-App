package com.example.doanthangcanh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class danhlamyeuthichAdapter  extends RecyclerView.Adapter<danhlamyeuthichAdapter.danhlamVH> {
    ArrayList<danhlam> danhlams;
    UserCallback userCallback;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public danhlamyeuthichAdapter(UserCallback userCallback, ArrayList<danhlam> danhlams) {
        this.danhlams = danhlams;
        this.userCallback=userCallback;
    }
    @NonNull
    @Override
    public danhlamVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_danhlam, parent,false);
        return new danhlamVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull danhlamVH holder, int position) {
        danhlam Danhlam = danhlams.get(position);
        Picasso.get()
                .load(Danhlam.getAnh1())
                .fit()
                .into(holder.imganh1);
//        Picasso.get()
//                .load(Danhlam.getAnh1())
//                .fit()
//                .into(holder.imganh2);
        holder.txtten.setText(Danhlam.getTen());
        holder.txtdiadiem.setText(Danhlam.getDiadiem());
        holder.txtmota.setText(Danhlam.getMota());
        NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);
        numberFormatter.setMinimumFractionDigits(2);
        holder.itemView.setOnClickListener(view -> userCallback.onItemClick(Danhlam.getTen(),Danhlam.getMota(),Danhlam.getAnh1(),Danhlam.getMotachitiet(),Danhlam.getVideo(),Danhlam.getId(),Danhlam.getLat(),Danhlam.getLon()));
        holder.btnkothich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map=new HashMap<>();
                map.put("thich",false);
                map.put("anh1",Danhlam.getAnh1());
                map.put("ten",Danhlam.getTen());
                map.put("diadiem",Danhlam.getDiadiem());
                map.put("mota",Danhlam.getMota());
                map.put("motachitiet",Danhlam.getMotachitiet());
                map.put("id",Danhlam.getId());
                map.put("idtp",Danhlam.getIdtp());
                map.put("video",Danhlam.getVideo());
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
        ImageView imganh1;
        TextView txtten,txtmota,txtdiadiem;
        ImageButton btnkothich;
        public danhlamVH(@NonNull View itemView) {
            super(itemView);
            imganh1 = itemView.findViewById(R.id.imganh1);
            txtten = itemView.findViewById(R.id.txtten);
            txtmota = itemView.findViewById(R.id.txtmota);
            txtdiadiem = itemView.findViewById(R.id.txtdiadiem);
            btnkothich=itemView.findViewById(R.id.btnkothich);
        }
    }
    public interface UserCallback{
        void onItemClick(String ten, String mota, String anh1, String motachitiet, String video, String id,String lat,String lon);
    }


}

