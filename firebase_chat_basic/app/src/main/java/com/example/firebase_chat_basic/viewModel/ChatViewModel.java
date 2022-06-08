package com.example.firebase_chat_basic.viewModel;
import androidx.lifecycle.ViewModel;

import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    private ArrayList<ChatListModel> chatListModelArrayList;
    private DatabaseReference databaseReference;
    private ChatRecyclerAdapter chatRecyclerAdapter;

    public ChatViewModel() {
        if(chatListModelArrayList == null) {
            chatListModelArrayList = new ArrayList<>();
        }
    }

    // 1. realTimeDatabase 에서 유저 정보를 가져온다.
    // 2. name을 가져온다.

    public void getDataFromRealtimeBase(){

    }


    public ChatRecyclerAdapter getChatRecyclerAdapter(){
        return chatRecyclerAdapter;
    }

    public ArrayList<ChatListModel> getChatListModelArrayList(){
        return chatListModelArrayList;
    }

    public String getProfileImage(int pos){
        return chatListModelArrayList.get(pos).getChatProfileImage();
    }

    public String getName(int pos) {
        return chatListModelArrayList.get(pos).getChatName();
    }

    public String getContent(int pos) {
        return chatListModelArrayList.get(pos).getChatContent();
    }

    public int getCount(int pos) {
        return chatListModelArrayList.get(pos).getChatCount();
    }

    public String getDate(int pos) {
        return chatListModelArrayList.get(pos).getChatDate();
    }



}

