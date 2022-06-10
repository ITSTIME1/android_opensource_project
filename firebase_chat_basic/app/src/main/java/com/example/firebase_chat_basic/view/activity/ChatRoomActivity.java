package com.example.firebase_chat_basic.view.activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityChatroomBinding;


// @TODO ChatRecyclerAdapter 로 부터 전달되어진 Intent 받고 ChatRoomActivity RecyclerView ChatList Item 연결 후 BindCustomAdapter 로 ChatRecyclerView 연결.

public class ChatRoomActivity extends AppCompatActivity {
    private ActivityChatroomBinding activityChatroomBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatroomBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatroom);
    }
}
