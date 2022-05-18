package com.example.kidsbrain.activity.video;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.brightcove.player.view.BrightcovePlayer;
import com.example.kidsbrain.R;

public class VideoPlay extends BrightcovePlayer {
    BrightcoveExoPlayerVideoView brightcoveExoPlayerVideoView;
    private ProgressBar progressBar;
    private Long mCurrentPosition = new Long(0);
    private static final String PLAYBACK_TIME = "play_time";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        brightcoveVideoView = (BrightcoveExoPlayerVideoView) findViewById(R.id.brightcove_video_view);
        progressBar = findViewById(R.id.videoProgress2);
        Bundle b = getIntent().getExtras();
        String url ="" ;
        if(b != null) {
            url = b.getString("url");
            progressBar.setVisibility(View.VISIBLE);
            if(savedInstanceState != null){
                mCurrentPosition = savedInstanceState.getLong(PLAYBACK_TIME);
            }
//            initializePlayer(url);
            Video video = Video.createVideo(url, DeliveryType.MP4);
            brightcoveVideoView.add(video);
            if(mCurrentPosition > 0){
                brightcoveVideoView.seekTo(mCurrentPosition);
            }else{
                brightcoveVideoView.seekTo(new Long(1));
            }
            brightcoveVideoView.start();

        }
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                if(brightcoveVideoView.isPlaying()){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(r,0);
    }

    private void releasePlayer(){
        brightcoveVideoView.stopPlayback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            brightcoveVideoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_TIME, brightcoveVideoView.getCurrentPositionLong());
    }

}
