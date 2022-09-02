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
    private String get_chat_key;
    private String get_other_uid;
    private String get_current_my_uid;
    private int maxMessageKey;
    private Uri videoURI;
    private final Date now_date = new Date();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
    private final String set_date = simpleDateFormat.format(now_date);
    private final String current_date = currentDateFormat.format(now_date);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        default_init();
        get_data_intent();
        getMessageKey();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent getVideoIntent = getIntent();
        videoURI = getVideoIntent.getParcelableExtra("videoIntent");
        get_chat_key = getVideoIntent.getStringExtra("get_chat_key");
        get_other_uid = getVideoIntent.getStringExtra("get_other_uid");
        get_current_my_uid = getVideoIntent.getStringExtra("get_current_my_uid");

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
    // @TODO exoplayer 사용
    // @TODO DASH 스트리밍 방식 사용
    // exoplayer 를 사용해서 viewtype 을 다르게 지정해
    // recyclerview 로 보내줄 때 미리 정의한 레이아웃에 맞게 보여준다.
    public void send_video() {

        databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey + 1)).child("videoURL").setValue(videoURI.toString());
        // getChatRoomViewType
        databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey + 1)).child("viewType").setValue(Constants.chatVideoViewType);
        // msg 에 키 값 저장
        databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey + 1)).child("mineKey").setValue(get_current_my_uid);
        // msg 에 시간 저장
        databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey + 1)).child("save_chat_date").setValue(set_date);
        // msg 에 날짜 저장
        databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey + 1)).child("currentDate").setValue(current_date);
        // 보낸 사람 저장
        databaseReference.child("chat").child(get_chat_key).child("보낸사람").setValue(get_current_my_uid);
        // 받은 사람 저장
        databaseReference.child("chat").child(get_chat_key).child("받은사람").setValue(get_other_uid);
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
    public void default_init() {
        BaseInterface.super.default_init();
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
