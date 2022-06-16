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
    public MutableLiveData<ArrayList<ChatListModel>> chatListModelList = new MutableLiveData<>();
    public ArrayList<ChatListModel> chatListModelArrayList = new ArrayList<>();
    private ChatRecyclerAdapter chatRecyclerAdapter;

    private DatabaseReference databaseReference;
    private final String getCurrentMyUIDKey;
    private SharedPreferences preferences;


    // realtime database variable
    String getUserKey;
    String getDate = "";
    String getContent = "메세지가 없습니다.";
    int chatCount = 0;
    int getLastCommentsKey;



    // 현재 날짜를 포맷 형식을 지정해서 가지고옴.
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String currentDate = currentDateFormat.format(date);


    public ChatViewModel(String getCurrentMyUID, Application application){
        super(application);
        initViewModel();
        getCurrentMyUIDKey = getCurrentMyUID;
        getLastCommentsKey = preferences.getInt("dateTime", 0);
        getRealTimeDatabase();
    }


    // init
    public void initViewModel(){
        Application context = getApplication();
        preferences = context.getSharedPreferences("putSendText", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        LocalDate localDate = LocalDate.now();


        if(chatListModelList == null ){
            chatListModelList = new MutableLiveData<>();
        }

        if(chatListModelArrayList == null) {
            chatListModelArrayList = new ArrayList<>();
        }
        if(chatRecyclerAdapter == null) {
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
        }
    }


    // 먼저 유저 키 값의 대한 정보를 가지고 오고

    public void getRealTimeDatabase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    getUserKey = dataSnapshot.getKey();
                    Log.d("유저 키값 리스트 : ", getUserKey);


                    assert getUserKey != null;
                    // 나와 다른 uid 값에 한해서만 chatListModelList 에 내용을 담아준다
                    if(!getUserKey.equals(getCurrentMyUIDKey)) {
                        Log.d("유저 키 값 검사를 시작합니다", "*****");
                        final String getOtherName = dataSnapshot.child("name").getValue(String.class);
                        final String getOtherUID = dataSnapshot.child("uid").getValue(String.class);
                        // 걸러서 나온값
                        Log.d("유저 키값 : ", getUserKey);
                        Log.d("유저 이름 : ", getOtherName);
                        getDate = currentDate;

                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // chat - awjieofjwa(getChatRoomCount) <- 이거의 개수를 가지고 옴
                                Log.d("채팅 탐색을 시작합니다.", "채팅 탐색 시작");
                                int getChatRoomCount = (int) snapshot.getChildrenCount();
                                Log.d("채팅방 개수 : ", Integer.toString(getChatRoomCount));
                                // 채팅창이 1개 이상이면
                                if(getChatRoomCount > 0) {
                                    // comments, 리시브 센더를 가지고옴
                                    for(DataSnapshot chatRoomContent : snapshot.getChildren()) {
                                        final String chatRoomUID = snapshot.getKey();

                                        final String sender_user = chatRoomContent.child("sender_user").getValue(String.class);
                                        Log.d("sender_user : ", sender_user);

                                        final String receiver_user = chatRoomContent.child("receiver_user").getValue(String.class);
                                        Log.d("receiver_user : ", receiver_user);

                                        if((Objects.requireNonNull(sender_user).equals(getCurrentMyUIDKey) &&
                                                Objects.equals(receiver_user, chatRoomUID)) ||
                                                (sender_user.equals(chatRoomUID) &&
                                                        Objects.requireNonNull(receiver_user).equals(getCurrentMyUIDKey))) {
                                            // comments - 11236126
                                            for (DataSnapshot commentSnapShot : chatRoomContent.child("comments").getChildren()) {
                                                // 키 값 저장
                                                final int getCommentKey = Integer.parseInt(Objects.requireNonNull(commentSnapShot.getKey()));
                                                final int getLastCommentKey = preferences.getInt("getLastCommentKey", 0);

                                                getContent = commentSnapShot.child("msg").getValue(String.class);
                                                if(getCommentKey > getLastCommentKey) {
                                                    chatCount++;
                                                }
                                            }
                                        }
                                    }
                                }
                                Log.d("채팅 전체탐색 종료 리스트 삽입 : ", "*****");
                                ChatListModel chatListModel = new ChatListModel(getOtherName, getDate, getContent, String.valueOf(chatCount), getOtherUID, getCurrentMyUIDKey);
                                Log.d("잘 들어왔니 : ", String.valueOf(chatListModel));
                                chatListModelArrayList.add(chatListModel);
                                Log.d("리스트도 잘 들어왔니 : ", String.valueOf(chatListModelArrayList));
                                int i;
                                for(i=0; i < chatListModelArrayList.size(); i++){
                                    Log.d("리스트도 잘 들어왔니 : ", String.valueOf(chatListModelArrayList));
                                    Log.d("리스트 이름 확인 : ", chatListModelArrayList.get(i).getChatName());
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
