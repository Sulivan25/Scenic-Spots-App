package com.example.doanthangcanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ggmapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggmap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frgmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        String lat=getIntent().getStringExtra("lat");
        String lon=getIntent().getStringExtra("lon");
        String ten= getIntent().getStringExtra("ten");
        double la = Double.parseDouble(lat);
        double lo = Double.parseDouble(lon);

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(la, lo);

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(ten));
        /*mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16f));
    }
}