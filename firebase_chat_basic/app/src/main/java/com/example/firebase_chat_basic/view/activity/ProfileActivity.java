package com.example.firebase_chat_basic.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

// @TODO 첨부파일 구현 grid layout 구현.

/**
 * <Topic>
 *     이거다.
 * </Topic>
 */
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
        get_intent_data();
        default_init();
        glide();
        go_to_chat_room();
        swipe_scroll();
    }
    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        activityProfileBinding.profileName.setText(client_name);
        activityProfileBinding.profileStateMessage.setText(client_state_message);
    }

    public void get_intent_data(){
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
    }
    public void go_to_chat_room(){
        activityProfileBinding.chatImageGifLayout.setOnClickListener(view -> {
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

    // glide profile_image, background_image
    public void glide(){
        Glide.with(this).load(R.raw.iconmessage).into(activityProfileBinding.chatImageGif);
        Glide.with(this).load(R.raw.iconcontact).into(activityProfileBinding.contactImageGif);
        Glide.with(this).load(R.raw.iconsave).into(activityProfileBinding.saveImageGif);
    }

    // swipe_scroll
    public void swipe_scroll(){
        activityProfileBinding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {}
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    activityProfileBinding.scrollableTouchText.setText("위로 올려 보세요");
                    activityProfileBinding.scrollableTouchIcon.setImageResource(R.drawable.ic_chat_profile_up_panel_icon);
                } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    activityProfileBinding.scrollableTouchText.setText("한번 터치해 보세요");
                    activityProfileBinding.scrollableTouchIcon.setImageResource(R.drawable.ic_chat_profile_down_panel_icon);
                }
            }
        });
    }
}
