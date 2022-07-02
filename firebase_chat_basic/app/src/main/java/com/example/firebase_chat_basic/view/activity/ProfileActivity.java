package com.example.firebase_chat_basic.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements BaseInterface {
    private ActivityProfileBinding activityProfileBinding;
    private String client_profile_image;
    private String client_background_image;
    private String client_state_message;
    private String client_name;
    private String chatKey;
    private String client_my_uid;
    private String client_other_uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        defaultInit();
        glide();
        goToChatRoom();
    }


    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        Intent intent = getIntent();
        // 이름, 프로필 이미지, 배경 이미지, 상태 메세지, 상대방 uid, 나의 uid
        client_name = intent.getStringExtra("client_name");
        client_profile_image = intent.getStringExtra("client_profile_image");
        client_background_image = intent.getStringExtra("client_background_image");
        client_state_message = intent.getStringExtra("client_state_message");
        chatKey = intent.getStringExtra("chatKey");
        client_my_uid = intent.getStringExtra("client_my_uid");
        client_other_uid = intent.getStringExtra("client_other_uid");

        if(client_state_message == null ){
            client_state_message = "Default Message";
        }

        activityProfileBinding.profileName.setText(client_name);
        activityProfileBinding.profileStateMessage.setText(client_state_message);
    }

    public void glide(){
        ImageView chat = activityProfileBinding.chatImageGif;
        GlideDrawableImageViewTarget chat_gif_image = new GlideDrawableImageViewTarget(chat);
        Glide.with(this).load(R.raw.iconmessage).into(chat_gif_image);

        ImageView contact = activityProfileBinding.contactImageGif;
        GlideDrawableImageViewTarget contact_gif_image = new GlideDrawableImageViewTarget(contact);
        Glide.with(this).load(R.raw.iconcontact).into(contact_gif_image);

        ImageView save = activityProfileBinding.saveImageGif;
        GlideDrawableImageViewTarget save_gif_image = new GlideDrawableImageViewTarget(save);
        Glide.with(this).load(R.raw.iconsave).into(save_gif_image);
    }

    // @TODO 채팅 하기 클릭시 채팅 룸으로 값 보내주기.
    // Contact 에서 클릭하면 ChatRoomActivity 로 가는데 이 때 필요한건 chatKey 가 필요한것임.
    // 때문에 ChatRoomActivity 에 key 값과 나머지 값들을 보내주고
    // chatRoomActivity 에서 클릭시에 chatRoomActivity 에서 받은 ChatKey 값을 받아서 메세지를 생성하게 됨.
    // 보낼게 이름, uid, 프로필 이미지, 내 아이디,
    public void goToChatRoom(){
        activityProfileBinding.chatImageGifLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("chat gif 클릭 잘 됨 ", "");
                Intent goToChatRoom = new Intent(view.getContext(), ChatRoomActivity.class);
                goToChatRoom.putExtra("getChatKey", chatKey);
                goToChatRoom.putExtra("getOtherName", client_name);
                goToChatRoom.putExtra("getOtherUID", client_other_uid);
                goToChatRoom.putExtra("getCurrentMyUID", client_my_uid);

                startActivity(goToChatRoom);
                finish();
            }
        });
    }


}
