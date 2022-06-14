package com.example.firebase_chat_basic.model;

/**
 * [ChatListModel]
 *
 * chatName, chatDate, chatContent, chatCount
 * @TODO create ChatProfile
 */
public class ChatListModel {
    String chatName;
    String chatDate;
    String chatContent;
    String chatCount;
    String chatOtherUID;
    String chatMyUID;

    public ChatListModel(String chatName, String chatDate, String chatContent, String chatCount, String chatOtherUID, String chatMyUID) {
        this.chatName = chatName;
        this.chatDate = chatDate;
        this.chatContent = chatContent;
        this.chatCount = chatCount;
        this.chatOtherUID = chatOtherUID;
        this.chatMyUID = chatMyUID;
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

    public String getChatOtherUID() {
        return chatOtherUID;
    }

    public void setChatOtherUID(String chatOtherUID) {
        this.chatOtherUID = chatOtherUID;
    }

    public String getChatMyUID() {
        return chatMyUID;
    }

    public void setChatMyUID(String chatMyUID) {
        this.chatMyUID = chatMyUID;
    }
}
