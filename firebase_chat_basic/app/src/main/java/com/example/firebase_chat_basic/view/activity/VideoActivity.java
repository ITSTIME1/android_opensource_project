package com.example.firebase_chat_basic.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityVideoBinding;

import java.util.ArrayList;


/**
 * [VideoActivity]
 *
 * VideoActivity gets URI data from "ChatRoomBottomSheetDialog"
 * then VideoActivity use "setMediaController" because There is a possibility that it will be stopped
 * and if videoActivity is deleted from memory then it is stopped.
 *
 */
public class VideoActivity extends AppCompatActivity implements BaseInterface {
    private ActivityVideoBinding activityVideoBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        default_init();
        get_data_intent();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent getVideoIntent = getIntent();
        Uri videoURI = getVideoIntent.getParcelableExtra("videoIntent");
        Log.d("videoURI", String.valueOf(videoURI));
        activityVideoBinding.videoView.setMediaController(new MediaController(this));
        activityVideoBinding.videoView.setVideoURI(videoURI);

        // if video prepared to start video
        activityVideoBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                activityVideoBinding.videoView.start();
            }
        });
    }

    // if screen is nothing or black screen
    @Override
    protected void onPause() {
        super.onPause();
        //비디오 일시 정지
        if(activityVideoBinding.videoView.isPlaying()) {
            activityVideoBinding.videoView.pause();
        }
    }
    // when activity is deleted from memory
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityVideoBinding.videoView.stopPlayback();
    }



    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        activityVideoBinding.setVideoActivity(this);
    }
}
