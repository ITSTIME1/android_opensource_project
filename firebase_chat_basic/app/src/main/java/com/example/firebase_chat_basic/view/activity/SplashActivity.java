package com.example.firebase_chat_basic.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding activitySplashBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        ImageView splash_icon = activitySplashBinding.splashIcon;
        GlideDrawableImageViewTarget splash_gif_image = new GlideDrawableImageViewTarget(splash_icon);
        Glide.with(this).load(R.raw.splashicon).into(splash_gif_image);

    }
}
