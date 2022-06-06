package com.example.firebase_chat_basic.model;

/**
 * [ContactModel]
 *
 * profileImage, userName, userPhoneNumber
 */
public class ContactModel {
    String contactNameView;
    String contactNumberView;

    public ContactModel(String contactNameView, String contactNumberView) {
        this.contactNameView = contactNameView;
        this.contactNumberView = contactNumberView;
    }

    public String getContactNameView() {
        return contactNameView;
    }

    public void setContactNameView(String contactNameView) {
        this.contactNameView = contactNameView;
    }

    public String getContactNumberView() {
        return contactNumberView;
    }

    public void setContactNumberView(String contactNumberView) {
        this.contactNumberView = contactNumberView;
    }
}
