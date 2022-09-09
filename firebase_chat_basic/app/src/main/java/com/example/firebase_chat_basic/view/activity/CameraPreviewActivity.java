package com.example.firebase_chat_basic.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    private String getChatKey, getOtherUID, getMyUID;
    private int maxMessageKey;
    private final Date nowDate = new Date();
    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
    private final String setDate = simpleDateFormat.format(nowDate);
    private final String currentDate = currentDateFormat.format(nowDate);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCameraPreviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera_preview);
        activityCameraPreviewBinding.setCameraPreviewActivity(this);
        initialize();
        getDataIntent();
        getMessageKey();
    }

    @Override
    public void initialize() {
        BaseInterface.super.initialize();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
    }

    @Override
    public void getDataIntent() {
        BaseInterface.super.getDataIntent();
        Intent getCameraXIntent = getIntent();
        getChatKey = getCameraXIntent.getStringExtra("getChatPrivateKey");
        getImageURI = Uri.parse(getCameraXIntent.getStringExtra("getImageUri"));
        getOtherUID = getCameraXIntent.getStringExtra("getOtherUID");
        getMyUID = getCameraXIntent.getStringExtra("getMyUID");
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
                        // getChatPrivateKey 값이랑 동일하다면
                        if (key_check != null && key_check.equals(getChatKey)) {
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
    // @TODO 메세지 선택 후 저장.

    public void send_image() {
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("imageURI").setValue(getImageURI.toString());
        // getChatRoomViewType
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("viewType").setValue(Constants.chatImageViewType);
        // msg 에 키 값 저장
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("mineKey").setValue(getMyUID);
        // msg 에 시간 저장
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("save_chat_date").setValue(setDate);
        // msg 에 날짜 저장
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("currentDate").setValue(currentDate);
        // 보낸 사람 저장
        databaseReference.child("chat").child(getChatKey).child("보낸사람").setValue(getMyUID);
        // 받은 사람 저장
        databaseReference.child("chat").child(getChatKey).child("받은사람").setValue(getOtherUID);
        finish();
    }


    public void previewCancel() {
        finish();
    }
}
