package com.example.kidsbrain.activity.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kidsbrain.R;

public class VideoPlay extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    private ProgressBar progressBar;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    Uri uri;
    public VideoPlay() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.videoProgress);
        Bundle b = getIntent().getExtras();
        String url ="" ;
        if(b != null){
            url = b.getString("url");
            Uri ur =  Uri.parse(url);
            initializePlayer(ur);
            progressBar.setVisibility(View.VISIBLE);
            if(savedInstanceState != null){
                mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
            }
            mediaController = new MediaController(this);
            mediaController.setMediaPlayer(videoView);
            videoView.setMediaController(mediaController);
        }
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                if(videoView.isPlaying()){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(r,0);
    }
    private void initializePlayer(Uri videoUri){
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if(mCurrentPosition > 0){
                            videoView.seekTo(mCurrentPosition);
                        }else{
                            videoView.seekTo(1);
                        }
                    }
                }
        );
        videoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        videoView.seekTo(0);
                        finish();
                    }
                }
        );
    }
    private void releasePlayer(){
        videoView.stopPlayback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            videoView.pause();
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
        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }
}
