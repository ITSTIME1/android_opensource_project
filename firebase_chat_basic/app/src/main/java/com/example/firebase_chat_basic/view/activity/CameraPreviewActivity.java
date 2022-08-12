package com.example.firebase_chat_basic.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityCameraPreviewBinding;

/**
 * [CameraPreviewActivity]
 *
 * This activity show previewImage
 * previewImage get imageUri from "Camera2Activity"
 */

// @TODO 카메라 프리뷰 시간 된다면 preview 화면 제작
public class CameraPreviewActivity extends AppCompatActivity implements BaseInterface {
    private ActivityCameraPreviewBinding activityCameraPreviewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCameraPreviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera_preview);
        activityCameraPreviewBinding.setCameraPreviewActivity(this);
        get_data_intent();
    }

    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent getIntentImage = getIntent();
        Uri getImageURI = Uri.parse(getIntentImage.getStringExtra("getImageUri"));
        Log.d("getImageURI 잘 들어왔나 확인", String.valueOf(getImageURI));
        Glide.with(getBaseContext()).load(getImageURI).into(activityCameraPreviewBinding.galleryImageView);
    }

    public void previewCancel() {
        finish();
    }
}
