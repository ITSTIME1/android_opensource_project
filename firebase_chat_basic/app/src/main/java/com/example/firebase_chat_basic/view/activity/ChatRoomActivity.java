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

// @TODO ChatRecyclerAdapter 로 부터 전달되어진 Intent 받고 ChatRoomActivity RecyclerView ChatList Item 연결 후 BindCustomAdapter 로 ChatRecyclerView 연결.

public class ChatRoomActivity extends AppCompatActivity implements BaseInterface{
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private DatabaseReference databaseReference;
    private ActivityChatroomBinding activityChatroomBinding;
    private String getChatRoomName;

    public String getChatFragmentName;
    private String getChatFragmentChatKey;
    private String getChatFragmentOtherKey;
    private String getChatFragmentCurrentUserKey;

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

        if(getChatFragmentChatKey.isEmpty()) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // 채팅키를 받아왔는데 만약에 채팅키가 없다라고 한다면 하나를 무조건적으로 생성.
                    getChatFragmentChatKey = "1";
                    if(snapshot.hasChild("chat")) {
                        getChatFragmentChatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
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
                editor.putString("ChatKey", getChatFragmentChatKey);
                editor.apply();


                databaseReference.child("chat").child(getChatFragmentChatKey).child("user_1").setValue(getChatFragmentCurrentUserKey);
                databaseReference.child("chat").child(getChatFragmentChatKey).child("user_2").setValue(getChatFragmentOtherKey);
                databaseReference.child("chat").child(getChatFragmentChatKey).child("message").child(currentTimeStamp).child("msg").setValue(getSendText);
                databaseReference.child("chat").child(getChatFragmentChatKey).child("message").child(currentTimeStamp).child("Sender").setValue(getChatFragmentCurrentUserKey);
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
        getChatFragmentName = intent.getStringExtra("getChatFragmentName");
        // 채팅방 내역이 없을 경우 chatKey 값은 들어오지 않는다.
        // chatKey는 String 타입으로 들어온다.
        getChatFragmentChatKey = intent.getStringExtra("getChatFragmentChatKey");
        getChatFragmentOtherKey = intent.getStringExtra("getChatFragmentOtherKey");
        getChatFragmentCurrentUserKey = intent.getStringExtra("getChatFragmentCurrentUserKey");


        Log.d("getChatFragmentLayoutData - ( ChatRoomActivity ) 에서 값을 잘 ( 전달 받았습니다. )",
                getChatFragmentName + "\n" +
                        getChatFragmentChatKey + "\n" +
                        getChatFragmentOtherKey + "\n" +
                        getChatFragmentCurrentUserKey);
    }

}
