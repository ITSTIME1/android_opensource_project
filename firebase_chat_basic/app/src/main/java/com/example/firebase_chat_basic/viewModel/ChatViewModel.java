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
    private String getCurrentUserUID;
    private int unseenCount = 0;
    private String chatKey = "";
    private String lastContent = "메세지가 없어요!";
    private String getOtherUID = "";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public ChatViewModel(String getChatFragmentUID, Application application){
        super(application);
        preferences = context.getSharedPreferences("message", Context.MODE_PRIVATE);
        System.out.println(preferences + "이건 preference");
        editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        getCurrentUserUID = getChatFragmentUID;


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
                // chatModelList 한번 초기화
                chatListModelList.clear();
                // "users" 를 하나 만들고. users 하위에 있는 값들을 가져오는 반복문.
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    // users 하위에 있는 other authentication uid 가져오기.
                    getOtherUID = dataSnapshot.getKey();



                    // 다른 사람의 이름, 채팅 개수 를 가져오고
                    // chat 이라는 하위 목록을 하나 만들어서 읽어온다.
                    // 읽어올떄 chat 하위 목록에 있는 값들을 가져오는데 이때 처음에는 값이 들어가 있지 않다.
                    // 다시 말해 채팅을 보내지 않았으므로 chat 하위 목록은 null 을 보낼 것이며\
                    // getChatCount 의 수가 0보다 크다면 chat 하위 목록의 아이템들을 다 불러오고
                    // chat이 user_1, user_2, message 값을 가지고 있다면 이 값들은 메세지 전송 버튼을 눌렀을 때
                    // 누가 보냈는지 user_1, user_2 에 표시해주고, message 목록을 따로 만들어서 chat 안에 가지고 있는다.
                    // 그럼 다시 말해 버튼을 눌럿을 때 하위 목록이 생성되는 것이므로
                    // 메세지 전송 버튼을 누르기 전까지는 항목이 생성되지 않으므로
                    // 그냥 초기 list 값만 표시도게 된다.
                    assert getOtherUID != null;
                    if(!getOtherUID.equals(getCurrentUserUID)) {
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
                                            if((getUserOne.equals(getCurrentUserUID) && getUserTwo.equals(getOtherUID)) || (getUserOne.equals(getOtherUID) && getUserTwo.equals(getCurrentUserUID))) {
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
                                chatListModelList.add(new ChatListModel(getUserName, "2022-06-09", lastContent, getUnseenCount, chatKey, getOtherUID, getCurrentUserUID));
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

    public String getOtherKey(int pos) {
        return chatListModelList.get(pos).getGetOtherUID();
    }

    public String getChatFragmentUIDKey(int pos) {
        return chatListModelList.get(pos).getGetCurrentUserUID();
    }


}
