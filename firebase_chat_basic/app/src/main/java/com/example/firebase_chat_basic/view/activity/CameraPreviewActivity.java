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
 *     This activity shows previewImage
 *     previewImage get imageUri from "Camera2Activity"
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




    // 이미지를 넣을때 우선 chatroom activity 에 보여주어야 하니까
    // chatroom activity 의 viewtype 을 두개로 나누어서 이미지를 보낼때, 메세지를 보낼때로 나누어서
    // 각각 다른 UI를 보여준다.

    // message 보낼때는 viewtype = 0
    // image 보낼때는 viewtype = 1
    // 여기서 이미지를 보내는 거니까 model 로 만들어서 image

    // send image
    public void send_image_btn(){
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                // chatRoomImageModel object
                // chatImageViewType = 1
                // getImageURI
                // current_date
                ChatRoomImageModel chatRoomImageModel = new ChatRoomImageModel(getImageURI.toString(), Constants.chatImageViewType);
                // imageURI 에 URI 를 저장.
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("imageURI").setValue(chatRoomImageModel.getImageUrl());
                // getChatRoomViewType
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("viewType").setValue(chatRoomImageModel.getChatRoomViewType());
                // msg 에 키 값 저장
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("mineKey").setValue(get_current_my_uid);
                // msg 에 시간 저장
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("save_chat_date").setValue(set_date);
                // msg 에 날짜 저장
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("currentDate").setValue(current_date);
                // 보낸 사람 저장
                databaseReference.child("chat").child(get_chat_key).child("보낸사람").setValue(get_current_my_uid);
                // 받은 사람 저장
                databaseReference.child("chat").child(get_chat_key).child("받은사람").setValue(get_other_uid);
                Log.d("잘 넣어줌", "");
            }
        }, 100); // 0.5초후
        finish();
    }


    public void previewCancel() {
        finish();
    }
}
