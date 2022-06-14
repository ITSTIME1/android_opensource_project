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
    private String getCurrentMyUID;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LocalDate localDate;


    // realtime database variable
    String getOtherUID;
    String getOtherName;
    String getDate;
    String getContent;
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
        initRealTimeDatabaseVariable();
        getRealTimeDatabase();
    }

    // init
    public void initViewModel(){
        Application context = getApplication();
        preferences = context.getSharedPreferences("putSendText", Context.MODE_PRIVATE);
        editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        getCurrentMyUID = getCurrentMyUID;
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

        //  우선 적으로 루트 단에 있는 모든 값들을 가져와서 검사하는데
        // 1. users 가 저장되어 있는 uid 값들을 전부 가져와서 내 uid랑 비교한다.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // snapshot == root

                // 루트 - users 아래에 있는 하위 값들을 전부 가져올때 까지 반복.
                for(DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    // users - key 값들.
                    getOtherUID = dataSnapshot.getKey();
                    Log.d("getOtherUID = 다른 사람의 uid : ", getOtherUID);
                    // 나의 uid 와 다른 uid 값을 비교해서
                    // 같지 않은 데이터만 가져옴
                    // ( 같게 되면 나의 아이디까지 가져오게 되니까

                    // 나랑 같지 않은것 중 이름, 채팅 내역, 채팅 카운트

                    // 채팅 내역 같은 경우는 채팅 내역이 있나 없나로 판단하고
                    // 채팅 내역이 있다면 채팅 내역에 있던 마지막 메세지를 전달해준다.
                    // 만약 채팅 내역이 없다면 빈 메세지를 전달해주고

                    if(!getOtherUID.equals(getCurrentMyUID)) {
                        getOtherName = dataSnapshot.child(getOtherUID).getValue(String.class);
                        getDate = localDate.toString();


                        // 채팅 내역을 불러올건데 한번 호출 된 후에 다시 호출 되지 않는다.
                        // 한번 가지고 와서 표시할 목적이기 때문에 수신을 대기 할 필요가 없다.
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // chat 하위 목록의 채팅 목록이 0개 이상이라면 (채팅방 존재)
                                // chatRoomActivity 에서 메세지를 보냈을때 chatRoomUID, comments 가 생성이 되었다.
                                // 그렇다는건 chat 하위 목록이 생겨났다는 거고


                                getDate = currentDate;
                                Log.d("getDate = 현재 날짜 : ", getDate);

                                // 카운트 가지고오기.
                                getRefreshCount = String.valueOf(getChatCount);
                                Log.d("getChatCount = 현재 채팅 개수 : ", getRefreshCount);
                                // chat - 하위목록(채팅방개수)
                                if(snapshot.getChildrenCount() > 0) {
                                    // 존재하는 채팅방을 전부 다 가지고온다.
                                    // dataSnapshot1 은 생성되는 chatRoomUID 가 되고 그 uid 하위 목록에 comments 라는걸 가지고 있다면
                                    // uid를 쭉 가지고오고
                                    // uid 에서 sender, receiver 정보가 일치한다면
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                        // chat - 하위에 있는 채팅목록의 UID 값들을 다 가지고온다.
                                        // UID 값은 상대방의 UID 값으로 기록되어 있음.

                                        // 보내는 필드랑
                                        // 받는 필드를 가지고 와서

                                        // 만약 보내는 사람의 UID 가 현재 내꺼랑 맞고
                                        // 만약 받는 사람의 UID 가 상대방의 UID 가 맞다면 정보를 가지고 온다.
                                        final String chatRoomUID = dataSnapshot1.getKey();
                                        final String sender_user = dataSnapshot1.child("sender_user").getValue(String.class);
                                        Log.d("sender_user = 보내는 사람 UID : ", sender_user);
                                        final String receiver_user = dataSnapshot1.child("receiver_user").getValue(String.class);
                                        Log.d("receiver_user = 받는 사람 UID : ", receiver_user);

                                        // 1. 보내는 사람이 나이고 받는 사람이 다른 사람의 UID로 일치할 때 ( 직접 보내는 시점 )
                                        // 2. 보내는 사람의 그 사람이고 받는 사람이 나일때 ( 받는 시점 )
                                        if(sender_user.equals(getCurrentMyUID) && receiver_user.equals(getOtherUID) || sender_user.equals(getOtherUID) && receiver_user.equals(getCurrentMyUID)) {

                                            // uid 정보 하위에 저장되어 있는 comments 정보를 가지고 온다.

                                            // users - uid - 정보
                                            // chat - chatkey(uid) - comments - message(메세지키값을 활용해서 하나더 생성 되었다면 chat count++;)
                                            for(DataSnapshot chatDataSnapShot : dataSnapshot1.child("comments").getChildren()) {

                                                // comment 하위에 있는 채팅을 하게 되면 생성될 키값들을 가지고 온다
                                                // 채팅을 하게 되면 그 값들이 아래로 하나씩 생기는데
                                                // 이전 채팅값이랑 새로 보낸 채팅값의 키값을 비교해서 ( 만약 이전의 채팅했다면 1 새로 채팅했다면 2니까 )
                                                final long getCommentKey = Long.parseLong(Objects.requireNonNull(chatDataSnapShot.getKey()));
                                                Log.d("commentsKey = commentsKey dateTime : ", String.valueOf(getCommentKey));
                                                // 채팅을 보냈으면 보내는 곳에서 putString 으로 sharedPreference 로 저장해주고
                                                // 그 값을 여기서 가져온다.
                                                final long getLastCommentKey = preferences.getLong("dateTime", 0);
                                                Log.d("getLastCommentKey = 마지막에 저장되었던 채팅 키 : ", String.valueOf(getLastCommentKey));
                                                getContent = chatDataSnapShot.child("dateTime").child("msg").getValue(String.class);
                                                Log.d("getContent = 마지막에 msg에 저장된 메세지 : ", getContent);
                                                if(getCommentKey > getLastCommentKey) {
                                                    getChatCount++;
                                                }
                                            }
                                        }
;                                    }
                                }

                                // getOtherName, getDate, getContent, getRefreshCount, getOtherUID, getCurrentMyUID
                                chatListModelList.add(new ChatListModel(getOtherName, getDate, getContent, getRefreshCount, getOtherUID, getCurrentMyUID));
                                chatRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println("onCancelled ChatViewModel : " + error);

                            }
                        });
                        Log.d("getOtherName = 다른 사람의 name", getOtherName);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
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
