package com.example.firebase_chat_basic.model;

/**
 * [ContactModel]
 *
 * This object is "contact page user list model"
 */

public class ContactModel {
    private String contact_profile_image;
    private String contact_name;
    private String contact_state_message;
    private String contact_phone_number;
    private String contact_online_state;
    private String contact_profile_background_image;
    private String chatKey;
    private String contact_my_uid;
    private String contact_other_uid;


    public ContactModel(String contact_profile_image, String contact_name, String contact_state_message, String contact_phone_number, String contact_online_state, String contact_profile_background_image, String chatKey, String contact_my_uid, String contact_other_uid) {
        this.contact_profile_image = contact_profile_image;
        this.contact_name = contact_name;
        this.contact_state_message = contact_state_message;
        this.contact_phone_number = contact_phone_number;
        this.contact_online_state = contact_online_state;
        this.contact_profile_background_image = contact_profile_background_image;
        this.chatKey = chatKey;
        this.contact_my_uid = contact_my_uid;
        this.contact_other_uid = contact_other_uid;
    }

    public String getContact_profile_image() {
        return contact_profile_image;
    }

    public void setContact_profile_image(String contact_profile_image) {
        this.contact_profile_image = contact_profile_image;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_state_message() {
        return contact_state_message;
    }

    public void setContact_state_message(String contact_state_message) {
        this.contact_state_message = contact_state_message;
    }

    public String getContact_phone_number() {
        return contact_phone_number;
    }

    public void setContact_phone_number(String contact_phone_number) {
        this.contact_phone_number = contact_phone_number;
    }

    public String getContact_online_state() {
        return contact_online_state;
    }

    public void setContact_online_state(String contact_online_state) {
        this.contact_online_state = contact_online_state;
    }

    public String getContact_profile_background_image() {
        return contact_profile_background_image;
    }

    public void setContact_profile_background_image(String contact_profile_background_image) {
        this.contact_profile_background_image = contact_profile_background_image;
    }

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    public String getContact_my_uid() {
        return contact_my_uid;
    }

    public void setContact_my_uid(String contact_my_uid) {
        this.contact_my_uid = contact_my_uid;
    }

    public String getContact_other_uid() {
        return contact_other_uid;
    }

    public void setContact_other_uid(String contact_other_uid) {
        this.contact_other_uid = contact_other_uid;
    }
}
