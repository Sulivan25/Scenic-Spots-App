package com.example.doanthangcanh;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.doanthangcanh.Adapter.danhlamAdapter;
import com.example.doanthangcanh.Adapter.thanhphoAdapter;
import com.example.doanthangcanh.Fragment.HomeFragment;
import com.example.doanthangcanh.Fragment.SearchFragment;
import com.example.doanthangcanh.Fragment.SettingFragment;
import com.example.doanthangcanh.Fragment.thanhphoFragment;
import com.example.doanthangcanh.Fragment.yeuthichFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements danhlamAdapter.UserCallback,thanhphoAdapter.Listener {
    BottomNavigationView bnv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SearchView searchView;
    RecyclerView rvdanhlams;
    ArrayList<danhlam> dsdanhlams;
    danhlamAdapter DanhlamAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottom_menu);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                display(item.getItemId());

                return true;
            }
        });
        display(R.id.mnuHome);
    }

    void display(int id) {
        Fragment fragment = null;
        Activity activity = null;
        switch (id) {
            case R.id.mnuHome:
                fragment = new HomeFragment();
                getSupportActionBar().setTitle("Trang chủ");
                break;
            case R.id.mnuYeuthich:
                fragment = new yeuthichFragment();
                getSupportActionBar().setTitle("Yêu thích");
                break;
            case R.id.mnSetting:
                fragment = new SettingFragment();
                getSupportActionBar().setTitle("Cài đặt");
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containter, fragment);
        ft.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchdata(s);
                searchdiadiem(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchdata(s);
                searchdiadiem(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void searchdata(String s) {
        rvdanhlams = findViewById(R.id.rvdanhlams);
        rvdanhlams.setLayoutManager(new LinearLayoutManager(this));
        dsdanhlams = new ArrayList<danhlam>();
        DanhlamAdapter = new danhlamAdapter(this, dsdanhlams);
        rvdanhlams.setAdapter(DanhlamAdapter);
        db.collection("danhlam").whereEqualTo("ten", s)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                dsdanhlams.add(dc.getDocument().toObject(danhlam.class));
                            }
                            DanhlamAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    private void searchdiadiem(String s) {
        rvdanhlams = findViewById(R.id.rvdanhlams);
        rvdanhlams.setLayoutManager(new LinearLayoutManager(this));
        dsdanhlams = new ArrayList<danhlam>();
        DanhlamAdapter = new danhlamAdapter(this, dsdanhlams);
        rvdanhlams.setAdapter(DanhlamAdapter);
        db.collection("danhlam").whereEqualTo("diadiem", s)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                dsdanhlams.add(dc.getDocument().toObject(danhlam.class));
                            }
                            DanhlamAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(String ten, String mota, String anh1, String motachitiet, String video, String id,String lat,String lon) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("ten", ten);
        i.putExtra("mota", mota);
        i.putExtra("motachitiet", motachitiet);
        i.putExtra("anh1", anh1);
        i.putExtra("video", video);
        i.putExtra("id", id);
        i.putExtra("lat",lat);
        i.putExtra("lon",lon);
        startActivity(i);
    }

    @Override
    public void senId(String id) {
        Intent i = new Intent(this, SearchFragment.class);
//        String s=getIntent().getStringExtra("idtp");
//        i.putExtra("idtp",id);
        Bundle bundle = new Bundle();
        bundle.putString("idtp", id);

        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);
        startActivity(i);
    }

}