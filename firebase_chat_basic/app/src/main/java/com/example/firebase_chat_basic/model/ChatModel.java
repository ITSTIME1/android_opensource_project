package com.example.firebase_chat_basic.model;


/**
 * [ChatModel]
 */
public class ChatModel {
    String chatNameView;
    String chatContentView;

    public ChatModel(String chatNameView, String chatContentView) {
        this.chatNameView = chatNameView;
        this.chatContentView = chatContentView;
    }

    public String getChatNameView() {
        return chatNameView;
    }

    public void setChatNameView(String chatNameView) {
        this.chatNameView = chatNameView;
    }

    public String getChatContentView() {
        return chatContentView;
    }

    public void setChatContentView(String chatContentView) {
        this.chatContentView = chatContentView;
    }
}

