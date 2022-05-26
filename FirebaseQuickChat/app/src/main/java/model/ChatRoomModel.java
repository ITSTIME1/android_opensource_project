package model;


/**
 * [Favourite DataModel]
 */

public class ChatRoomModel {
    private int chatRoomImage;
    private int chatRoomStarImage;
    private String chatRoomName;
    private String chatRoomDate;

    public ChatRoomModel(int chatRoomImage, int chatRoomStarImage, String chatRoomName, String chatRoomDate) {
        this.chatRoomImage = chatRoomImage;
        this.chatRoomStarImage = chatRoomStarImage;
        this.chatRoomName = chatRoomName;
        this.chatRoomDate = chatRoomDate;
    }

    public int getChatRoomImage() {
        return chatRoomImage;
    }

    public void setChatRoomImage(int chatRoomImage) {
        this.chatRoomImage = chatRoomImage;
    }

    public int getChatRoomStarImage() {
        return chatRoomStarImage;
    }

    public void setChatRoomStarImage(int chatRoomStarImage) {
        this.chatRoomStarImage = chatRoomStarImage;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public String getChatRoomDate() {
        return chatRoomDate;
    }

    public void setChatRoomDate(String chatRoomDate) {
        this.chatRoomDate = chatRoomDate;
    }
}
