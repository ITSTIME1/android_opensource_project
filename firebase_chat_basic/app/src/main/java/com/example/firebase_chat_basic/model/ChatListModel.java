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
    String chatKey;
    String getKey;
    String getChatFragmentUID;

    public ChatListModel(String chatName, String chatDate, String chatContent, String chatCount, String chatKey, String getKey, String getChatFragmentUID) {
        this.chatName = chatName;
        this.chatDate = chatDate;
        this.chatContent = chatContent;
        this.chatCount = chatCount;
        this.chatKey = chatKey;
        this.getKey = getKey;
        this.getChatFragmentUID = getChatFragmentUID;
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

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    public String getGetKey() {
        return getKey;
    }

    public void setGetKey(String getKey) {
        this.getKey = getKey;
    }

    public String getGetChatFragmentUID() {
        return getChatFragmentUID;
    }

    public void setGetChatFragmentUID(String getChatFragmentUID) {
        this.getChatFragmentUID = getChatFragmentUID;
    }
}
