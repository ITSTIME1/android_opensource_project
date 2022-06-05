package com.example.firebase_chat_basic.model;


/**
 * [ChatModel]
 */
public class ChatModel {
    int chatImageViewId;
    String chatNameViewId;
    String chatContentViewId;

    public ChatModel(int chatImageViewId, String chatNameViewId, String chatContentViewId) {
        this.chatImageViewId = chatImageViewId;
        this.chatNameViewId = chatNameViewId;
        this.chatContentViewId = chatContentViewId;
    }

    public int getChatImageViewId() {
        return chatImageViewId;
    }

    public void setChatImageViewId(int chatImageViewId) {
        this.chatImageViewId = chatImageViewId;
    }

    public String getChatNameViewId() {
        return chatNameViewId;
    }

    public void setChatNameViewId(String chatNameViewId) {
        this.chatNameViewId = chatNameViewId;
    }

    public String getChatContentViewId() {
        return chatContentViewId;
    }

    public void setChatContentViewId(String chatContentViewId) {
        this.chatContentViewId = chatContentViewId;
    }
}
