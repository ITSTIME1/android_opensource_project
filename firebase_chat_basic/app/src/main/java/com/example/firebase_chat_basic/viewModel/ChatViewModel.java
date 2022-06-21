package com.example.firebase_chat_basic.viewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;


import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


// @TODO getContent 내용이 다른 한쪽에서는 최신화가 안됨.

public class ChatViewModel extends AndroidViewModel {
    // database reference
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    public ArrayList<ChatListModel> chatListModelArrayList;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private DatabaseReference databaseReference;
    private SharedPreferences preferences;

    private String getCurrentMyUIDKey;
    private String getDate;
    private String chatKey = "";
    String getContent = "메세지가 없어요";
    int getMessageCount;
    private boolean dataSet = false;


    public ChatViewModel(String getCurrentMyUID, Application application) {
        super(application);
        initViewModel();
        if (getCurrentMyUID != null) {
            getCurrentMyUIDKey = getCurrentMyUID;
        }
        if (chatListModelArrayList == null && chatRecyclerAdapter == null) {
            chatListModelArrayList = new ArrayList<>();
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
            // No Adapter 문제가 생기는거임 아직 리스트 값이 없는데 그러니까
        }
        // 채팅 정보 가지고 오기
        userRealTimeDataBase();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void userRealTimeDataBase() {
        // 유저의 정보를 계속 감지하고 있다가 users 하위 목록이 변경 되면 그 목록을 추가.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("최상위 루트 변화감지", "");
                chatListModelArrayList.clear();
                for(DataSnapshot userDataSnapShot : snapshot.child("users").getChildren()) {
                    final String equalsUserKey = userDataSnapShot.getKey();
                    if(!equalsUserKey.equals(getCurrentMyUIDKey)) {
                        String otherUserKey = equalsUserKey;
                        final String userName = userDataSnapShot.child("name").getValue(String.class);
                        chatRealTimeDataBase(otherUserKey, userName);
                        chatListModelArrayList.add(new ChatListModel(userName, getDate, getContent, String.valueOf(getMessageCount), chatKey, getCurrentMyUIDKey, otherUserKey));
                        chatRecyclerAdapter.notifyDataSetChanged();
                        Log.d("리스트추가 후 변경사항 적용", "");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void chatRealTimeDataBase(String getOtherKey, String getOtherName) {
        String otherKey = getOtherKey;
        String otherName = getOtherName;
        Log.d("otherKey ", otherKey);
        Log.d("otherName", otherName);
        if(otherKey != null) {
            databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int chatCount = (int) snapshot.getChildrenCount();
                    if(chatCount > 0) {
                        for(DataSnapshot chatRoomSnapShot : snapshot.getChildren()) {
                            if(chatRoomSnapShot.hasChild("receiver_user") && chatRoomSnapShot.hasChild("sender_user") && chatRoomSnapShot.hasChild("comments")) {

                                final String receiver_user = chatRoomSnapShot.child("receiver_user").getValue(String.class);
                                final String sender_user = chatRoomSnapShot.child("sender_user").getValue(String.class);

                                if((receiver_user.equals(otherKey) && sender_user.equals(getCurrentMyUIDKey)) || (receiver_user.equals(getCurrentMyUIDKey) && sender_user.equals(otherKey))) {
                                    final String getRoomKey = chatRoomSnapShot.getKey();
                                    chatKey = getOtherKey;
                                    for(DataSnapshot commentSnapShot : chatRoomSnapShot.child("comments").getChildren()) {
                                        long beforeMessageKey = 0;
                                        final long recentMessageKey = preferences.getLong("getLastCommentKey", 0);
                                        long childMessageKey = Long.parseLong(Objects.requireNonNull(commentSnapShot.getKey()));

                                        if (recentMessageKey > childMessageKey) {
                                            beforeMessageKey = Long.parseLong(commentSnapShot.getKey());
                                        }
                                        getContent = preferences.getString("putSendText", "Message is nothing");
                                        if (recentMessageKey > beforeMessageKey) {
                                            getMessageCount++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.d("채팅방이 없어요", "");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    // init
    public void initViewModel() {
        Application context = getApplication();
        preferences = context.getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);

        Date nowDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        getDate = simpleDateFormat.format(nowDate);
    }


    public String getName(int pos) {
        return chatListModelArrayList.get(pos).getChatName();
    }

    public String getContent(int pos) {
        return chatListModelArrayList.get(pos).getChatContent();
    }

    public String getCount(int pos) {
        return chatListModelArrayList.get(pos).getChatCount();
    }

    public String getDate(int pos) {
        return chatListModelArrayList.get(pos).getChatDate();
    }

    public String getChatKey(int pos) {
        return chatListModelArrayList.get(pos).getChatKey();
    }

    public String getCurrentMyUID(int pos) {
        return chatListModelArrayList.get(pos).getChatMyUID();
    }

    public String getOtherUID(int pos) {
        return chatListModelArrayList.get(pos).getChatOtherUID();
    }

    public ArrayList<ChatListModel> getChatListModelList() {
        return chatListModelArrayList;

    }

    public ChatRecyclerAdapter getChatRecyclerAdapter() {
        return chatRecyclerAdapter;
    }

}
