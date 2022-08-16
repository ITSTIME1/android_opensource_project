package com.example.firebase_chat_basic.model;

/**
 * [ChatRoomImageModel]
 *
 * <Topic>
 *     This model is for "displays image to chatroom"
 * </Topic>
 */

public class ChatRoomImageModel {
    private String imageUrl;
    private int chatRoomViewType;

    public ChatRoomImageModel(String imageUrl, int chatRoomViewType) {
        this.imageUrl = imageUrl;
        this.chatRoomViewType = chatRoomViewType;
    }

    public int getChatRoomViewType() {
        return chatRoomViewType;
    }

    public void setChatRoomViewType(int chatRoomViewType) {
        this.chatRoomViewType = chatRoomViewType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
