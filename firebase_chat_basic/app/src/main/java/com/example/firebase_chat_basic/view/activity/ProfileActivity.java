package com.example.firebase_chat_basic.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements BaseInterface {
    private ActivityProfileBinding activityProfileBinding;
    private String client_profile_image;
    private String client_background_image;
    private String client_state_message;
    private String client_name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        defaultInit();
        activityProfileBinding.profileName.setText(client_name);


        ImageView chat = activityProfileBinding.chatImageGif;
        GlideDrawableImageViewTarget chat_gif_image = new GlideDrawableImageViewTarget(chat);
        Glide.with(this).load(R.raw.iconmessage).into(chat_gif_image);

        ImageView contact = activityProfileBinding.contactImageGif;
        GlideDrawableImageViewTarget contact_gif_image = new GlideDrawableImageViewTarget(contact);
        Glide.with(this).load(R.raw.iconcontact).into(contact_gif_image);

        ImageView save = activityProfileBinding.saveImageGif;
        GlideDrawableImageViewTarget save_gif_image = new GlideDrawableImageViewTarget(save);
        Glide.with(this).load(R.raw.iconsave).into(save_gif_image);

    }


    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        Intent intent = getIntent();
        // 이름, 프로필 이미지, 배경 이미지, 상태 메세지
        client_name = intent.getStringExtra("client_name");
        client_profile_image = intent.getStringExtra("client_profile_image");
        client_background_image = intent.getStringExtra("client_background_image");
        client_state_message = intent.getStringExtra("client_state_message");
    }
}
