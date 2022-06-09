package com.example.firebase_chat_basic.viewModel;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatViewModel {
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private ArrayList<ChatListModel> chatListModelList;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private final DatabaseReference databaseReference;

    public ChatViewModel(){
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);

        if(chatListModelList == null ){
            chatListModelList = new ArrayList<>();
        }
        if(chatRecyclerAdapter == null) {
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
        }
        getData();
    }

    public void getData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 기존 배열리스트를 한번 초기화
                chatListModelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    String getUserName = dataSnapshot.child("name").getValue(String.class);
                    // chatName, chatDate, chatContent chatCount
                    chatListModelList.add(new ChatListModel(getUserName, "2022-06-09", "testing 용도", "0"));
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
