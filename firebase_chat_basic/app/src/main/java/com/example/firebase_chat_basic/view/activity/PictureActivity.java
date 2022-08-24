package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ImageViewerAdapter;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.ActivityPictureBinding;
import com.example.firebase_chat_basic.model.ChatRoomImageModel;
import com.example.firebase_chat_basic.model.ImageViewerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * [PictureActivity]
 *
 * MultiSelectImageViewer Create
 */


// simple crop adapt
// @TODO 사진 선택후 채팅으로 보낼 수 있는 로직 추가
// @TODO 동영상 선택후 채팅으로 보낼 수 있는 로직 추가
public class PictureActivity extends AppCompatActivity implements BaseInterface {
    private ImageViewerAdapter imageViewerAdapter;
    private ActivityPictureBinding activityPictureBinding;
    private DatabaseReference databaseReference;
    private String get_chat_key, get_other_uid, get_current_my_uid;
    private int maxMessageKey;
    private List<Object> getSelectedList;
    private Handler mHandler = new Handler();
    private final Date now_date = new Date();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
    private final String set_date = simpleDateFormat.format(now_date);
    private final String current_date = currentDateFormat.format(now_date);


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPictureBinding = DataBindingUtil.setContentView(this, R.layout.activity_picture);
        default_init();
        get_data_intent();
        getMessageKey();

    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        activityPictureBinding.setPictureActivity(this);
        activityPictureBinding.setLifecycleOwner(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
    }

    // 왜 아까는 안되었을까 봐야 될 거 같다.
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent getIntent = getIntent();
        get_chat_key = getIntent.getStringExtra("get_chat_key");
        get_other_uid = getIntent.getStringExtra("get_other_uid");
        get_current_my_uid = getIntent.getStringExtra("get_current_my_uid");

        Log.d("picturekey", get_chat_key);
        Log.d("get_other_uid", get_other_uid);
        Log.d("get_current_my_uid", get_current_my_uid);
        ArrayList<ImageViewerModel> imageViewerModelArrayList = new ArrayList<>();
        getSelectedList = (List<Object>) getIntent.getSerializableExtra("selectedImage");

        // 받아온 데이터가 있는지 없는지 판단
        if(getSelectedList != null ){
            for (int i = 0; i < getSelectedList.size(); i++) {
                // 받은 이미지를 imageViewerModelArrayList 에 넣어주고 그 리스트 값들을 imageViewerAdapter 로 보내준다.
                imageViewerModelArrayList.add(new ImageViewerModel(getSelectedList.get(i).toString()));
                imageViewerAdapter = new ImageViewerAdapter(imageViewerModelArrayList, PictureActivity.this);
                imageViewerAdapter.notifyDataSetChanged();
                Log.d("imageViewerList value", imageViewerModelArrayList.get(i).getImage_viewer());
            }
        }

    }


    public void getMessageKey() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("chat")) {
                    ArrayList<Integer> messageKeyList = new ArrayList<>();
                    for(DataSnapshot dataSnapshot : snapshot.child("chat").getChildren()) {
                        String key_check = dataSnapshot.getKey();
                        Log.d("dataSnapshot", String.valueOf(key_check));
                        // get_chat_key 값이랑 동일하다면
                        if (key_check != null && key_check.equals(get_chat_key)) {
                            for (DataSnapshot messageKeySnapShot : dataSnapshot.child("message").getChildren()) {
                                messageKeyList.add(Integer.valueOf(Objects.requireNonNull(messageKeySnapShot.getKey())));
                                Log.d("messageKeySnapshot", String.valueOf(messageKeySnapShot.getKey()));
                            }
                        }
                    }
                    maxMessageKey = Collections.max(messageKeyList);
                    Log.d("maxMessageKey + PictureActivity", String.valueOf(maxMessageKey));
                } else {
                    Log.d("don't have chat", "");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // async image send
    public void async_send_image() {
        Log.d("getSelectedListPictureActivity", String.valueOf(getSelectedList));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < getSelectedList.size(); i++) {
                    ChatRoomImageModel chatRoomImageModel = new ChatRoomImageModel(getSelectedList.get(i).toString(), Constants.chatImageViewType);
                    // imageURI 에 URI 를 저장.
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("imageURI").setValue(chatRoomImageModel.getImageUrl());
                    // getChatRoomViewType
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("viewType").setValue(chatRoomImageModel.getChatRoomViewType());
                    // msg 에 키 값 저장
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("mineKey").setValue(get_current_my_uid);
                    // msg 에 시간 저장
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("save_chat_date").setValue(set_date);
                    // msg 에 날짜 저장
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("currentDate").setValue(current_date);
                    // 보낸 사람 저장
                    databaseReference.child("chat").child(get_chat_key).child("보낸사람").setValue(get_current_my_uid);
                    // 받은 사람 저장
                    databaseReference.child("chat").child(get_chat_key).child("받은사람").setValue(get_other_uid);
                    Log.d("chatviewmodelimage", chatRoomImageModel.getImageUrl());
                }
            }
        },1000);
        finish();
    }

    // backPressed method
    public void onBackPressed(){
        finish();
    }

    // binding imageViewerAdapter
    public ImageViewerAdapter getImageViewerAdapter(){
        return imageViewerAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityPictureBinding = null;
    }
}
