package com.example.firebase_chat_basic.viewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;


// @TODO getContent 내용이 다른 한쪽에서는 최신화가 안됨.

public class ChatViewModel extends AndroidViewModel {
    // database reference
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    public ArrayList<ChatListModel> chatListModelArrayList;
    public ArrayList<ChatListModel> resultList;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private DatabaseReference databaseReference;
    private SharedPreferences preferences;
    private String firebase_MyKey;
    private String chatContent = "메세지";
    private String chatCount = String.valueOf(0);
    private boolean dataSet = false;



    public ChatViewModel(String getCurrentMyUID, Application application) {
        super(application);
        initViewModel();
        if (getCurrentMyUID != null) {
            firebase_MyKey = getCurrentMyUID;
        }
        if (chatListModelArrayList == null && chatRecyclerAdapter == null) {
            chatListModelArrayList = new ArrayList<>();
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
            // No Adapter 문제가 생기는거임 아직 리스트 값이 없는데 그러니까
        }
        if(resultList == null){
            resultList = new ArrayList<>();
        }
        // 유저 정보 생성.
        userRealTimeDataBase();

    }


    // 새로운 유저 생성
    @SuppressLint("NotifyDataSetChanged")
    public void userRealTimeDataBase() {
        databaseReference.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                // 1. 나와 다른 사용자 & 채팅검사 후 나의 키 값과 상대방의 키 값을 포함하는 chat내용을 가지고 와서 getContent
                // 2. 초반에 chatKey 값을 생성해주고
                // 3. 만약 채팅을 검사하다가 본인의 채팅키 값을 발견했다면 그 chatKey를 새로 부여해준다.

                // 최초에 앱을 실행했을때 채팅의 내역 그리고 나와 키 값이 다른 사람의 값을 가지고 온다.
                if(!snapshot.child("uid").getValue(String.class).equals(firebase_MyKey)) {
                    Date nowDate = new Date();
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String chatDate = newDtFormat.format(nowDate);;
                    Log.d("ChatViewModel", "======== Date 생성 ========");

                    String chatKey = firebase_MyKey + snapshot.child("uid").getValue(String.class);
                    Log.d("ChatViewModel", "======== 채팅방이 없을시 가져가는 [ChatKey] 생성 ========");
                    // 상대방의 키 값을 저장.
                    final String getOtherKey = snapshot.getKey();
                    final String getOtherName = snapshot.child("name").getValue(String.class);
                    // 채팅 검사
                    databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("ChatViewModel", "======== 채팅 읽어오기 ========");
                            int chatRoomCount = (int) snapshot.getChildrenCount();
                            // 채팅이 있으면
                            if(chatRoomCount > 0) {
                                Log.d("ChatViewModel", "======== 채팅방 검사 시작 ========");
                                // 채팅방 내역을 훑고
                                for(DataSnapshot chatDataSnapShot : snapshot.getChildren()) {
                                    // 그 해당 채팅방의 보낸 사람과 받은 사람의 키 값을 확인할거임.
                                    if(chatDataSnapShot.hasChild("보낸사람") && chatDataSnapShot.hasChild("받은사람")) {
                                        final String receiver = chatDataSnapShot.child("받은사람").getValue(String.class);
                                        final String sender = chatDataSnapShot.child("보낸사람").getValue(String.class);
                                        // 보낸 사람과 받은 사람의 키 값들을 확인하고
                                        if(receiver.equals(getOtherKey) && sender.equals(firebase_MyKey) || receiver.equals(firebase_MyKey) && sender.equals(getOtherKey)) {
                                            // 해당 키 값을 저장해주고
                                            Log.d("ChatViewModel", "======== 채팅방 검사 완료 ========");
                                            dataSet = false;
                                            final String getChatKey = chatDataSnapShot.getKey();
                                            String getContent = "메세지가 없습니다.";
                                            int getMessageCount = 0;

                                            for (DataSnapshot messageSnapShot : chatDataSnapShot.child("message").getChildren()) {
                                                long beforeMessageKey = 0;
                                                final long recentMessageKey = preferences.getLong("chatDateTime", 0);
                                                long childMessageKey = Long.parseLong(Objects.requireNonNull(messageSnapShot.getKey()));

                                                if (recentMessageKey > childMessageKey) {
                                                    beforeMessageKey = Long.parseLong(messageSnapShot.getKey());
                                                }
                                                if (recentMessageKey > beforeMessageKey) {
                                                    getMessageCount++;
                                                }
                                                getContent = messageSnapShot.child("msg").getValue(String.class);
                                            }
                                            chatListModelArrayList.add(new ChatListModel(
                                                    getOtherName,
                                                    chatDate,
                                                    getContent,
                                                    String.valueOf(getMessageCount),
                                                    getChatKey,
                                                    firebase_MyKey,
                                                    getOtherKey));
                                            chatRecyclerAdapter.notifyDataSetChanged();
                                            Log.d("ChatViewModel", "======== 채팅방 값을 넘겨준 채로 리스트 생성 완료 ========");
                                            dataSet = true;
                                        }
                                    }
                                }
                            }
                            dataSet = false;
                            // 채팅이 있는 건 채팅을 생성하고 또 이쪽으로 와서 생성하니까 문제가 있다.
                            chatListModelArrayList.add(new ChatListModel(
                                    getOtherName,
                                    chatDate,
                                    chatContent,
                                    String.valueOf(chatCount),
                                    chatKey,
                                    firebase_MyKey,
                                    getOtherKey));
                            chatRecyclerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("변경되었음?", "");

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void initViewModel() {
        Application context = getApplication();
        preferences = context.getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
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



//    int chatRoomCount = (int) snapshot.getChildrenCount();
//                if(chatRoomCount > 0) {
//                    for(DataSnapshot chatSnapShot : snapshot.getChildren()){
//                        final String getChatKey = chatSnapShot.getKey();
//                        // 반복문을 통해서 얻어온 키 값의 길이와 객체를 생성하기 전에 부여
//                        assert getChatKey != null;
//                        if(getChatKey.length() == chatKey.length() && ) {
//                            // comments 하위에 있는 메세지 키 값들을 가지고 온다.
//                            for(DataSnapshot commentSnapShot : chatSnapShot.child("comments").getChildren()) {
//                                long beforeMessageKey = 0;
//                                final long recentMessageKey = preferences.getLong("chatDateTime", 0);
//                                long childMessageKey = Long.parseLong(Objects.requireNonNull(commentSnapShot.getKey()));
//
//                                getContent = commentSnapShot.child("msg").getValue(String.class);
//                                if (recentMessageKey > childMessageKey) {
//                                    beforeMessageKey = Long.parseLong(commentSnapShot.getKey());
//                                }
//                                // 최신 메세지가 이전 메세지 보다 크다면
//                                if (recentMessageKey > beforeMessageKey) {
//                                    getMessageCount++;
//                                }
//                            }
//                        } else {
//                            continue;
//                        }
//                    }
//                }
//                chatListModelArrayList.add(new ChatListModel(firebase_otherName, getDate, getContent, String.valueOf(getMessageCount), chatKey, firebase_MyKey, firebase_otherUID));
//                chatRecyclerAdapter.notifyDataSetChanged();
//                Log.d("list chatKey ", String.valueOf(chatListModelArrayList.get(0).getChatKey()));
//
//                Log.d("리스트추가 후 변경사항 적용", "");