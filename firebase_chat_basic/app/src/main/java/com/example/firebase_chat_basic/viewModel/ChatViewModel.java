package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


// @TODO 1. 문제점 채팅을 보내는 즉시 채팅의 개수가 2배로 증가
// @TODO 2. 모든 채팅방에 동일한 채팅 표시
// @TODO 3. 채팅 개수가 보내는쪽은 표시되지 않음.


// 1. 처음 chatkey = null 이기 때문에
// 2. 두번째 chatkey 값이 비었다면 getOtherUId 값을 저장시켜서
// 3. 그리고 나서 sendbutton 클릭시에 채팅방이 생기니까. chatkey 값이 저장되는 형태
// 4. chatKey 값이 저장되니 onDataChanged 로 데이터의 변화가 생기니 트리거가 다시 한번 작동해서 chat 내역을 가지고오는데


public class ChatViewModel extends AndroidViewModel {
    // database reference
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    public ArrayList<ChatListModel> chatListModelArrayList;
    private ChatListModel chatListModel;
    private ChatRecyclerAdapter chatRecyclerAdapter;

    private DatabaseReference databaseReference;
    String getCurrentMyUIDKey;
    String newDateFormat;
    private SharedPreferences preferences;

    String getContent = "메세지가 없습니다.";
    int getMessageCount;
    String chatKey = "";


    public ChatViewModel(String getCurrentMyUID, Application application){
        super(application);
        initViewModel();

        if(getCurrentMyUID != null ){
            getCurrentMyUIDKey = getCurrentMyUID;
        }

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
        // 경로에서 데이터를 읽고 수신을 대기해야 될 경우 사용한다.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            // onDataChange 는 한번 트리거 된 후 하위 요소를 포함한
            // 데이터가 변경될 때마다 다시 트리거 된다.
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelArrayList.clear();
                for(DataSnapshot userDataSnapshot : snapshot.child("users").getChildren()) {
                    final String getUserKey = userDataSnapshot.getKey();
                    Log.d("getUserKey", getUserKey);
                    assert getUserKey != null;
                    if (!getUserKey.equals(getCurrentMyUIDKey)){
                        final String getOtherKey = getUserKey;
                        Log.d("getOtherKey", getOtherKey);
                        final String getUserName = userDataSnapshot.child("name").getValue(String.class);
                        // 채팅 탐색.
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int getChatCount = (int) snapshot.getChildrenCount();
                                Log.d("채팅 개수 ", String.valueOf(getChatCount));
                                Log.d("chatKey", chatKey);

                                // 채팅방의 개수가 있는것과 없는것.
                                if(getChatCount > 0) {
                                    // 채팅 이름은 상대방의 key 값으로 저장.
                                    // 그럼 포문을 가지고 올때 chatSnapShot 에 묶음으로 가지고 오게 됨.
                                    for(DataSnapshot chatSnapShot : snapshot.getChildren()) {
                                        // comments, receiver, sender...
                                        final String getChatKey = chatSnapShot.getKey();
                                        chatKey = getChatKey;
                                        Log.d("chatKey", chatKey);
                                        if(chatSnapShot.hasChild("receiver_user") && chatSnapShot.hasChild("sender_user") && chatSnapShot.hasChild("comments")) {

                                            final String receive_user = chatSnapShot.child("receiver_user").getValue(String.class);
                                            final String sender_user = chatSnapShot.child("sender_user").getValue(String.class);

                                            if((receive_user.equals(chatKey) && sender_user.equals(getCurrentMyUIDKey)) || (receive_user.equals(getCurrentMyUIDKey) && sender_user.equals(chatKey))) {
                                                // commentSnapShot 으로 comments 내용을 전부다 가지고 온다.
                                                for(DataSnapshot commentSnapShot : chatSnapShot.child("comments").getChildren()) {
                                                    if(commentSnapShot != null) {
                                                        long beforeMessageKey = 0;
                                                        final long recentMessageKey = preferences.getLong("getLastCommentKey", 0);
                                                        long commentSnapShotKey = Long.parseLong(Objects.requireNonNull(commentSnapShot.getKey()));
                                                        // 만약 가지고온 값보다 작은 값만 저장.
                                                        if(commentSnapShotKey < recentMessageKey) {
                                                            beforeMessageKey = Long.parseLong(commentSnapShot.getKey());
                                                        }
                                                        getContent = commentSnapShot.child("msg").getValue(String.class);
                                                        if(recentMessageKey > beforeMessageKey) {
                                                            getMessageCount++;
                                                        }
                                                    } else {
                                                        Log.d("commentSnapShot is null ", null);
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
                        chatListModel = new ChatListModel(getUserName, newDateFormat, getContent, String.valueOf(getMessageCount), chatKey, getCurrentMyUIDKey, getOtherKey);
                        chatListModelArrayList.add(chatListModel);
                    }
                }
                chatRecyclerAdapter.notifyDataSetChanged();
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

    public String getChatKey(int pos) {
        return chatListModelArrayList.get(pos).getChatKey();
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
