package com.example.firebase_chat_basic.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ChatRoomRecyclerAdapter;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.ActivityChatroomBinding;
import com.example.firebase_chat_basic.databinding.ActivityChatroomUploadBottomDialogBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.example.firebase_chat_basic.view.fragment.ChatRoomBottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * [ChatRoomActivity Introduce]
 *
 * <Topic>
 * "ChatRoomActivity" has 6 methods in class the class used only "chatting" so if you want to chat someone or your friend and anyone
 * you can to send "message", "image", "voice", "reservation message"
 * there weren't the "ViewModel" because i thought that we don't need a "Dependency injection" in class so that if it used has many boiler code.
 *
 *  Let me introduce "ChatRoomActivity Methods"
 *  1. image method
 *  2. video select method
 *  3. voice select method
 *
 * </Topic>
 */


public class ChatRoomActivity extends AppCompatActivity implements BaseInterface, View.OnKeyListener, View.OnTouchListener, View.OnClickListener {
    private ActivityChatroomBinding activityChatroomBinding;

    private ChatRoomRecyclerAdapter chat_room_recycler_adapter;
    private ArrayList<ChatRoomModel> chat_room_list;

    private DatabaseReference databaseReference;
    private SharedPreferences.Editor editor;

    private String get_other_name,
            get_chat_key,
            get_current_my_uid,
            get_other_uid;

    public String get_phone_number;



    private Long date_time;


    private boolean dataSet = false;
    private InputMethodManager inputMethodManager;


    public String getOtherName() {
        return get_other_name;
    }


    public ChatRoomRecyclerAdapter getChatRoomRecyclerAdapter() {
        return chat_room_recycler_adapter;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatroomBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatroom);
        default_init();
        get_from_chat_recycler_adapter();
        get_message_list();
        on_focus_text_field();
        click_listener();


    }

    @SuppressLint("ClickableViewAccessibility")
    public void click_listener() {
        activityChatroomBinding.chatRoomUploadImage.setOnClickListener(this);
        activityChatroomBinding.chatRoomListRec.setOnTouchListener(this);
    }

    // get from realtime_database
    public void get_message_list() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chatSnapShot : snapshot.child("chat").getChildren()) {
                    // ???????????? ????????? ?????????
                    if (chatSnapShot.hasChild("message")) {
                        chat_room_list.clear();
                        // message ??? ????????? ??????.
                        for (DataSnapshot messageSnapShot : chatSnapShot.child("message").getChildren()) {
                            // ???????????? ????????? ?????? ??? ????????? ????????? ???????????? ?????? ??? ?????? ?????????
                            // ?????????, ?????? ????????? ??? ???, dateTime
                            dataSet = false;
                            if (messageSnapShot.hasChild("msg") && messageSnapShot.hasChild("mineKey")) {
                                final String setListMessage = messageSnapShot.child("msg").getValue(String.class);
                                final String setKey = messageSnapShot.child("mineKey").getValue(String.class);
                                final String setDate = messageSnapShot.child("save_chat_date").getValue(String.class);
                                if (!dataSet) {
                                    dataSet = true;
                                    chat_room_list.add(new ChatRoomModel(setKey, setListMessage, setDate));
                                    chat_room_recycler_adapter.notifyDataSetChanged();
                                    activityChatroomBinding.chatRoomListRec.scrollToPosition(chat_room_list.size() - 1);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("get_message_list (Error) ", String.valueOf(error));
            }
        });
    }

    // if you touch "enter key" hide keyboard
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
            activityChatroomBinding.chatRoomTextField.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }
        return false;

    }


    // focus text field
    public void on_focus_text_field() {
        activityChatroomBinding.chatRoomTextField.setOnFocusChangeListener((View view, boolean b) -> {
            // ???????????? ????????? ????????? ???
            if (b) {
                if (view != null) {
                    activityChatroomBinding.chatRoomListRec.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activityChatroomBinding.chatRoomListRec.smoothScrollToPosition(chat_room_list.size() - 1);
                        }
                    }, 500);
                }
            }
        });
    }

    // initialize
    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        activityChatroomBinding.setChatRoomActivity(this);
        activityChatroomBinding.setLifecycleOwner(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (chat_room_list == null && chat_room_recycler_adapter == null) {
            chat_room_list = new ArrayList<>();
            chat_room_recycler_adapter = new ChatRoomRecyclerAdapter(chat_room_list, getBaseContext());
        }
        SharedPreferences preferences = getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    // get data from (chat recycler adapter, profile activity)
    public void get_from_chat_recycler_adapter() {

        Intent getIntent = getIntent();
        if (getIntent != null) {
            get_other_name = getIntent.getStringExtra("getOtherName");
            get_chat_key = getIntent.getStringExtra("getChatKey");
            get_current_my_uid = getIntent.getStringExtra("getCurrentMyUID");
            get_other_uid = getIntent.getStringExtra("getOtherUID");
            get_phone_number = getIntent.getStringExtra("getPhoneNumber");
            Log.d("getOtherName", get_other_name);
            Log.d("getChatKey", get_chat_key);
            Log.d("getCurrentMyUID", get_current_my_uid);
            Log.d("getOtherUID", get_other_uid);
            Log.d("getPhoneNumber", get_phone_number);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            final int constraint_view_h = activityChatroomBinding.constraintViewId.getMeasuredHeight();
            final int header_view_h = activityChatroomBinding.chatRoomHeaderId.getMeasuredHeight();
            final int edittext_view_h = activityChatroomBinding.chatRoomTextField.getMeasuredHeight();

            // result view is "recycler_view_area"
            final int result = constraint_view_h - header_view_h - edittext_view_h;

            if (result < constraint_view_h && view != null) {
                inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                activityChatroomBinding.chatRoomTextField.clearFocus();
            }
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        // touch upload image
        // create bottomSheetDialog
        if (view.getId() == activityChatroomBinding.chatRoomUploadImage.getId()) {
            ChatRoomBottomSheetDialog chatRoomBottomSheetDialog = new ChatRoomBottomSheetDialog();
            chatRoomBottomSheetDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.ChatRoomActivity_Bottom_Sheet_Dialog);
            chatRoomBottomSheetDialog.show(getSupportFragmentManager(), "ChatRoomBottomSheetDialog");
            Log.d("chatRoomBottomSheetDialog", "");
        }
        // 1. ????????? ??????
        // 2. ????????? ??????
        // 3. ?????? ?????????
        // 4. ????????????
        // 5. ????????????
        // 6. ?????????
    }

    // data binding -> back_pressed
    public void back_pressed(){
        finish();
    }


    // data binding -> send_button
    public void send_button(){
        final String chat_text = activityChatroomBinding.chatRoomTextField.getText().toString();
        // ???????????? ???????????? ????????? database ?????? ?????? ??????.
        if (!chat_text.isEmpty()) {
            date_time = System.currentTimeMillis();

            Date now_date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("??????" + "HH:mm");
            // ?????? ?????? ?????? (?????????)
            String set_date = simpleDateFormat.format(now_date);
            // ?????? ??????
            databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(date_time)).child("msg").setValue(chat_text);
            // msg ??? ??? ??? ??????
            databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(date_time)).child("mineKey").setValue(get_current_my_uid);
            // msg ??? ?????? ??????
            databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(date_time)).child("save_chat_date").setValue(set_date);
            // ?????? ?????? ??????
            databaseReference.child("chat").child(get_chat_key).child("????????????").setValue(get_current_my_uid);
            // ?????? ?????? ??????
            databaseReference.child("chat").child(get_chat_key).child("????????????").setValue(get_other_uid);

            // picture editor ??????????????? ????????? ??????, ??????, etc ????????? ?????? ??? ????????? ???????????? chatRoomActivity ????????? ?????? ????????? ????????? ?????????
            // ?????? ????????? ????????? ????????? ??? intent ?????? null??? ?????? ?????? databasereference??? ?????????.
            // ??? ?????? ??? ????????? uri ??? ????????? ???

            // ????????? ????????? null ????????? if?????? ???????????? ??????
            // ?????? ????????? ????????? if?????? ???????????? ??????.

            editor.putLong("chatDateTime", date_time);
            editor.commit();

            activityChatroomBinding.chatRoomTextField.setText("");
        }
    }

    public String getGet_phone_number() {
        return get_phone_number;
    }

}
