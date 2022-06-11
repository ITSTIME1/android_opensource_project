package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
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
import java.util.ArrayList;

public class ChatViewModel extends Application {
    private static Context context;
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private ArrayList<ChatListModel> chatListModelList;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private final DatabaseReference databaseReference;
    private String getChatFragmentUID;
    private int unseenCount = 0;
    private String lastContent = "";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public ChatViewModel(String getUID){
        // application context
        ChatViewModel.context = getApplicationContext();
        preferences = ChatViewModel.context.getSharedPreferences("message", Context.MODE_PRIVATE);
        editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        getChatFragmentUID = getUID;


        if(chatListModelList == null ){
            chatListModelList = new ArrayList<>();
        }
        if(chatRecyclerAdapter == null) {
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
        }
        getRealTimeDatabase();
    }

    public void getRealTimeDatabase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 기존 배열리스트를 한번 초기화
                chatListModelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    // users 하위에 있는 authentication uid 가져오기.
                    final String getKey = dataSnapshot.getKey();

                    // getChatFragmentUID = ChatFragment 에서 bundle을 통해서 받은 UID.
                    assert getKey != null;
                    if(!getKey.equals(getChatFragmentUID)) {
                        String getUserName = dataSnapshot.child("name").getValue(String.class);
                        String getUnseenCount = Integer.toString(unseenCount);


                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // chat 안에 있는 개수를 반환함.
                                final int getChatCounts = (int) snapshot.getChildrenCount();
                                if(getChatCounts > 0) {
                                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                        final String getChatUID = dataSnapshot1.getKey();
                                        final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                        final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                        assert getUserOne != null;
                                        if((getUserOne.equals(getChatFragmentUID) && getUserTwo.equals(getKey)) || (getUserOne.equals(getKey) && getUserTwo.equals(getChatFragmentUID))) {
                                            for (DataSnapshot chatSnapShot : dataSnapshot1.child("message").getChildren()) {
                                                // messageKey
                                                final long getMessageKey = Long.parseLong(chatSnapShot.getKey());
                                                // lastMessage key
                                                // @TODO SharedPreference
                                                // @TODO getLastSeenMessage = sharepreference에서 key값 저장되어 있는거 가져오기.

                                                // @TODO 이걸 어떻게 키 값으로 변형할까.
                                                // @TODO 이제 getChatUID가 작동하나 확인.
                                                assert getChatUID != null;
                                                final long getLastSeenMessage = preferences.getInt("lastSeenMessage", Integer.parseInt(getChatUID));


                                                lastContent = chatSnapShot.child("msg").getValue(String.class);

                                                if(getMessageKey > getLastSeenMessage) {
                                                    unseenCount++;
                                                }
                                            }
                                        }
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println(error);

                            }
                        });



                        chatListModelList.add(new ChatListModel(getUserName, "2022-06-09", lastContent, getUnseenCount));
                    }
                }
                chatRecyclerAdapter.notifyDataSetChanged();

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

    public ArrayList<ChatListModel> getChatListModelList(){
        return chatListModelList;
    }

    public ChatRecyclerAdapter getChatRecyclerAdapter(){
        return chatRecyclerAdapter;
    }



}
