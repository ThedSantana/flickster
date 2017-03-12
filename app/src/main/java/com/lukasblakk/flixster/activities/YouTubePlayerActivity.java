package com.lukasblakk.flixster.activities;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.lukasblakk.flixster.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YouTubePlayerActivity extends YouTubeBaseActivity {

    @BindView(R.id.player) YouTubePlayerView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);
        ButterKnife.bind(this);

        YouTubePlayerView youTubePlayerView = player;

        youTubePlayerView.initialize("AIzaSyDXOS_LJWa-rzvsd9OQ9WehGNMhoUmTxMo\t",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // get the key for the video to play from the Intent
                        String youTubeKey = getIntent().getStringExtra("youTubeKey");
                        Boolean autoplay = getIntent().getBooleanExtra("autoplay", false);
                        if ( autoplay == true){
                            youTubePlayer.loadVideo(youTubeKey);
                        } else {
                            youTubePlayer.cueVideo(youTubeKey);
                        }
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
