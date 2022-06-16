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

// @TODO ChatRecyclerAdapter 로 부터 전달되어진 Intent 받고 ChatRoomActivity RecyclerView ChatList Item 연결 후 BindCustomAdapter 로 ChatRecyclerView 연결.

public class ChatRoomActivity extends AppCompatActivity implements BaseInterface{
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private DatabaseReference databaseReference;
    private ActivityChatroomBinding activityChatroomBinding;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Long dateTime;
    private Timestamp timestamp;

    // 상대방 이름
    String getOtherName;
    // 상대방 UID
    String getOtherUID;
    // 나의 UID
    String getCurrentMyUID;


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


    // sendMessage method.
    // @TODO 채팅방이 두개가 생기는 이유가 뭘까
    // @TODO 다른 채팅방 생기는 이유를 고쳐야 함.
    public void sendMessage(){

        activityChatroomBinding.chatRoomSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이쪽으로 레이아웃을 터치해서 받은 상대방의 UID 값을 넘겨받음.

                final String getChatRoomKey = getOtherUID;
                String receiver_key = getOtherUID;
                String sender_key = getCurrentMyUID;

                String getText = activityChatroomBinding.chatRoomTextField.getText().toString();

                // 마지막 text 저장.

                if(!getText.isEmpty()) {
                    // Text가 비어있지 않다면 putString 에 getText 값을 저장해주고.
                    editor.putString("putSendText", getText);
                    Toast.makeText(ChatRoomActivity.this, "보내실 메세지가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                // 시간을 밀리세컨즈 단위로 변경.
                dateTime = System.currentTimeMillis();
                // getLastCommentKey 값으로 저장.
                editor.putLong("getLastCommentKey", dateTime);

                // 보내는 시간을 날짜 + 시간으로 나타내기 위해서 timeStamp 변수 운영.
                timestamp = new Timestamp(dateTime);
                // 적용
                editor.apply();

                // 유저 필드를 생성.
                // 실시간 키값 생성해서 이전 키값에 저장.
                // chatRoomUID = 상대방 UID
                databaseReference.child("chat").child(getChatRoomKey).child("sender_user").setValue(sender_key);
                databaseReference.child("chat").child(getChatRoomKey).child("receiver_user").setValue(receiver_key);
                // String 값으로 실시간으로 현재 시간의 millis 가 들어감. 거기 안에
                databaseReference.child("chat").child(getChatRoomKey).child("comments").child(String.valueOf(dateTime)).child("msg").setValue(getText);
                // 현재 날짜도 포함시켜 준다.
                databaseReference.child("chat").child(getChatRoomKey).child("comments").child(String.valueOf(dateTime)).child("currentDate").setValue(timestamp.toString());


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
        getOtherUID = getIntent.getStringExtra("getOtherUID");
        getCurrentMyUID = getIntent.getStringExtra("getCurrentMyUID");
    }


}
