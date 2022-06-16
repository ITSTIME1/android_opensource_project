package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
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
    int receive_chat_count = 0;


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



//                        databaseReference.child("chat").child(chatRoomUID).child("sender_user").setValue(sender_user);
//                        databaseReference.child("chat").child(chatRoomUID).child("receiver_user").setValue(receiver_user);
//                        // String 값으로 실시간으로 현재 시간의 millis 가 들어감. 거기 안에
//                        databaseReference.child("chat").child(chatRoomUID).child("comments").child(String.valueOf(dateTime)).child("msg").setValue(getText);
//                        // 현재 날짜도 포함시켜 준다.
//                        databaseReference.child("chat").child(chatRoomUID).child("comments").child(String.valueOf(dateTime)).child("currentDate").setValue(timestamp.toString());


                        // 채팅 탐색.
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int getChatCount = (int) snapshot.getChildrenCount();
                                Log.d("채팅 개수 ", String.valueOf(getChatCount));

                                // 채팅방의 개수가 있는것과 없는것.
                                if(getChatCount > 0) {
                                    // chatKeySnapShot  D/chatKeySnapShot: DataSnapshot { key = UsSF86xUzmQR0hBlCxYFkGt3Sxy2, value = {comments={null={msg=}}, sender_user=lb7oZnqLZUaBn9gf94v49zpQKGu1, receiver_user=UsSF86xUzmQR0hBlCxYFkGt3Sxy2} }
                                    for(DataSnapshot chatSnapShot : snapshot.getChildren()) {
                                        final String getChatKey = chatSnapShot.getKey();
                                        Log.d("getChatKey", getChatKey + "<-- 이걸로 chatRoomKey 넘겨줄거임");
                                        if(chatSnapShot.hasChild("receiver_user") && chatSnapShot.hasChild("sender_user") && chatSnapShot.hasChild("comments")) {
                                            final String receive_user = chatSnapShot.child("receiver_user").getValue(String.class);
                                            Log.d("receive_user ", receive_user);
                                            final String sender_user = chatSnapShot.child("sender_user").getValue(String.class);
                                            Log.d("sender_user ", sender_user);
                                            assert sender_user != null;
                                            if((sender_user.equals(getCurrentMyUIDKey) && receive_user.equals(getUserKey) || (sender_user.equals(getUserKey) && receive_user.equals(getCurrentMyUIDKey)))){
                                                for(DataSnapshot chatDataSnapShot : chatSnapShot.child("comments").getChildren()) {
                                                    // getKey = 165156 날짜를 밀리세컨즈로 저장해둔 값.
                                                    final long getMessageKey = Long.parseLong(chatDataSnapShot.getKey());
                                                    Log.d("getMessageKey 최신 키 값 ", String.valueOf(getMessageKey));
                                                    final long getLastMessageKey = preferences.getLong("getLastCommentKey", 0);
                                                    Log.d("getLastMessageKey 마지막 키 값 ", String.valueOf(getLastMessageKey));
                                                    getContent = chatDataSnapShot.child("msg").getValue(String.class);
                                                    if(getMessageKey > getLastMessageKey) {
                                                        receive_chat_count++;
                                                        Log.d("chatCount", String.valueOf(getChatCount));
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
                        chatListModel = new ChatListModel(getUserName, newDateFormat, getContent, String.valueOf(receive_chat_count), getUserKey, getCurrentMyUIDKey);
                        chatListModelArrayList.add(chatListModel);
                        chatRecyclerAdapter.notifyDataSetChanged();
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
