package com.example.firebase_chat_basic.view.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityChatroomBinding;
import com.example.firebase_chat_basic.viewModel.ChatRoomViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.sql.Timestamp;

// @TODO ChatRecyclerAdapter 로 부터 전달되어진 Intent 받고 ChatRoomActivity RecyclerView ChatList Item 연결 후 BindCustomAdapter 로 ChatRecyclerView 연결.

public class ChatRoomActivity extends AppCompatActivity implements BaseInterface{
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private DatabaseReference databaseReference;
    private ActivityChatroomBinding activityChatroomBinding;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Long dateTime;
    private Timestamp timestamp;


    private String getOtherName;
    private String getDate;
    private String getContent;
    private String getRefreshCount;
    private String getOtherUID;
    private String getCurrentMyUID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatroomBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatroom);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        defaultInit();
        getFromChatRecyclerAdapter();


        activityChatroomBinding.chatRoomSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이쪽으로 레이아웃을 터치해서 받은 상대방의 UID 값을 넘겨받음.
                final String chatRoomUID = getOtherUID;
                final String getMyUID = getCurrentMyUID;
                final String getText = activityChatroomBinding.chatRoomTextField.getText().toString();

                final String sender_user = getMyUID;
                final String receiver_user = getOtherUID;

                // 마지막 text 저장.

                if(!getText.isEmpty()) {
                    editor.putString("putSendText", getText);

                    dateTime = System.currentTimeMillis();
                    timestamp = new Timestamp(dateTime);
                    System.out.println("Current Time Stamp: "+ timestamp);
                    // 저장한 순간
                    editor.putLong("dateTime", dateTime);
                    editor.apply();
                }

                // 유저 필드를 생성.
                // 실시간 키값 생성해서 이전 키값에 저장.
                databaseReference.child("chat").child(chatRoomUID).child("sender_user").setValue(sender_user);
                databaseReference.child("chat").child(chatRoomUID).child("receiver_user").setValue(receiver_user);
                // String 값으로 실시간으로 현재 시간의 millis 가 들어감. 거기 안에
                databaseReference.child("chat").child(chatRoomUID).child("comments").child(String.valueOf(dateTime)).child("msg").setValue(getText);
                // 현재 날짜도 포함시켜 준다.
                databaseReference.child("chat").child(chatRoomUID).child("comments").child(String.valueOf(dateTime)).child("currentDate").setValue(timestamp.toString());
            }
        });

        onBackPressed();
    }
    public void onBackPressed(){
        activityChatroomBinding.chatRoomBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        Intent intent = getIntent();
        activityChatroomBinding.setChatRoomActivity(this);
        preferences = getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void getFromChatRecyclerAdapter(){
        Intent getIntent = getIntent();
        getOtherName = getIntent.getStringExtra("getOtherName");
        getDate = getIntent.getStringExtra("getDate");
        getContent = getIntent.getStringExtra("getContent");
        getRefreshCount = getIntent.getStringExtra("getRefreshCount");
        getOtherUID = getIntent.getStringExtra("getOtherUID");
        getCurrentMyUID = getIntent.getStringExtra("getCurrentMyUID");
    }
}
