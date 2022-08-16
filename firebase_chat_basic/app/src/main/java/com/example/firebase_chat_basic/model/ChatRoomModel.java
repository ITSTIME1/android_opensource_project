package com.example.firebase_chat_basic.model;

/**
 * [ChatRoomModel]
 *
 * <Topic>
 *     This model is for "chat room every message"
 * </Topic>
 *
 */
public class ChatRoomModel {
    private String setKey;
    private String chat_message;
    private String chat_date;
    private String current_date;
    private int viewType;
    private String imageURL;

    public ChatRoomModel(String setKey, String chat_date, String current_date, int viewType, String imageURL) {
        this.setKey = setKey;
        this.chat_date = chat_date;
        this.current_date = current_date;
        this.viewType = viewType;
        this.imageURL = imageURL;
    }

    // overloading constructor
    public ChatRoomModel(String setKey, String chat_message, String chat_date, String current_date, int viewType) {
        this.setKey = setKey;
        this.chat_message = chat_message;
        this.chat_date = chat_date;
        this.current_date = current_date;
        this.viewType = viewType;
    }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
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

