package com.example.firebase_chat_basic.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityProfileBinding;

/**
 * [ProfileActivity]
 *
 * <Topic>
 *     This is for "user profile activity".
 *     when create new user and save realtime database
 *     display on "contact activity"
 *     and when you touch the "profile bottom sheet dialog" that it comes up.
 * </Topic>
 */

public class ProfileActivity extends AppCompatActivity implements BaseInterface {
    private ActivityProfileBinding activityProfileBinding;
//    private String client_profile_image;
//    private String client_background_image;
    private String clientStateMessage,
        clientName,
        chatKey,
        clientMyUID,
        clientOtherUID,
        clientPhoneNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        getDataIntent();
        initialize();
        goToChatRoom();
        callToUser();
    }

    @Override
    public void initialize() {
        BaseInterface.super.initialize();
        activityProfileBinding.profileName.setText(clientName);
        activityProfileBinding.profileStateMessage.setText(clientStateMessage);
    }

    @Override
    public void getDataIntent() {
        BaseInterface.super.getDataIntent();
        Intent intent = getIntent();
        // 이름, 프로필 이미지, 배경 이미지, 상태 메세지, 상대방 uid, 나의 uid
        clientName = intent.getStringExtra("clientName");
//        client_profile_image = intent.getStringExtra("client_profile_image");
//        client_background_image = intent.getStringExtra("client_background_image");
        clientStateMessage = intent.getStringExtra("clientStateMessage");
        clientPhoneNumber = intent.getStringExtra("clientPhoneNumber");
        chatKey = intent.getStringExtra("chatKey");
        clientMyUID = intent.getStringExtra("clientMyUID");
        clientOtherUID = intent.getStringExtra("clientOtherUID");

        if (clientStateMessage == null) {
            clientStateMessage = "Default Message";
        }
    }

    public void goToChatRoom() {
        activityProfileBinding.profileChatLayout.setOnClickListener(view -> {
            Log.d("chat gif 클릭 잘 됨 ", "");
            Intent goToChatRoom = new Intent(view.getContext(), ChatRoomActivity.class);
            goToChatRoom.putExtra("getChatKey", chatKey);
            goToChatRoom.putExtra("getOtherName", clientName);
            goToChatRoom.putExtra("getOtherUID", clientOtherUID);
            goToChatRoom.putExtra("getCurrentMyUID", clientMyUID);
            startActivity(goToChatRoom);
            finish();
        });
    }

    // callToUser
    public void callToUser() {
        activityProfileBinding.profileContactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"0"+ clientPhoneNumber));
                try{
                    startActivity(callIntent);
                }catch (Exception e) {
                    e.printStackTrace();
                    Log.d("콜링 메서드 에러", "");
                }
            }
        });
    }


    // @TODO 상태메세지 기능구현.
    // @TODO 예약메세지 기능구현.
    // @TODO 프로필 수정 메세지 기능구현.
    // @TODO 배경 사진 수정 기능 구현.
    public void reservation_message(){}
    public void state_message() {}
    public void profile_image_change(){}
    public void background_image_change(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityProfileBinding = null;
    }
}
