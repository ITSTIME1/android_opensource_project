package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


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
import java.util.Objects;


// @TODO getContent 내용이 다른 한쪽에서는 최신화가 안됨.

public class ChatViewModel extends AndroidViewModel {
    // database reference
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    public MutableLiveData<ArrayList<ChatListModel>> arrayListMutableLiveData = new MutableLiveData<>();
    public ArrayList<ChatListModel> chatListModelArrayList;
    private ChatListModel chatListModel;
    private ChatRecyclerAdapter chatRecyclerAdapter;

    private DatabaseReference databaseReference;
    private String getCurrentMyUIDKey;
    private String getDate;
    private int chatKey;
    private SharedPreferences preferences;
    private String getContent;
    private int getMessageCount;
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
        }

        Date nowDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        getDate = simpleDateFormat.format(nowDate);
        userRealTimeDataBase();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void userRealTimeDataBase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    // 키 값을 전부 가지고 와준 다음에
                    final String dataSnapShotKey = dataSnapshot.getKey();
                    // 나의 키 값이랑 다른 값만 가지고 와서 리스트에 추가시킨다.
                    if(!dataSnapShotKey.equals(getCurrentMyUIDKey)) {
                        final String getOtherKey = dataSnapShotKey;
                        final String getOtherName = dataSnapshot.child("name").getValue(String.class);
                        Log.d("getOtherName", getOtherName);
                        chatRealTimeDataBase(getOtherKey);
                        ChatListModel chatListModel = new ChatListModel(getOtherName, getDate, getContent, String.valueOf(getMessageCount), Integer.toString(chatKey), getCurrentMyUIDKey, getOtherKey);
                        chatListModelArrayList.add(chatListModel);
                    }
                    arrayListMutableLiveData.setValue(chatListModelArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void chatRealTimeDataBase(String getOtherKey) {
        final String getOtherUID = getOtherKey;
        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot chatDataSnapShot : snapshot.getChildren()) {
                    if(chatDataSnapShot != null) {
                        chatKey = Integer.parseInt(Objects.requireNonNull(chatDataSnapShot.getKey()));
                    }
                    // chat - 하위 키 값이 채팅 기록을 가지고 있다면
                    assert chatDataSnapShot != null;
                    if(chatDataSnapShot.hasChild("receiver_user") && chatDataSnapShot.hasChild("sender_user")) {
                        final String receiver_user = chatDataSnapShot.child("receiver_user").getValue(String.class);
                        final String sender_user = chatDataSnapShot.child("sender_user").getValue(String.class);

                        if((receiver_user.equals(getCurrentMyUIDKey) && sender_user.equals(getOtherUID)) || (receiver_user.equals(getOtherUID) && sender_user.equals(getCurrentMyUIDKey))) {
                            for(DataSnapshot commentsSnapShot : chatDataSnapShot.child("comments").getChildren()) {
                                // chatCount 값과 그리고 마지막 메세지 설정
                                final int commentsCount = (int) commentsSnapShot.getChildrenCount();
                                if(commentsCount < 0) {
                                    getContent = "채팅 기록이 없습니다";
                                    getMessageCount = 0;
                                } else {
                                    long beforeMessageKey = 0;
                                    final long recentMessageKey = preferences.getLong("getLastCommentKey", 0);
                                    long childrenMessageKey = Long.parseLong(Objects.requireNonNull(commentsSnapShot.getKey()));
                                    // 만약 가지고온 값보다 작은 값만 저장.
                                    if(childrenMessageKey < recentMessageKey) {
                                        beforeMessageKey = Long.parseLong(commentsSnapShot.getKey());
                                    }
                                    getContent = commentsSnapShot.child("msg").getValue(String.class);
                                    if(recentMessageKey > beforeMessageKey) {
                                        getMessageCount++;
                                    }
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

    // init
    public void initViewModel() {
        Application context = getApplication();
        preferences = context.getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
    }


    public String getName(int pos){
        return Objects.requireNonNull(arrayListMutableLiveData.getValue()).get(pos).getChatName();
    }
    public String getContent(int pos){
        return Objects.requireNonNull(arrayListMutableLiveData.getValue()).get(pos).getChatContent();
    }
    public String getCount(int pos){
        return Objects.requireNonNull(arrayListMutableLiveData.getValue()).get(pos).getChatCount();
    }
    public String getDate(int pos){
        return Objects.requireNonNull(arrayListMutableLiveData.getValue()).get(pos).getChatDate();
    }

    public String getChatKey(int pos) {
        return Objects.requireNonNull(arrayListMutableLiveData.getValue()).get(pos).getChatKey();
    }

    public String getCurrentMyUID(int pos) {
        return Objects.requireNonNull(arrayListMutableLiveData.getValue()).get(pos).getChatMyUID();
    }
    public String getOtherUID(int pos) {
        return Objects.requireNonNull(arrayListMutableLiveData.getValue()).get(pos).getChatOtherUID();
    }
    public int getChatListModelList(){
        return chatListModelArrayList.size();
    }

    public ChatRecyclerAdapter getChatRecyclerAdapter(){
        return chatRecyclerAdapter;
    }

}
