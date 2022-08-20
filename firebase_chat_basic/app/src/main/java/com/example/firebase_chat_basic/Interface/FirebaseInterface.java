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
    public default void get_user_database(){};
    public default void get_chat_database(String getOtherName, String getOtherKey, String getPhoneNumber){};

    // for "chat method"
    public default String get_user_name(int pos){
        return null;
    };
    public default String get_content(int pos) {return null;};
    public default String get_email(int pos){
        return null;
    };
    public default String get_count(int pos) {
        return null;
    };
    public default String get_date(int pos){
        return null;
    };
    public default String get_chat_key(int pos) {return null;};
    public default String get_my_uid(int pos){return null;};
    public default String get_other_uid(int pos) {return null;};


    // for "contact fragment"
    public default String get_profile_image(int pos){
        return null;
    };
    public default String get_background_image(int pos) {return null;};
    public default String get_state_message(int pos) {return null;};
    public default String get_phone_number(int pos){
        return null;
    };
}
