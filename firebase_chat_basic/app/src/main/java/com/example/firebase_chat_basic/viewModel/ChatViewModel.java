package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.ArrayList;

public class ChatViewModel extends AndroidViewModel {
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private final Application context = getApplication();
    private ArrayList<ChatListModel> chatListModelList;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private final DatabaseReference databaseReference;
    private String getChatFragmentUID;
    private int unseenCount = 0;
    private String chatKey = "";
    private String lastContent = "";
    private String getKey = "";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public ChatViewModel(String getUID, Application application){
        super(application);
        preferences = context.getSharedPreferences("message", Context.MODE_PRIVATE);
        System.out.println(preferences + "이건 preference");
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
        // 참조 되어 있는 Root에 있는 값들을 가져옴.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 기존 배열리스트를 한번 초기화
                chatListModelList.clear();
                // "users" 를 하나 만들고. users 하위에 있는 값들을 가져오는 반복문.
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    // users 하위에 있는 authentication uid 가져오기.
                    getKey = dataSnapshot.getKey();

                    // getChatFragmentUID = ChatFragment 에서 bundle을 통해서 받은 UID.
                    // if 문은 나랑 다른 UID를 가진 값만 가지고 와야 되기 때문에 내가 전달 받은 getChatFragmentUID랑 비교해서
                    // 값이 다른 것만 userName, unseencount 를 가져옴.
                    assert getKey != null;
                    if(!getKey.equals(getChatFragmentUID)) {
                        String getUserName = dataSnapshot.child("name").getValue(String.class);
                        String getUnseenCount = Integer.toString(unseenCount);


                        // Root 단에 users 말고 chat을 생성해서 값을 읽어오는 ValueEventListener를 달아줌.
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // chat 안에 있는 개수를 반환함.
                                int getChatCounts = (int) snapshot.getChildrenCount();
                                if(getChatCounts > 0) {
                                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                        if(dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("message")) {
                                            final String getChatUID = dataSnapshot1.getKey();
                                            chatKey = getChatUID;
                                            final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                            final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                            assert getUserOne != null;
                                            if((getUserOne.equals(getChatFragmentUID) && getUserTwo.equals(getKey)) || (getUserOne.equals(getKey) && getUserTwo.equals(getChatFragmentUID))) {
                                                for (DataSnapshot chatSnapShot : dataSnapshot1.child("message").getChildren()) {
                                                    // messageKey
                                                    final long getMessageKey = Long.parseLong(chatSnapShot.getKey());

                                                    System.out.println(getMessageKey + " message key");
                                                    // lastMessage key
                                                    // @TODO SharedPreference
                                                    // @TODO getLastSeenMessage = sharepreference에서 key값 저장되어 있는거 가져오기.

                                                    // @TODO 이걸 어떻게 키 값으로 변형할까.
                                                    // @TODO 이제 getChatUID가 작동하나 확인.
                                                    assert getChatUID != null;
                                                    final long getLastSeenMessage = preferences.getInt("lastSeenMessage", Integer.parseInt(getChatUID));
                                                    System.out.println(getLastSeenMessage + " message key");


                                                    lastContent = chatSnapShot.child("msg").getValue(String.class);

                                                    if(getMessageKey > getLastSeenMessage) {
                                                        unseenCount++;
                                                    }
                                                }
                                            }

                                        }


                                    }
                                }
                                chatListModelList.add(new ChatListModel(getUserName, "2022-06-09", lastContent, getUnseenCount, chatKey, getKey, getChatFragmentUID));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println(error);

                            }
                        });
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

    public String getChatKey(int pos){
        return chatListModelList.get(pos).getChatKey();
    }

    public String getKey(int pos) {
        return chatListModelList.get(pos).getGetKey();
    }

    public String getGetChatFragmentUID(int pos) {
        return chatListModelList.get(pos).getGetChatFragmentUID();
    }


}
