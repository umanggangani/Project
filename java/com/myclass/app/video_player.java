package com.myclass.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class video_player extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    String video_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        video_name=getIntent().getExtras().getString("link");
        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.setLongClickable(false);
        youTubePlayerView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                return true;
            }
        });
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener()
        {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer)
            {
                String videoId = "BSckZGliL-Y";
                youTubePlayer.loadVideo(video_name, 0);

            }
            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality)
            {
                super.onPlaybackQualityChange(youTubePlayer, PlayerConstants.PlaybackQuality.SMALL);
            }
        });
        youTubePlayerView.enterFullScreen();
    }


}