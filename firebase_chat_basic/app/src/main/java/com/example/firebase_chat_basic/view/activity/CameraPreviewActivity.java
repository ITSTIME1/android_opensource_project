package com.example.firebase_chat_basic.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.ActivityCameraPreviewBinding;
import com.example.firebase_chat_basic.model.ChatRoomImageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * [CameraPreviewActivity]
 *
 * <Topic>
 *
 *     This activity shows previewImage
 *     previewImage get imageUri from "Camera2Activity"
 *
 * </Topic>
 *
 */

// @TODO 카메라 프리뷰 시간 된다면 preview 화면 제작
public class CameraPreviewActivity extends AppCompatActivity implements BaseInterface {
    private ActivityCameraPreviewBinding activityCameraPreviewBinding;
    private DatabaseReference databaseReference;
    private Uri getImageURI;
    private String get_chat_key, get_other_uid, get_current_my_uid;
    private int maxMessageKey;

    private final Handler mHandler = new Handler();
    private final Date now_date = new Date();
    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
    private final String set_date = simpleDateFormat.format(now_date);
    private final String current_date = currentDateFormat.format(now_date);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCameraPreviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera_preview);
        activityCameraPreviewBinding.setCameraPreviewActivity(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
        default_init();
        get_data_intent();
        getMessageKey();
    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
    }

    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent getCameraXIntent = getIntent();
        get_chat_key = getCameraXIntent.getStringExtra("get_chat_key");
        getImageURI = Uri.parse(getCameraXIntent.getStringExtra("getImageUri"));
        get_other_uid = getCameraXIntent.getStringExtra("get_other_uid");
        get_current_my_uid = getCameraXIntent.getStringExtra("get_current_my_uid");

        Glide.with(getBaseContext()).load(getImageURI).into(activityCameraPreviewBinding.galleryImageView);
    }

    // get maxMessageKey from "message list"
    public void getMessageKey(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("chat")) {
                    ArrayList<Integer> messageKeyList = new ArrayList<>();
                    for(DataSnapshot dataSnapshot : snapshot.child("chat").getChildren()) {
                        String key_check = dataSnapshot.getKey();
                        Log.d("dataSnapshot", String.valueOf(key_check));
                        // get_chat_key 값이랑 동일하다면
                        if (key_check != null && key_check.equals(get_chat_key)) {
                            for (DataSnapshot messageKeySnapShot : dataSnapshot.child("message").getChildren()) {
                                messageKeyList.add(Integer.valueOf(Objects.requireNonNull(messageKeySnapShot.getKey())));
                                Log.d("messageKeySnapshot", String.valueOf(messageKeySnapShot.getKey()));
                            }
                        }
                    }
                    maxMessageKey = Collections.max(messageKeyList);
                } else {
                    Log.d("don't have chat", "");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void previewCancel() {
        finish();
    }
}
