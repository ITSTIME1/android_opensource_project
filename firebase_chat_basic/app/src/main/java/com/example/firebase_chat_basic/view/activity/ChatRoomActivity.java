package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.databinding.ActivityChatroomBinding;
import com.example.firebase_chat_basic.viewModel.ChatRoomViewModel;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.sql.Timestamp;

public class ChatRoomActivity extends AppCompatActivity implements BaseInterface{
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private DatabaseReference databaseReference;
    private ActivityChatroomBinding activityChatroomBinding;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Long dateTime;
    // 상대방 이름
    private String getOtherName;
    // 채팅방 키
    private String getChatKey;
    // 나의 UID
    private String getCurrentMyUID;
    // 상대방 UId
    private String getOtherUID;
    public String getGetOtherName() {
        return getOtherName;
    }


    // 두개가 다르게 저장이된다.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatroomBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatroom);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        defaultInit();
        getFromChatRecyclerAdapter();
        sendMessage();
        onBackPressed();

    }


    public void sendMessage(){
        activityChatroomBinding.chatRoomSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 채팅을 하는거야 그 상대방의 chatKey 값을 가지고 와서.
                final String chatText = activityChatroomBinding.chatRoomTextField.getText().toString();
                if(!chatText.isEmpty()) {
                    dateTime = System.currentTimeMillis();
                    // 채팅 저장
                    databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(dateTime)).child("msg").setValue(chatText);
                    // 보낸 사람 저장
                    databaseReference.child("chat").child(getChatKey).child("보낸사람").setValue(getCurrentMyUID);
                    // 받은 사람 저장
                    databaseReference.child("chat").child(getChatKey).child("받은사람").setValue(getOtherUID);

                    editor.putLong("chatDateTime", dateTime);
                    editor.commit();

                }

            }
        });
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
        activityChatroomBinding.setChatRoomActivity(this);
        preferences = getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void getFromChatRecyclerAdapter(){

        Intent getIntent = getIntent();
        getOtherName = getIntent.getStringExtra("getOtherName");
        getChatKey = getIntent.getStringExtra("getChatKey");
        getCurrentMyUID = getIntent.getStringExtra("getCurrentMyUID");
        getOtherUID = getIntent.getStringExtra("getOtherUID");

        Log.d("getOtherName", getOtherName);
        Log.d("getChatKey", getChatKey);
        Log.d("getCurrentMyUID", getCurrentMyUID);
        Log.d("getOtherUID", getOtherUID);


    }
}
