package com.example.firebase_chat_basic.model;

public class ChatRoomModel {
    private String setKey;
    private String chat_message;
    private String chat_date;

    public ChatRoomModel(String setKey, String chat_message, String chat_date) {
        this.setKey = setKey;
        this.chat_message = chat_message;
        this.chat_date = chat_date;
    }

    public String getSetKey() {
        return setKey;
    }

    public void setSetKey(String setKey) {
        this.setKey = setKey;
    }

    public String getChat_message() {
        return chat_message;
    }

    public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }

    public String getChat_date() {
        return chat_date;
    }

    public void setChat_date(String chat_date) {
        this.chat_date = chat_date;
    }
}

