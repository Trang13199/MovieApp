package com.mytrang.moviesapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mytrang.moviesapplication.model.Movies;
import com.squareup.picasso.Picasso;

public class DescriptionActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    ImageView imageView, imagelike;
    TextView title, luotxem, category, actor, director, manufacture,
            thoiluong, description, toolbar_title, next, like;
    String nameYoutube;
//    Toolbar toolbar;


    String API_KEY ="AIzaSyDhvRkLLyQZYKG4SnnzbsBF06WGKHma_gw";
    int REQUEST_VIDEO = 123;
    YouTubePlayerView youTubePlayerView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        anhXa();

        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            return;
        }
        Movies movies = (Movies) bundle.get("abc");
        String image = movies.getImage();
        Picasso.get().load(image).into(imageView);

        String name = movies.getTitle();

        if(name.contains("/")){
            String[] arr = name.split("/");
            for (int i =0; i<arr.length;i++){
                toolbar_title.setText(arr[0]);
                title.setText(arr[i]);
            }
        }else{
            toolbar_title.setText(movies.getTitle());
            title.setText(movies.getTitle());
        }

        nameYoutube = movies.getLink();
        String [] youtube = nameYoutube.split("=");
        for(int i = 0; i<youtube.length;i++){
            nameYoutube = youtube[i];
        }

//        luotxem.setText(movies.getViews());
        category.setText("Genres: "+movies.getCategory());
        actor.setText("Actor: "+movies.getActor());
        director.setText("Director: "+movies.getDirector());
        manufacture.setText("Manufacturer: "+movies.getManufacturer());
        thoiluong.setText("Thời lượng phim: "+movies.getDuration()+ " minute");


        next.setText("Xem thêm");
        String des = movies.getDescription();
        if(des.length()>200){
            description.setText(des.substring(0,200)+"...");
        }else{
        next.setText(null);
        description.setText(des);}

        toolbar_title = findViewById(R.id.toolbar_title);

        youTubePlayerView.initialize(API_KEY, this);

        next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            description.setText(movies.getDescription());
            next.setText(null);
        }
    });

    imagelike.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            like.setText("Đã thích");
            like.setTextColor(Color.parseColor("#F43D04"));

            imagelike.setImageResource(R.drawable.ic_like_orange);
        }
    });

    }

    private void anhXa() {

        imageView = findViewById(R.id.img_des);
        title = findViewById(R.id.name_des);
        luotxem = findViewById(R.id.txt_vote);
        category = findViewById(R.id.txt_category);
        actor = findViewById(R.id.txt_actor);
        director = findViewById(R.id.txt_director);
        manufacture = findViewById(R.id.manufacture);
        thoiluong = findViewById(R.id.txt_thoiluong);
        description = findViewById(R.id.txt_description);

        imagelike = findViewById(R.id.image_like);
        like = findViewById(R.id.like);

        next = findViewById(R.id.next);
        youTubePlayerView = findViewById(R.id.youtube);
        toolbar_title = findViewById(R.id.toolbar_title);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(nameYoutube);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(DescriptionActivity.this, REQUEST_VIDEO);
        }else{
            Toast.makeText(this, "ERROR!!!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode== REQUEST_VIDEO){
            youTubePlayerView.initialize(API_KEY, DescriptionActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}