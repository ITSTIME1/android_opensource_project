package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


// @TODO getContent 내용이 다른 한쪽에서는 최신화가 안됨.

public class ChatViewModel extends AndroidViewModel {
    // database reference
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    public List<ChatListModel> chatNewList;
    public ArrayList<ChatListModel> chatListModelArrayList;
    private ChatListModel chatListModel;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private DatabaseReference databaseReference;

    private String getCurrentMyUIDKey;
    private String getDate;
    private String chatKey = "";
    private SharedPreferences preferences;
    String getContent = "";
    int getMessageCount;
    private boolean dataset = false;


    public ChatViewModel(String getCurrentMyUID, Application application){
        super(application);
        initViewModel();
        if(getCurrentMyUID != null ){
            getCurrentMyUIDKey = getCurrentMyUID;
        }
        if(chatListModelArrayList == null && chatRecyclerAdapter == null) {
            chatListModelArrayList = new ArrayList<>();
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
            // No Adapter 문제가 생기는거임 아직 리스트 값이 없는데 그러니까
        }
        userRealTimeDataBase();
    }



    @SuppressLint("NotifyDataSetChanged")
    public void userRealTimeDataBase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("clear", "");
                chatListModelArrayList.clear();
                getContent = "메세지가 없습니다.";
                chatKey = "";
                getMessageCount = 0;

                // 유저의 개수가 2개라면 2번반복
                for(DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    // 키 값을 전부 가지고 와준 다음에
                    final String dataSnapShotKey = dataSnapshot.getKey();
                    // 나의 키 값이랑 다른 값만 가지고 와서 리스트에 추가시킨다.
                    if(!dataSnapShotKey.equals(getCurrentMyUIDKey)) {
                        final String getOtherKey = dataSnapShotKey;
                        String getOtherName = dataSnapshot.child("name").getValue(String.class);
                        Log.d("getOtherName", getOtherName);
                        chatRealTimeDataBase(getOtherKey, getOtherName);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Chat logic
     * @param getOtherKey
     */
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
                    int chatRoomCount = (int) snapshot.getChildrenCount();
                    Log.d("채팅방 개수 확인 ", "");
                    // 만약 채팅방이 존재한다면
                    if (chatRoomCount > 0) {
                        // 채팅방 키를 가지고 오는 for문을 돌림.
                        for (DataSnapshot chatKeySnapShot : snapshot.getChildren()) {
                            // 해당 채팅방이 3개의 값을 전부다 가지고 있다면
                            if (chatKeySnapShot.hasChild("receiver_user") && chatKeySnapShot.hasChild("sender_user") && chatKeySnapShot.hasChild("comments")) {
                                // 채팅기록이 담겨져 있는 receiver_user, sender_user 를 가지고온다.
                                final String receiver_user = chatKeySnapShot.child("receiver_user").getValue(String.class);
                                final String sender_user = chatKeySnapShot.child("sender_user").getValue(String.class);
                                // 만약 보낸사람, 받은 사람에 나 또는 해당 다른 키 값을 가지고 있는 사람의 값이 있다면
                                if ((receiver_user.equals(otherKey) && sender_user.equals(getCurrentMyUIDKey)) || (receiver_user.equals(getCurrentMyUIDKey) && sender_user.equals(otherKey))) {
                                    // 해당 키 값을 따로 받아서 저장한다.
                                    Log.d("채팅 유저 확인 완료 ", "");
                                    final String getKey = chatKeySnapShot.getKey();
                                    chatKey = getKey;
                                    Log.d("받은 chatKey ", chatKey);
                                    for (DataSnapshot commentSnapShot : chatKeySnapShot.child("comments").getChildren()) {
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

                    chatListModelArrayList.add(new ChatListModel(otherName, getDate, getContent, String.valueOf(getMessageCount), chatKey, getCurrentMyUIDKey, otherKey));
                    Log.d("채팅 객체 생성 완료.", "");
                    chatRecyclerAdapter.notifyDataSetChanged();

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


    public String getName(int pos){
        return chatListModelArrayList.get(pos).getChatName();
    }
    public String getContent(int pos){
        return chatListModelArrayList.get(pos).getChatContent();
    }
    public String getCount(int pos){
        return chatListModelArrayList.get(pos).getChatCount();
    }
    public String getDate(int pos){
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
    public ArrayList<ChatListModel> getChatListModelList(){
        return chatListModelArrayList;

    }

    public ChatRecyclerAdapter getChatRecyclerAdapter(){
        return chatRecyclerAdapter;
    }

}
