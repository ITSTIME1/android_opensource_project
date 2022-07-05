package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ChatRoomRecyclerAdapter;
import com.example.firebase_chat_basic.constants.Constants;
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

/**
 * [ChatRoomActivity Introduce]
 *
 * <Topic>
 *     "ChatRoomActivity" has 6 methods in class the class used only "chatting" so if you want to chat someone or your friend and anyone
 *     you can to send "message", "image", "voice", "reservation message"
 *     there weren't the "ViewModel" because i thought that we don't need a "Dependency injection" in class so that if it used has many boiler code.
 *
 * </Topic>
 *
 *
 */


public class ChatRoomActivity extends AppCompatActivity implements BaseInterface{
    private ActivityChatroomBinding activityChatroomBinding;

    private DatabaseReference databaseReference;
    private SharedPreferences.Editor editor;

    private String get_other_name;
    private String get_chat_key;
    private String get_current_my_uid;
    private String get_other_uid;
    private Long date_time;

    private ChatRoomRecyclerAdapter chat_room_recycler_adapter;
    private ArrayList<ChatRoomModel> chat_room_list;

    private boolean dataSet = false;

    public String getOtherName() {
        return get_other_name;
    }

    public ChatRoomRecyclerAdapter getChatRoomRecyclerAdapter() {
        return chat_room_recycler_adapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatroomBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatroom);
        default_init();
        get_from_chat_recycler_adapter();
        get_message_list();
        send_message();
        on_back_pressed();
        on_focus_text_field();
    }

    // create_list
    public void get_message_list() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chatSnapShot : snapshot.child("chat").getChildren()) {
                    // 메세지를 가지고 있다면
                    if (chatSnapShot.hasChild("message")) {
                        chat_room_list.clear();
                        // message 를 가지고 온다.
                        for (DataSnapshot messageSnapShot : chatSnapShot.child("message").getChildren()) {
                            // 메세지를 가지고 와서 그 메세지 값들이 메세지와 나의 키 값이 있다면
                            // 메시지, 보낸 사람의 키 값, dateTime
                            dataSet = false;
                            if (messageSnapShot.hasChild("msg") && messageSnapShot.hasChild("mineKey")) {
                                final String setListMessage = messageSnapShot.child("msg").getValue(String.class);
                                final String setKey = messageSnapShot.child("mineKey").getValue(String.class);
                                final String setDate = messageSnapShot.child("save_chat_date").getValue(String.class);
                                if(!dataSet) {
                                    dataSet = true;
                                    chat_room_list.add(new ChatRoomModel(setKey, setListMessage, setDate));
                                    chat_room_recycler_adapter.notifyDataSetChanged();
                                    activityChatroomBinding.chatRoomListRec.scrollToPosition(chat_room_list.size()-1);
                                }

                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("get_message_list (Error) ", String.valueOf(error));
            }
        });
    }

    // send_message
    public void send_message() {
        activityChatroomBinding.chatRoomSendButton.setOnClickListener((View view) -> {
            final String chat_text = activityChatroomBinding.chatRoomTextField.getText().toString();
            if (!chat_text.isEmpty()) {
                date_time = System.currentTimeMillis();

                Date now_date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("오후" + " HH:mm");
                // 날짜 포멧 지정 (저장용)
                String set_date = simpleDateFormat.format(now_date);
                // 채팅 저장
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(date_time)).child("msg").setValue(chat_text);
                // msg 에 키 값 저장
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(date_time)).child("mineKey").setValue(get_current_my_uid);
                // msg 에 시간 저장
                databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(date_time)).child("save_chat_date").setValue(set_date);
                // 보낸 사람 저장
                databaseReference.child("chat").child(get_chat_key).child("보낸사람").setValue(get_current_my_uid);
                // 받은 사람 저장
                databaseReference.child("chat").child(get_chat_key).child("받은사람").setValue(get_other_uid);

                editor.putLong("chatDateTime", date_time);
                editor.commit();
            }
        });
    }



    // back pressed
    public void on_back_pressed() {
        activityChatroomBinding.chatRoomBackButton.setOnClickListener((View view) -> finish());
    }

    // focus text field
    public void on_focus_text_field(){
        activityChatroomBinding.chatRoomTextField.setOnFocusChangeListener((View view, boolean b) -> {
            if(b) {
                Toast.makeText(ChatRoomActivity.this, "키보드 열림", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(() ->
                        activityChatroomBinding.chatRoomListRec.scrollToPosition(chat_room_list.size()-1), 500);
            }
        });
    }

    // initialize
    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        activityChatroomBinding.setChatRoomActivity(this);
        activityChatroomBinding.setLifecycleOwner(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
        if (chat_room_list == null && chat_room_recycler_adapter == null) {
            chat_room_list = new ArrayList<>();
            chat_room_recycler_adapter = new ChatRoomRecyclerAdapter(chat_room_list, getBaseContext());
        }

        SharedPreferences preferences = getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        editor = preferences.edit();

    }

    // get data from (chat recycler adapter, profile activity)
    public void get_from_chat_recycler_adapter() {
        Intent getIntent = getIntent();
        get_other_name = getIntent.getStringExtra("getOtherName");
        get_chat_key = getIntent.getStringExtra("getChatKey");
        get_current_my_uid = getIntent.getStringExtra("getCurrentMyUID");
        get_other_uid = getIntent.getStringExtra("getOtherUID");
        Log.d("getOtherName", get_other_name);
        Log.d("getChatKey", get_chat_key);
        Log.d("getCurrentMyUID", get_current_my_uid);
        Log.d("getOtherUID", get_other_uid);
    }
}
