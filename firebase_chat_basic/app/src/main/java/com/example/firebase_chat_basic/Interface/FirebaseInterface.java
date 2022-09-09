package com.example.firebase_chat_basic.Interface;

/**
 * [Firebase Interface]
 *
 * <Topic>
 *
 *     This interface is consist of associate with firebase.
 *
 * </Topic>
 */

public interface FirebaseInterface {
    // database method
    public default void getUserFromDataBase(){};
    public default void getChatDataFromDataBase(String getOtherName, String getOtherKey, String getPhoneNumber){};

    // for "chat method"
    public default String getUserName(int pos){
        return null;
    };
    public default String getContent(int pos) {return null;};
    public default String getEmail(int pos){
        return null;
    };
    public default String getCount(int pos) {
        return null;
    };
    public default String getDate(int pos){
        return null;
    };
    public default String getChatPrivateKey(int pos) {return null;};
    public default String getMyUID(int pos){return null;};
    public default String getOtherUID(int pos) {return null;};


    // for "contact fragment"
    public default String getProfileImage(int pos){
        return null;
    };
    public default String getBackgroundImage(int pos) {return null;};
    public default String getStateMessage(int pos) {return null;};
    public default String getPhoneNumber(int pos){
        return null;
    };
}
