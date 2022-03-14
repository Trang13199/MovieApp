package com.mytrang.moviesapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
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
            thoiluong, description, toolbar_title, next, like, back;
    String nameYoutube, id;
    
    String API_KEY = "AIzaSyDhvRkLLyQZYKG4SnnzbsBF06WGKHma_gw";
    int REQUEST_VIDEO = 123;
    YouTubePlayerView youTubePlayerView;

    SharedPreferences preferences;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        anhXa();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Movies movies = (Movies) bundle.get("abc");
        String image = movies.getImage();
        Picasso.get().load(image).into(imageView);

        id = movies.getId().toString();

        String name = movies.getTitle();

        if (name.contains("/")) {
            String[] arr = name.split("/");
            for (int i = 0; i < arr.length; i++) {
                toolbar_title.setText(arr[0]);
                title.setText(arr[i]);
            }
        } else {
            toolbar_title.setText(movies.getTitle());
            title.setText(movies.getTitle());
        }

        nameYoutube = movies.getLink();
        String[] youtube = nameYoutube.split("=");
        for (int i = 0; i < youtube.length; i++) {
            nameYoutube = youtube[i];
        }

        luotxem.setText(getSpan("Lượt xem: " + movies.getViews()));
        category.setText(getSpan("Genres: " + movies.getCategory()));
        actor.setText(getSpan("Actor: " + movies.getActor()));
        director.setText(getSpan("Director: " + movies.getDirector()));
        manufacture.setText(getSpan("Manufacturer: " + movies.getManufacturer()));
        thoiluong.setText(getSpan("Thời lượng phim: " + movies.getDuration().toString() + " minute"));


        next.setText("Xem thêm");
        String des = movies.getDescription();
        if (des.length() > 180) {
            description.setText(des.substring(0, 180) + "...");
        } else {
            next.setText(null);
            description.setText(des);
        }

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
                preferences = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                boolean check = preferences.getBoolean(id, false);

                if (!check) {
                    checkRed();
                    editor.putBoolean(id, true);
                } else {
                    checkWhite();
                    editor.putBoolean(id, false);
                }
                editor.commit();
                Log.e("idDes", id);
            }

        });


        getInfo();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            Intent intent = new Intent(DescriptionActivity.this, ListActivity.class);
//            startActivity(intent);
                finish();
            }
        });
    }

    private SpannableString getSpan(String s) {
        String[] input = s.split(":");
        String view = input[0];

        SpannableString spannableString = new SpannableString(s);
        StyleSpan ss = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan fg = new ForegroundColorSpan(Color.GRAY);
        ForegroundColorSpan white = new ForegroundColorSpan(Color.WHITE);
        spannableString.setSpan(white, 0, view.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(ss, 0, view.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(fg, view.length() + 1, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private void getInfo() {
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean ok = preferences.getBoolean(id, false);
        if (ok) {

            imagelike.setImageResource(R.drawable.ic_like_orange);
            like.setText("Đã thích");
            like.setTextColor(Color.parseColor("#F43D04"));
        } else {

            imagelike.setImageResource(R.drawable.ic_like);
            like.setText("Thích");
            like.setTextColor(Color.parseColor("#FFFFFFFF"));

        }
    }

    private void checkRed() {
        imagelike.setImageResource(R.drawable.ic_like_orange);
        like.setText("Đã thích");
        like.setTextColor(Color.parseColor("#F43D04"));
    }

    private void checkWhite() {
        imagelike.setImageResource(R.drawable.ic_like);
        like.setText("Thích");
        like.setTextColor(Color.parseColor("#FFFFFFFF"));
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
        like = findViewById(R.id.txtlike);

        back = findViewById(R.id.back);


        next = findViewById(R.id.next);
        youTubePlayerView = findViewById(R.id.youtube);
        toolbar_title = findViewById(R.id.toolbar_title);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(nameYoutube);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(DescriptionActivity.this, REQUEST_VIDEO);
        } else {
            Toast.makeText(this, "ERROR!!!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_VIDEO) {
            youTubePlayerView.initialize(API_KEY, DescriptionActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}