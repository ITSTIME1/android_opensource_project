package com.example.firebase_chat_basic.model;

/**
 * [ChatListModel]
 *
 * ChatFragment ItemList
 */
public class ChatListModel {
    String chatName;
    String chatProfileImage;
    String chatDate;
    String chatContent;
    int chatCount;

    public ChatListModel(String chatName, String chatProfileImage, String chatDate, String chatContent, int chatCount) {
        this.chatName = chatName;
        this.chatProfileImage = chatProfileImage;
        this.chatDate = chatDate;
        this.chatContent = chatContent;
        this.chatCount = chatCount;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatProfileImage() {
        return chatProfileImage;
    }

    public void setChatProfileImage(String chatProfileImage) {
        this.chatProfileImage = chatProfileImage;
    }

    public String getChatDate() {
        return chatDate;
    }

    public void setChatDate(String chatDate) {
        this.chatDate = chatDate;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public int getChatCount() {
        return chatCount;
    }

    public void setChatCount(int chatCount) {
        this.chatCount = chatCount;
    }
}
