package com.example.firebase_chat_basic.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityVideoBinding;


/**
 * [VideoActivity]
 */
public class VideoActivity extends AppCompatActivity implements BaseInterface {
    private ActivityVideoBinding activityVideoBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);



    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        Intent getVideoIntent = getIntent();
        getVideoIntent.getSerializableExtra("videoIntent");
        Log.d("비디오 받기 성공 ", getVideoIntent.getDataString());
    }
}
