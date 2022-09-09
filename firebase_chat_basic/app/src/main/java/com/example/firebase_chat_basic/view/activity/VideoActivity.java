package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.ActivityVideoBinding;
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
 * [VideoActivity]
 *
 * <Topic>
 *
 *     VideoActivity gets URI data from "ChatRoomBottomSheetDialog"
 *     then VideoActivity use "setMediaController" because There is a possibility that it will be stopped
 *     and if videoActivity is deleted from memory then it is stopped.
 *
 * </Topic>
 *
 */
public class VideoActivity extends AppCompatActivity implements BaseInterface {
    private ActivityVideoBinding activityVideoBinding;
    private DatabaseReference databaseReference;
    private String getChatKey;
    private String getOtherUID;
    private String getMyUID;
    private int maxMessageKey;
    private Uri videoURI;
    private final Date nowDate = new Date();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
    private final String set_date = simpleDateFormat.format(nowDate);
    private final String current_date = currentDateFormat.format(nowDate);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        initialize();
        getDataIntent();
        getMessageKey();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getDataIntent() {
        BaseInterface.super.getDataIntent();
        Intent getVideoIntent = getIntent();
        videoURI = getVideoIntent.getParcelableExtra("videoIntent");
        getChatKey = getVideoIntent.getStringExtra("getChatPrivateKey");
        getOtherUID = getVideoIntent.getStringExtra("getOtherUID");
        getMyUID = getVideoIntent.getStringExtra("getMyUID");

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

    public void send_video() {
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("videoURL").setValue(videoURI.toString());
        // getChatRoomViewType
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("viewType").setValue(Constants.chatVideoViewType);
        // msg 에 키 값 저장
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("mineKey").setValue(getMyUID);
        // msg 에 시간 저장
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("save_chat_date").setValue(set_date);
        // msg 에 날짜 저장
        databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(maxMessageKey + 1)).child("currentDate").setValue(current_date);
        // 보낸 사람 저장
        databaseReference.child("chat").child(getChatKey).child("보낸사람").setValue(getMyUID);
        // 받은 사람 저장
        databaseReference.child("chat").child(getChatKey).child("받은사람").setValue(getOtherUID);
        finish();
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

    @Override
    public void initialize() {
        BaseInterface.super.initialize();
        activityVideoBinding.setVideoActivity(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
    }

    public void onBackKey(){
        finish();
    }


    // when activity is deleted from memory
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityVideoBinding.videoView.stopPlayback();
    }
}
