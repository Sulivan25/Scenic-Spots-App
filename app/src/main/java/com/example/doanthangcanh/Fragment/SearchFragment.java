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

import com.example.doanthangcanh.Adapter.danhlamAdapter;
import com.example.doanthangcanh.Adapter.recents_danhlamAdapter;
import com.example.doanthangcanh.DetailActivity;
import com.example.doanthangcanh.R;
import com.example.doanthangcanh.danhlam;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements danhlamAdapter.UserCallback  {
    RecyclerView rvsearch;
    ArrayList<danhlam> dsdanhlams;
    danhlamAdapter DanhlamAdapter;
    SearchView searchView;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public SearchFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        setHasOptionsMenu(true);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        rvsearch = v.findViewById(R.id.rvdanhlams);
        rvsearch.setLayoutManager (new LinearLayoutManager(container.getContext()));
        dsdanhlams = new ArrayList<danhlam>();
        DanhlamAdapter = new danhlamAdapter(this,dsdanhlams);
        rvsearch.setAdapter(DanhlamAdapter);
        EventChangeListener();
        return v;
    }
    private void EventChangeListener() {
        db.collection("danhlam")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            DanhlamAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

       inflater.inflate(R.menu.option_menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        searchView= (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchdata(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchdata(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
    private void searchdata(String s) {
        rvsearch = getActivity().findViewById(R.id.rvdanhlams);
        rvsearch.setLayoutManager (new LinearLayoutManager(getContext()));
        dsdanhlams = new ArrayList<danhlam>();
        DanhlamAdapter = new danhlamAdapter( this,dsdanhlams);

        rvsearch.setAdapter(DanhlamAdapter);
        db.collection("danhlam").whereEqualTo("ten",s.toLowerCase())

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            DanhlamAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    @Override
    public void onItemClick(String ten, String mota, String anh1, String motachitiet,String video,String id,String lat,String lon) {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("ten", ten);
        i.putExtra("mota", mota);
        i.putExtra("motachitiet", motachitiet);
        i.putExtra("anh1", anh1);
        i.putExtra("video",video);
        i.putExtra("id",id);
        i.putExtra("lat",lat);
        i.putExtra("lon",lon);
        startActivity(i);

    }


}