package com.example.doanthangcanh.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.doanthangcanh.Adapter.danhlamAdapter;
import com.example.doanthangcanh.Adapter.recents_danhlamAdapter;
//import com.example.doanthangcanh.App;
import com.example.doanthangcanh.DetailActivity;
import com.example.doanthangcanh.MainActivity;
import com.example.doanthangcanh.R;
import com.example.doanthangcanh.danhlam;
import com.example.doanthangcanh.ggmapActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements danhlamAdapter.UserCallback {
//    implements danhlamAdapter.Listener
    ToggleButton thaydoi,thaydoi1;
    ImageButton imgBack;

    RecyclerView rvdanhlams,recentsdanhlam;
    ArrayList<danhlam> dsdanhlams,dsdanhlams1,dsdanhlams2;
    danhlamAdapter DanhlamAdapter;
    recents_danhlamAdapter Recents_danhlamAdapter;
    SearchView searchView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v= inflater.inflate(R.layout.fragment_home, container, false);

        // rv danh lam
        thaydoi=v.findViewById(R.id.thaydoi);
        thaydoi1=v.findViewById(R.id.thaydoi1);
        rvdanhlams = v.findViewById(R.id.rvdanhlams);
        // recent danh lam
        recentsdanhlam=v.findViewById(R.id.recent_recycler);
        rvdanhlams.setLayoutManager (new LinearLayoutManager(container.getContext()));
        recentsdanhlam.setLayoutManager(new LinearLayoutManager(container.getContext(),RecyclerView.HORIZONTAL,false));
        dsdanhlams = new ArrayList<danhlam>();
        dsdanhlams1 = new ArrayList<danhlam>();
        DanhlamAdapter = new danhlamAdapter(this,dsdanhlams);
        Recents_danhlamAdapter=new recents_danhlamAdapter(this::onItemClick,dsdanhlams1);
        //T
        CollectionReference data=db.collection("danhlam");
        Query query1=data.orderBy("ten");
        Query query=data.orderBy("ten", Query.Direction.DESCENDING);
        EventChangeListener(query);
        EventChangeListener1(query1);
        thaydoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Query query1 = data.orderBy("ten", Query.Direction.DESCENDING);//ASCII descending chạy từ z->a rồi Z->A
                    EventChangeListener1(query1);
                }
                else{
                    Query query1=data.orderBy("ten");
                    EventChangeListener1(query1);
                }
            }
        });
        thaydoi1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Query query = data.orderBy("ten");
                    Toast.makeText(getContext(), "Bạn đã click", Toast.LENGTH_SHORT).show();
                    EventChangeListener(query);
                }
                else{
                    Query query=data.orderBy("ten", Query.Direction.DESCENDING);
                    Toast.makeText(getContext(), "Bạn đã click ngược lại", Toast.LENGTH_SHORT).show();
                    EventChangeListener(query);
                }
            }
        });
        rvdanhlams.setAdapter(DanhlamAdapter);
        recentsdanhlam.setAdapter(Recents_danhlamAdapter);
        return v;
    }

    private void EventChangeListener1(Query query1) {
        dsdanhlams1.clear();
//        db.collection("danhlam").orderBy("ten",Query.Direction.DESCENDING)
                query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e( "Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                dsdanhlams1.add(dc.getDocument().toObject (danhlam.class));
                            }

                        }
                        Recents_danhlamAdapter.notifyDataSetChanged();
                    }
                });
    }
    private void EventChangeListener(Query query) {
        dsdanhlams.clear();
//        db.collection("danhlam").orderBy("ten")
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {

                    Log.e( "Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        dsdanhlams.add(dc.getDocument().toObject (danhlam.class));

                    }

                }
                DanhlamAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClick(String ten,String mota,String anh1,String motachitiet,String video,String id,String lat,String lon) {
        Intent i= new Intent(getActivity(),DetailActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("motachitiet",motachitiet);
        i.putExtra("anh1",anh1);
        i.putExtra("video",video);
        i.putExtra("id",id);
        i.putExtra("lat",lat);
        i.putExtra("lon",lon);
        startActivity(i);
    }
}