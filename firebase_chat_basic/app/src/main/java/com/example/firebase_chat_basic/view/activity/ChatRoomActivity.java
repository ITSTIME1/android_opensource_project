package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
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
import com.example.firebase_chat_basic.adapters.ChatRoomRecyclerAdapter;
import com.example.firebase_chat_basic.databinding.ActivityChatroomBinding;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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


    // message adapter
    private ChatRoomRecyclerAdapter chatRoomRecyclerAdapter;
    private ArrayList<ChatRoomModel> chatRoomModelArrayList;



    public String getGetOtherName() {
        return getOtherName;
    }

    public ChatRoomRecyclerAdapter getChatRoomRecyclerAdapter() {
        return chatRoomRecyclerAdapter;
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

    // chatRoomActivity 에 진입했을 때 메세지를 불러올 메서드.
    public void getMessageList(){};

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
                    // msg 에 키 값 저장
                    databaseReference.child("chat").child(getChatKey).child("message").child(String.valueOf(dateTime)).child("mineKey").setValue(getCurrentMyUID);
                    // 보낸 사람 저장
                    databaseReference.child("chat").child(getChatKey).child("보낸사람").setValue(getCurrentMyUID);
                    // 받은 사람 저장
                    databaseReference.child("chat").child(getChatKey).child("받은사람").setValue(getOtherUID);


                    editor.putLong("chatDateTime", dateTime);
                    editor.commit();

                    // 1. 메세지를 누를때
                    // 메세지 리스트 추가
                    // 보내는 사람의 키 값을 확인하고
                    // 키 값이 맞는지 본다음에
                    // 메세지를 보내는 순간 리스트에 추가가 되니
                    // 그 메세지가 adapter에 보이게 되고
                    // key 값에 따라서 layout에 표시되는게 다를것이다.
                    // 눌렀을 때 메세지 리스트를 추가 해주고

                    // 메세지를 읽어오는데 내가 보낸 메세지를 가지고
                    setMessageList();
                }
            }
        });
    }

    public void setMessageList(){
        // 키 값을 확인하기 위해서 Chat 의 있는 정보를 가지고 온다.
        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot chatSnapShot : snapshot.getChildren()) {
                    // 메세지를 가지고 있다면
                    if(chatSnapShot.hasChild("message")) {
                        // message 를 가지고 온다.
                        for(DataSnapshot messageSnapShot : chatSnapShot.child("message").getChildren()) {
                            // 메세지를 가지고 와서 그 메세지 값들이 메세지와 나의 키 값이 있다면
                            // 메시지, 보낸 사람의 키 값, dateTime
                            if(messageSnapShot.hasChild("msg") && messageSnapShot.hasChild("mineKey")) {
                                final String setListMessage = messageSnapShot.child("msg").getValue(String.class);
                                final String setKey = messageSnapShot.child("mineKey").getValue(String.class);

                                // 보내는 시간을 기준으로 함.
                                Date nowDate = new Date();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "오후" + " HH:mm");
                                // 포맷 지정
                                String setDate = simpleDateFormat.format(nowDate);
                                chatRoomModelArrayList.add(new ChatRoomModel(setKey, setListMessage, setDate));
                                chatRoomRecyclerAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        activityChatroomBinding.setLifecycleOwner(this);
        if(chatRoomModelArrayList == null && chatRoomRecyclerAdapter == null) {
            chatRoomModelArrayList = new ArrayList<>();
            chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(chatRoomModelArrayList, getBaseContext());
        }

        preferences = getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        editor = preferences.edit();

    }

    // get data (Chat Recycler Adapter, Profile Activity)
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
