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
import com.example.doanthangcanh.thanhpho;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class thanhphoAdapter extends RecyclerView.Adapter<thanhphoAdapter.thanhphoVH> {
    ArrayList<thanhpho> thanhphos;
    Listener listener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public thanhphoAdapter(Listener listener, ArrayList<thanhpho> thanhphos) {
       this.listener=listener;
        this.thanhphos=thanhphos;
    }
    @NonNull
    @Override
    public thanhphoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cell_danhlam, parent, false);
        return new thanhphoVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull thanhphoVH holder, int position) {
        thanhpho Thanhpho = thanhphos.get(position);
        holder.tentp.setText(Thanhpho.getId());
        NumberFormat numberFormatter = NumberFormat.getInstance(Locale.US);
        numberFormatter.setMinimumFractionDigits(2);
        holder.itemView.setOnClickListener(view -> listener.senId(Thanhpho.getId()));
    }
    @Override
    public int getItemCount() {
        return thanhphos.size();
    }
    class thanhphoVH extends RecyclerView.ViewHolder {
        TextView  tentp;
        public thanhphoVH(@NonNull View itemView) {
            super(itemView);
            tentp=itemView.findViewById(R.id.tentp);
        }
    }
    public interface Listener {
        void senId(String id);
    }
}
