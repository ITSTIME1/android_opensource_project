package model;


/**
 * [ChatRooms DataModel]
 */
public class ChatRoom2Model {
    private int chatRoom2Image;
    private String chatRoom2ProfileName;
    private String chatRoom2Message;
    private String chatRoom2Date;
    private String chatRoom2Count;


    public ChatRoom2Model(int chatRoom2Image, String chatRoom2ProfileName, String chatRoom2Message, String chatRoom2Date, String chatRoom2Count) {
        this.chatRoom2Image = chatRoom2Image;
        this.chatRoom2ProfileName = chatRoom2ProfileName;
        this.chatRoom2Message = chatRoom2Message;
        this.chatRoom2Date = chatRoom2Date;
        this.chatRoom2Count = chatRoom2Count;
    }

    public int getChatRoom2Image() {
        return chatRoom2Image;
    }

    public void setChatRoom2Image(int chatRoom2Image) {
        this.chatRoom2Image = chatRoom2Image;
    }

    public String getChatRoom2ProfileName() {
        return chatRoom2ProfileName;
    }

    public void setChatRoom2ProfileName(String chatRoom2ProfileName) {
        this.chatRoom2ProfileName = chatRoom2ProfileName;
    }

    public String getChatRoom2Message() {
        return chatRoom2Message;
    }

    public void setChatRoom2Message(String chatRoom2Message) {
        this.chatRoom2Message = chatRoom2Message;
    }

    public String getChatRoom2Date() {
        return chatRoom2Date;
    }

    public void setChatRoom2Date(String chatRoom2Date) {
        this.chatRoom2Date = chatRoom2Date;
    }

    public String getChatRoom2Count() {
        return chatRoom2Count;
    }

    public void setChatRoom2Count(String chatRoom2Count) {
        this.chatRoom2Count = chatRoom2Count;
    }
}
