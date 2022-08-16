package com.example.firebase_chat_basic.model;


/**
 * [UserModel]
 *
 * This is for "camera activity"
 */
public class UserModel {
    private String user_uid;
    private String user_name;
    private String user_email;
    private String user_phone_number;
    private String user_profile_image;
    private String user_background_image;
    private String user_online_state;
    private String user_state_message;

    public UserModel(String user_uid, String user_name, String user_email, String user_phone_number, String user_profile_image, String user_background_image, String user_online_state, String user_state_message) {
        this.user_uid = user_uid;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone_number = user_phone_number;
        this.user_profile_image = user_profile_image;
        this.user_background_image = user_background_image;
        this.user_online_state = user_online_state;
        this.user_state_message = user_state_message;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getUser_profile_image() {
        return user_profile_image;
    }

    public void setUser_profile_image(String user_profile_image) {
        this.user_profile_image = user_profile_image;
    }

    public String getUser_background_image() {
        return user_background_image;
    }

    public void setUser_background_image(String user_background_image) {
        this.user_background_image = user_background_image;
    }

    public String getUser_online_state() {
        return user_online_state;
    }

    public void setUser_online_state(String user_online_state) {
        this.user_online_state = user_online_state;
    }

    public String getUser_state_message() {
        return user_state_message;
    }

    public void setUser_state_message(String user_state_message) {
        this.user_state_message = user_state_message;
    }
}
