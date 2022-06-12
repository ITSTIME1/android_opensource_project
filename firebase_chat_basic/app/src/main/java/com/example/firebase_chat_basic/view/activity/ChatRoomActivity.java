package com.example.firebase_chat_basic.view.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

// @TODO ChatRecyclerAdapter 로 부터 전달되어진 Intent 받고 ChatRoomActivity RecyclerView ChatList Item 연결 후 BindCustomAdapter 로 ChatRecyclerView 연결.

public class ChatRoomActivity extends AppCompatActivity implements BaseInterface{
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private DatabaseReference databaseReference;
    private ActivityChatroomBinding activityChatroomBinding;
    private String getChatRoomName;

    public String dName;
    private String dChatKey;
    private String dGeyKey;
    private String dGetChatFragmentUID;

    SharedPreferences pref;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatroomBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatroom);
        defaultInit();
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        testing();

        if(dChatKey.isEmpty()) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // 채팅키를 받아왔는데 만약에 채팅키가 없다라고 한다면 하나를 무조건적으로 생성.
                    dChatKey = "1";
                    if(snapshot.hasChild("chat")) {
                        dChatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error);

                }
            });
        }



        activityChatroomBinding.chatRoomSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getSendText = activityChatroomBinding.chatRoomTextField.getText().toString();


                // user_1 = 내꺼 uid
                // user_2 = 연결하고자 하는 상대방의 uid

                final String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                editor.putString("CurrentStamp", currentTimeStamp);
                editor.putString("ChatKey", dChatKey);
                editor.apply();


                databaseReference.child("chat").child(dChatKey).child("user_1").setValue(dGetChatFragmentUID);
                databaseReference.child("chat").child(dChatKey).child("user_2").setValue(dGeyKey);
                databaseReference.child("chat").child(dChatKey).child("message").child(currentTimeStamp).child("msg").setValue(getSendText);
                databaseReference.child("chat").child(dChatKey).child("message").child(currentTimeStamp).child("getChatFragmentUID").setValue(dGetChatFragmentUID);
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
    }

    public void testing(){
        Intent intent = getIntent();
        dName = intent.getStringExtra("dName");
        dChatKey = intent.getStringExtra("dChatKey");
        dGeyKey = intent.getStringExtra("dGeyKey");
        dGetChatFragmentUID = intent.getStringExtra("dGetChatFragmentUID");

        System.out.println(dName + "\n" + dChatKey + "\n" + dGeyKey + "\n" + "이름" + "채팅방 키" + "터치한 타인키");
    }

}
