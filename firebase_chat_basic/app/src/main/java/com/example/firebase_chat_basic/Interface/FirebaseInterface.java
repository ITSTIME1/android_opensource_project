package com.example.firebase_chat_basic.Interface;

public interface FirebaseInterface {
    public default void getUserDataBase(){};
    public default void getChatDataBase(String getOtherName, String getOtherKey){};


    public default String getUserName(int pos){
        return null;
    };
    public default String getEmail(int pos){
        return null;
    };
    public default String getProfileImage(int pos){
        return null;
    };
    public default String getPhoneNumber(int pos){
        return null;
    };

    public default String getUID(int pos) {
        return null;
    }
}
