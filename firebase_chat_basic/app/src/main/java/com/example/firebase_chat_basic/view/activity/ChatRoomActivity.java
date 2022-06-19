package com.example.firebase_chat_basic.view.activity;
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
import com.example.firebase_chat_basic.databinding.ActivityChatroomBinding;
import com.example.firebase_chat_basic.viewModel.ChatRoomViewModel;
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
        if(getChatKey.isEmpty()) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    getChatKey = "1";
                    if(snapshot.hasChild("chat")) {
                        getChatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        sendMessage();
        onBackPressed();
    }


    public void sendMessage(){
        activityChatroomBinding.chatRoomSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이쪽으로 레이아웃을 터치해서 받은 상대방의 UID 값을 넘겨받음.
                String receiver_key = getOtherUID;
                Log.d("receiver_key ", receiver_key);
                String sender_key = getCurrentMyUID;
                Log.d("sender_key ", sender_key);
                String getText = activityChatroomBinding.chatRoomTextField.getText().toString();

                if(!getText.isEmpty()) {
                    editor.putString("putSendText", getText);
                }
                // 시간을 밀리세컨즈 단위로 변경.
                dateTime = System.currentTimeMillis();
                // getLastCommentKey 값으로 저장.
                editor.putLong("getLastCommentKey", dateTime);
                timestamp = new Timestamp(dateTime);
                editor.apply();


                databaseReference.child("chat").child(getChatKey).child("sender_user").setValue(sender_key);
                databaseReference.child("chat").child(getChatKey).child("receiver_user").setValue(receiver_key);
                // String 값으로 실시간으로 현재 시간의 millis 가 들어감. 거기 안에
                databaseReference.child("chat").child(getChatKey).child("comments").child(String.valueOf(dateTime)).child("msg").setValue(getText);
                // 현재 날짜도 포함시켜 준다.
                databaseReference.child("chat").child(getChatKey).child("comments").child(String.valueOf(dateTime)).child("currentDate").setValue(timestamp.toString());


                Toast.makeText(ChatRoomActivity.this, "메세지가 전상적으로 전송 되었습니다.", Toast.LENGTH_LONG).show();
                // 보내고 난 뒤에 메세지를 초기화.
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
        getChatKey = getIntent.getStringExtra("getChatKey");
        getCurrentMyUID = getIntent.getStringExtra("getCurrentMyUID");
        getOtherUID = getIntent.getStringExtra("getOtherUID");
    }
}
