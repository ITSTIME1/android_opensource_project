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
    private Timestamp timestamp;

    // 상대방 이름
    private String getOtherName;
    // 상대방 UID
    String getCombineKey;
    // 나의 UID
    String getCurrentMyUID;
    // 상대방 UId
    String getOtherUID;
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
        Log.d("getCombineKey", getCombineKey);
        sendMessage();
        onBackPressed();

    }


    public void sendMessage(){
        activityChatroomBinding.chatRoomSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chatText = activityChatroomBinding.chatRoomTextField.getText().toString();
                dateTime = System.currentTimeMillis();

                if(!getCombineKey.isEmpty()) {
                    databaseReference.child("chat").child(getCombineKey).child("comments").child(String.valueOf(dateTime)).child("msg").setValue(chatText);
                    databaseReference.child("chat").child(getCombineKey).child("comments").child(String.valueOf(dateTime)).child("currentDate").setValue(dateTime.toString());
                    editor.putString("chatLastText", chatText);
                    editor.putLong("chatDateTime", dateTime);
                    editor.apply();
                    Log.d("채팅방 저장완료", "");
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
        Intent intent = getIntent();
        activityChatroomBinding.setChatRoomActivity(this);
        preferences = getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void getFromChatRecyclerAdapter(){
        Intent getIntent = getIntent();
        getOtherName = getIntent.getStringExtra("getOtherName");
        getCombineKey = getIntent.getStringExtra("getChatKey");
        getCurrentMyUID = getIntent.getStringExtra("getCurrentMyUID");
        getOtherUID = getIntent.getStringExtra("getOtherUID");
    }
}
