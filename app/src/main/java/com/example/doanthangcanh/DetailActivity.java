package com.example.doanthangcanh;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DetailActivity extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    private String videoId;
    TextView txtten,txtmota,txtmotachitiet;
    ImageButton imgBack;
    ImageView imganh1;
    AppCompatButton map;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtten=findViewById(R.id.ten);
        txtmota=findViewById(R.id.mota);
        txtmotachitiet=findViewById(R.id.motachitiet);
        imganh1=findViewById(R.id.anh1);
        imgBack=findViewById(R.id.imgBack);
        map=findViewById(R.id.map);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String ten= getIntent().getStringExtra("ten");
        String mota= getIntent().getStringExtra("mota");
        String motachitiet= getIntent().getStringExtra("motachitiet");
        String anh1= getIntent().getStringExtra("anh1");
        String video=getIntent().getStringExtra("video");
        String id=getIntent().getStringExtra("id");
        String lat=getIntent().getStringExtra("lat");
        String lon=getIntent().getStringExtra("lon");
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DetailActivity.this,ggmapActivity.class);
                i.putExtra("lat",lat);
                i.putExtra("lon",lon);
                i.putExtra("ten",ten);
                startActivity(i);
            }
        });
        txtmota.setText(mota);
        txtten.setText(ten);
        txtmotachitiet.setText(motachitiet);
        Picasso.get()
                .load(anh1)
                .fit()
                .into(imganh1);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("danhlam")
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    videoId = documentSnapshot.getString("video");
                    if (videoId != null) {
                        getLifecycle().addObserver(youTubePlayerView);
                        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                            youTubePlayer.cueVideo(videoId, 0f);
                        });
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Get video ID failed.", e));
    }
}