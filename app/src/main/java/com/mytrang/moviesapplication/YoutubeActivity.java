package com.mytrang.moviesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String API_KEY ="AIzaSyDhvRkLLyQZYKG4SnnzbsBF06WGKHma_gw";
    int REQUEST_VIDEO = 123;
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubePlayerView = findViewById(R.id.youtube);
        youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo("W4P8gl4dnrg");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
if(youTubeInitializationResult.isUserRecoverableError()){
    youTubeInitializationResult.getErrorDialog(YoutubeActivity.this, REQUEST_VIDEO);
}else{
    Toast.makeText(this, "ERROR!!!!", Toast.LENGTH_LONG).show();
}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== REQUEST_VIDEO){
            youTubePlayerView.initialize(API_KEY, YoutubeActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}