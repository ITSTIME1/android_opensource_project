package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


// @TODO 1. 문제점 채팅을 보내는 즉시 채팅의 개수가 2배로 증가
// @TODO 2. 모든 채팅방에 동일한 채팅 표시
// @TODO 3. 리스트를 초기화 할때 (?)


public class ChatViewModel extends AndroidViewModel {
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    public ArrayList<ChatListModel> chatListModelArrayList;
    private ChatListModel chatListModel;
    private ChatRecyclerAdapter chatRecyclerAdapter;

    private DatabaseReference databaseReference;
    String getCurrentMyUIDKey;
    String newDateFormat;
    private SharedPreferences preferences;

    String getContent = "메세지가 없습니다.";
    int getChatCount = 0;
    int i;


    public ChatViewModel(String getCurrentMyUID, Application application){
        super(application);
        initViewModel();
        getCurrentMyUIDKey = getCurrentMyUID;

        if(chatListModelArrayList == null && chatRecyclerAdapter ==null) {
            chatListModelArrayList = new ArrayList<>();
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
        }

        Date nowDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        newDateFormat = simpleDateFormat.format(nowDate);
        realTimeDataBase();

    }
    public void realTimeDataBase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    final String getUserKey = dataSnapshot.getKey();

                    assert getUserKey != null;
                    if (!getUserKey.equals(getCurrentMyUIDKey)){
                        final String getUserName = dataSnapshot.child("name").getValue(String.class);


                        // 채팅 탐색.
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        chatListModel = new ChatListModel(getUserName, newDateFormat, getContent, String.valueOf(getChatCount), getUserKey, getCurrentMyUIDKey);
                        chatListModelArrayList.add(chatListModel);
                        chatRecyclerAdapter.notifyDataSetChanged();

                        // 여기서 데이터 변경이 일어나면 ChatFragment 에서 데이터를 감지해서 자동적으로 노티피를함.
                        // chatRecyclerAdapter 에 다시 notify가 되었다는걸 알려주어야 됨 왜냐하면
                        // 초반에는 list 값이 새로 변경 되었기 때문에
                        // chatRecyclerAdapter는 이를 감지하지 못하기 때문임.

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
        preferences = context.getSharedPreferences("putSendText", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        LocalDate localDate = LocalDate.now();
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

    public String getOtherUID(int pos) {
        return chatListModelArrayList.get(pos).getChatOtherUID();
    }

    public String getCurrentMyUID(int pos) {
        return chatListModelArrayList.get(pos).getChatMyUID();
    }

    public ArrayList<ChatListModel> getChatListModelList(){
        return chatListModelArrayList;
    }

    public ChatRecyclerAdapter getChatRecyclerAdapter(){
        return chatRecyclerAdapter;
    }

}
