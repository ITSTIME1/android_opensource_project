package com.example.firebase_chat_basic.viewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel implements BaseInterface {
    private List<ChatModel> chatModelList;
    private ChatRecyclerAdapter chatRecyclerAdapter;

    public ChatViewModel() {
        if(chatModelList == null && chatRecyclerAdapter == null) {
            chatModelList = new ArrayList<>();
            chatRecyclerAdapter = new ChatRecyclerAdapter(this);
        }
        initRetrofit();
    }

    /**
     * @TODO Retrofit Firebase Connect
     */
    @Override
    public void initRetrofit() {
        BaseInterface.super.initRetrofit();
        chatModelList.add(new ChatModel("HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
        chatModelList.add(new ChatModel( "HongTaeSun", "Welcome To my firebase quick chat project"));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onCreate(){
        chatRecyclerAdapter.notifyDataSetChanged();
    }

    public ChatRecyclerAdapter getChatRecyclerAdapter() {
        return chatRecyclerAdapter;
    }

    public List<ChatModel> getChatModelList() {
        return chatModelList;
    }

    public String getTitle(int pos) {
        return chatModelList.get(pos).getChatNameView();
    }

    public String getContent(int pos) {
        return chatModelList.get(pos).getChatContentView();
    }
}
