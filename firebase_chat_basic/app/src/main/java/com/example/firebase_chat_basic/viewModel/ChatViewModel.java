package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.example.firebase_chat_basic.view.fragment.ChatFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


// @TODO 1. 문제점 채팅을 보내는 즉시 채팅의 개수가 2배로 증가
// @TODO 2. 모든 채팅방에 동일한 채팅 표시
// @TODO 3. 리스트를 초기화 할때 (?)


public class ChatViewModel extends AndroidViewModel {
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private ArrayList<ChatListModel> chatListModelList;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private DatabaseReference databaseReference;
    private String getCurrentMyUIDKey;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LocalDate localDate;


    // realtime database variable
    String getOtherUID;
    String getOtherName;
    String getDate;
    String getContent;
    int getChatListCount;
    int getChatCount;
    String getRefreshCount;


    // 현재 날짜를 포맷 형식을 지정해서 가지고옴.
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String currentDate = currentDateFormat.format(date);


    public ChatViewModel(String getCurrentMyUID, Application application){
        super(application);
        initViewModel();
        getCurrentMyUIDKey = getCurrentMyUID;
        initRealTimeDatabaseVariable();
        getRealTimeDatabase();
    }

    // init
    public void initViewModel(){
        Application context = getApplication();
        preferences = context.getSharedPreferences("putSendText", Context.MODE_PRIVATE);
        editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        localDate = LocalDate.now();


        if(chatListModelList == null ){
            chatListModelList = new ArrayList<>();
        }
        if(chatRecyclerAdapter == null) {
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
        }

    }

    public void initRealTimeDatabaseVariable(){
        getOtherUID = "";
        getOtherName = "";
        getDate = "";
        getContent = "";
        getChatCount = 0;
    }

    /**
     * ViewModel 생성된 시점에 즉 회원가입이 완료되고 MainActivity로 넘어오고 난 이후에 기본적으로 ChatFragment 가 가장 먼저보인다.
     * ChatFragment 에서 ChatViewModel 을 초기화 한 시점에 회원가입한 uid 값을 넘겨받고
     * 넘겨 받은 값을 토대로 RecyclerView 리스트 값들을 받아온다.
     * 리스트 값을 받아 올 때 나랑 같은 uid 를 가진 user를 제외한 나머지 user를 표시한다.
     */

    public void getRealTimeDatabase(){

        // users 에 대한 정보를 전부 가지고 온다.
        // 데이터의 변경 사항의 대해서 수신대기한다.
        // 1. Root 에서 부터 찾는다.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelList.clear();
                for (DataSnapshot userDataSnapshot : snapshot.child("users").getChildren()) {
                    // 1. users - nov... key 값을 가져오고
                    // 2. key 값이랑 나의 키값이랑 다르다면 name을 가지고 온다.
                    // 3. 데이트 값을 가지고 온다.
                    getOtherUID = userDataSnapshot.getKey();

                    if(!getOtherUID.equals(getCurrentMyUIDKey)) {
                        getOtherName = userDataSnapshot.child("name").getValue(String.class);
                        getDate = currentDate;
                        getContent = "채팅을 시작하지 않았습니다.";
                        getChatCount = 0;
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // chatList 개수를 가지고 온다.
                                getChatListCount = (int) snapshot.getChildrenCount();
                                // 1. 한개 이상 존재하는거
                                // 2. 아무것도 없는거
                                // 3. 채팅 방이 생성되어서 생긴 키 값은 채팅할 상대방의 키 getOtherUID
                                // 4.
                                if(getChatListCount > 0) {
                                    for(DataSnapshot chatListValue : snapshot.getChildren()) {
                                        // getOtherUID = getChatKey
                                        // 채팅 생성시 상대방의 UID로 생성했기 때문이다.
                                        final String getChatKey = chatListValue.getKey();
                                        final String sender_user = chatListValue.child("sender_user").getValue(String.class);
                                        final String receive_user = chatListValue.child("receive_user").getValue(String.class);

                                        // 보내는 사람의 UID 가 나의 UID가 같고 , 받는 사람의 UID 가
                                        if((sender_user.equals(getCurrentMyUIDKey) && receive_user.equals(getChatKey)) || (sender_user.equals(getChatKey) && receive_user.equals(getCurrentMyUIDKey))) {
                                            // 댓글 하위에 잇는 목록들을 다 가지고 오고
                                            for(DataSnapshot chatCommentsSnapShot : snapshot.child("comments").getChildren()) {
                                                final long getMessageValue = Long.parseLong(Objects.requireNonNull(chatCommentsSnapShot.getKey()));
                                                final long getLastMessageValue = preferences.getLong("dateTime", 0);
                                                getContent = chatCommentsSnapShot.child("msg").getValue(String.class);

                                                if(getMessageValue > getLastMessageValue) {
                                                    getChatCount++;
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

                }
                chatListModelList.add(new ChatListModel(getOtherName, getDate, getContent, String.valueOf(getChatCount), getOtherUID, getCurrentMyUIDKey));
                chatRecyclerAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public String getName(int pos){
        return chatListModelList.get(pos).getChatName();
    }
    public String getContent(int pos){
        return chatListModelList.get(pos).getChatContent();
    }
    public String getCount(int pos){
        return chatListModelList.get(pos).getChatCount();
    }
    public String getDate(int pos){
        return chatListModelList.get(pos).getChatDate();
    }

    public String getOtherUID(int pos) {
        return chatListModelList.get(pos).getChatOtherUID();
    }

    public String getCurrentMyUID(int pos) {
        return chatListModelList.get(pos).getChatMyUID();
    }

    public ArrayList<ChatListModel> getChatListModelList(){
        return chatListModelList;
    }

    public ChatRecyclerAdapter getChatRecyclerAdapter(){
        return chatRecyclerAdapter;
    }

}
