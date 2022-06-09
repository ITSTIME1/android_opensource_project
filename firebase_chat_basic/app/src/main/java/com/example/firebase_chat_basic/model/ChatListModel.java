package com.example.firebase_chat_basic.model;

/**
 * [ChatListModel]
 *
 * ChatFragment ItemList
 */
public class ChatListModel {
    String chatName;
    String chatDate;
    String chatContent;
    String chatCount;

    public ChatListModel(String chatName, String chatDate, String chatContent, String chatCount) {
        this.chatName = chatName;
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

    public String getChatCount() {
        return chatCount;
    }

    public void setChatCount(String chatCount) {
        this.chatCount = chatCount;
    }
}
