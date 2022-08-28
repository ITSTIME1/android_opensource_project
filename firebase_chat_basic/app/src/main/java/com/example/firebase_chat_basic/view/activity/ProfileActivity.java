package com.example.firebase_chat_basic.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityProfileBinding;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Objects;

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
    private String client_state_message,
        client_name,
        chatKey,
        client_my_uid,
        client_other_uid,
        client_phone_number;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        get_intent_data();
        default_init();
        go_to_chat_room();
        call_method();
    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        activityProfileBinding.profileName.setText(client_name);
        activityProfileBinding.profileStateMessage.setText(client_state_message);
    }

    public void get_intent_data() {
        Intent intent = getIntent();
        // 이름, 프로필 이미지, 배경 이미지, 상태 메세지, 상대방 uid, 나의 uid
        client_name = intent.getStringExtra("client_name");
//        client_profile_image = intent.getStringExtra("client_profile_image");
//        client_background_image = intent.getStringExtra("client_background_image");
        client_state_message = intent.getStringExtra("client_state_message");
        client_phone_number = intent.getStringExtra("client_phone_number");
        chatKey = intent.getStringExtra("chatKey");
        client_my_uid = intent.getStringExtra("client_my_uid");
        client_other_uid = intent.getStringExtra("client_other_uid");

        if (client_state_message == null) {
            client_state_message = "Default Message";
        }
    }

    public void go_to_chat_room() {
        activityProfileBinding.profileChatLayout.setOnClickListener(view -> {
            Log.d("chat gif 클릭 잘 됨 ", "");
            Intent goToChatRoom = new Intent(view.getContext(), ChatRoomActivity.class);
            goToChatRoom.putExtra("getChatKey", chatKey);
            goToChatRoom.putExtra("getOtherName", client_name);
            goToChatRoom.putExtra("getOtherUID", client_other_uid);
            goToChatRoom.putExtra("getCurrentMyUID", client_my_uid);
            startActivity(goToChatRoom);
            finish();
        });
    }

    // call_method
    public void call_method() {
        activityProfileBinding.profileContactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"0"+client_phone_number));
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
