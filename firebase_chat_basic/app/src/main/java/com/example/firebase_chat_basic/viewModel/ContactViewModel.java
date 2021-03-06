package com.example.firebase_chat_basic.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.example.firebase_chat_basic.Interface.FirebaseInterface;
import com.example.firebase_chat_basic.adapters.ContactRecyclerAdapter;
import com.example.firebase_chat_basic.model.ContactModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

// @TODO Contact 정보 눌렀을 때 정보 XML 짜기
public class ContactViewModel extends AndroidViewModel implements FirebaseInterface {
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private final DatabaseReference databaseReference;
    private final ArrayList<ContactModel> contact_list;
    private ContactRecyclerAdapter contactRecyclerAdapter;
    private String firebase_my_key;
    private String firebase_my_profile_background_image;
    private String firebase_my_phone_number;
    private String contact_profile_image;
    private String contact_profile_background_image;
    private String contact_name;
    private String contact_state_message;
    private String contact_phone_number;
    private String contact_online_state;
    private String contact_my_uid;
    private String contact_other_uid;

    // constructor init
    // getCurrentMyUId = reference 에서 가지고 옵시다.
    public ContactViewModel(Application application, String getCurrentMyUID, String getProfileBackgroundImage) {
        super(application);


        if (getCurrentMyUID != null && getProfileBackgroundImage != null) {
            firebase_my_key = getCurrentMyUID;
            firebase_my_profile_background_image = getProfileBackgroundImage;
        }
        Log.d("firebase_my_key", String.valueOf(firebase_my_key));
        contact_list = new ArrayList<>();
        contactRecyclerAdapter = new ContactRecyclerAdapter(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);


        get_user_database();
    }


    @Override
    public void get_user_database() {
        FirebaseInterface.super.get_user_database();
        // user의 정보를 딱 한번만 가지고 와서 리스트에 넣어준다.
        // 구지 수신을 대기할 필요가 없음.
        // @TODO Online 상태랑 상태메세지 users에 넣어야됨.
        databaseReference.child("users").addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("ContactModel Create", "");
                final String getUserKey = snapshot.getKey();
                Log.d("getUserKey", getUserKey);
                Log.d("myKey", String.valueOf(firebase_my_key));

                assert getUserKey != null;
                if (!getUserKey.equals(firebase_my_key)) {

                    contact_profile_image = snapshot.child("profileImage").getValue(String.class);
                    contact_name = snapshot.child("name").getValue(String.class);
                    contact_phone_number = snapshot.child("phoneNumber").getValue(String.class);
                    contact_profile_background_image = snapshot.child("backgroundImage").getValue(String.class);
                    contact_online_state = snapshot.child("online").getValue(String.class);
                    contact_state_message = snapshot.child("stateMessage").getValue(String.class);
                    contact_my_uid = firebase_my_key;
                    contact_other_uid = snapshot.child("uid").getValue(String.class);

                    String chat_key = firebase_my_key + snapshot.child("uid").getValue(String.class);

                    // 그 사람의 contact_list 값.
                    contact_list.add(new ContactModel(contact_profile_image,
                            contact_name,
                            contact_state_message,
                            contact_phone_number,
                            contact_online_state,
                            contact_profile_background_image,
                            chat_key,
                            contact_my_uid,
                            contact_other_uid));

                    contactRecyclerAdapter.notifyDataSetChanged();

                    Log.d("contact to chat_key 확인용 ", contact_list.get(0).getChatKey());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    @Override
    public String get_user_name(int pos) {
        return contact_list.get(pos).getContact_name();
    }

    @Override
    public String get_phone_number(int pos) {
        return contact_list.get(pos).getContact_phone_number();
    }

    @Override
    public String get_profile_image(int pos) {
        return contact_list.get(pos).getContact_profile_image();
    }

    public String get_background_profile_image(int pos) {
        return contact_list.get(pos).getContact_profile_background_image();
    }

    public String get_state_message(int pos) {
        return contact_list.get(pos).getContact_state_message();
    }

    public String get_chat_key(int pos) {
        return contact_list.get(pos).getChatKey();
    }

    public String get_my_uid(int pos) {
        return contact_list.get(pos).getContact_my_uid();
    }

    public String get_other_uid(int pos) {
        return contact_list.get(pos).getContact_other_uid();
    }


    public ContactRecyclerAdapter getContactRecyclerAdapter() {
        return contactRecyclerAdapter;
    }

    public ArrayList<ContactModel> getContactModelList() {
        return contact_list;
    }


}
