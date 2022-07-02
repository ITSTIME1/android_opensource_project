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
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatRoomActivity extends AppCompatActivity implements BaseInterface {
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

    private boolean dataSet = false;


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


        get_from_chat_recycler_adapter();
        get_message_list();


        send_message();
        on_back_pressed();

    }

    // chatRoomActivity 에 진입했을 때 메세지를 불러올 메서드.
    public void get_message_list() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chatSnapShot : snapshot.child("chat").getChildren()) {
                    // 메세지를 가지고 있다면
                    if (chatSnapShot.hasChild("message")) {
                        chatRoomModelArrayList.clear();
                        // message 를 가지고 온다.
                        for (DataSnapshot messageSnapShot : chatSnapShot.child("message").getChildren()) {
                            // 메세지를 가지고 와서 그 메세지 값들이 메세지와 나의 키 값이 있다면
                            // 메시지, 보낸 사람의 키 값, dateTime
                            dataSet = false;
                            if (messageSnapShot.hasChild("msg") && messageSnapShot.hasChild("mineKey")) {
                                final String setListMessage = messageSnapShot.child("msg").getValue(String.class);
                                final String setKey = messageSnapShot.child("mineKey").getValue(String.class);

                                // 보내는 시간을 기준으로 함.
                                Date nowDate = new Date();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("오후" + " HH:mm");
                                // 포맷 지정
                                String setDate = simpleDateFormat.format(nowDate);
                                if(!dataSet) {
                                    dataSet = true;
                                    chatRoomModelArrayList.add(new ChatRoomModel(setKey, setListMessage, setDate));
                                    chatRoomRecyclerAdapter.notifyDataSetChanged();
                                    activityChatroomBinding.chatRoomListRec.scrollToPosition(chatRoomModelArrayList.size()-1);
                                }

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

    public void send_message() {
        activityChatroomBinding.chatRoomSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 채팅을 하는거야 그 상대방의 chatKey 값을 가지고 와서.
                final String chatText = activityChatroomBinding.chatRoomTextField.getText().toString();
                if (!chatText.isEmpty()) {
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

                }
            }
        });
    }

    public void on_back_pressed() {
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
        if (chatRoomModelArrayList == null && chatRoomRecyclerAdapter == null) {
            chatRoomModelArrayList = new ArrayList<>();
            chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(chatRoomModelArrayList, getBaseContext());
        }

        preferences = getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        editor = preferences.edit();

    }

    // get data (Chat Recycler Adapter, Profile Activity)
    public void get_from_chat_recycler_adapter() {

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
