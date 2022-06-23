package com.example.firebase_chat_basic.viewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


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
    String getContent = "메세지가 없어요";
    String equalsKey;
    int getMessageCount;
    private boolean dataset = false;

    String combineKey;

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
                Log.d("root", "=============== 최상위 루트 감지 ===============");
                chatListModelArrayList.clear();
                for(DataSnapshot userDataSnapShot : snapshot.child("users").getChildren()) {
                    final String equalsUserKey = userDataSnapShot.getKey();
                    if(!equalsUserKey.equals(getCurrentMyUIDKey)) {
                        String otherUserKey = equalsUserKey;
                        final String userName = userDataSnapShot.child("name").getValue(String.class);
                        combineKey = otherUserKey + userName;
                        // 비동기 작업으로 빠지는데
                        childChatCheck(combineKey);
                        chatListModelArrayList.add(new ChatListModel(userName, getDate, getContent, String.valueOf(getMessageCount), combineKey, getCurrentMyUIDKey, otherUserKey));
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
    public void childChatCheck(String combineKey){
        equalsKey = combineKey;
        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int chatRoomCount = (int) snapshot.getChildrenCount();
                if(chatRoomCount > 0) {
                    for(DataSnapshot chatSnapShot : snapshot.getChildren()){
                        final String chatKey = chatSnapShot.getKey();
                        // 키 값이 만들어진 키 값이랑 같다면
                        if(chatKey.equals(equalsKey)) {
                            // 채팅을 전부다 가지고 오고
                            for(DataSnapshot commentSnapShot : chatSnapShot.child("comments").getChildren()) {
                                long beforeMessageKey = 0;
                                final long recentMessageKey = preferences.getLong("chatDateTime", 0);
                                long childMessageKey = Long.parseLong(Objects.requireNonNull(commentSnapShot.getKey()));

                                getContent = commentSnapShot.child("msg").getValue(String.class);
                                if (recentMessageKey > childMessageKey) {
                                    beforeMessageKey = Long.parseLong(commentSnapShot.getKey());
                                }
                                // 최신 메세지가 이전 메세지 보다 크다면
                                if (recentMessageKey > beforeMessageKey) {
                                    getMessageCount++;
                                    Log.d("getMessageCount", String.valueOf(getMessageCount));
                                    getContent = preferences.getString("chatLastText", "Message is nothing");
                                }
                            }
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
