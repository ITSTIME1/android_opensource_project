package com.example.firebase_chat_basic.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.example.firebase_chat_basic.view.fragment.ChatRoomBottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;


/**
 * [ChatRoomActivity Introduce]
 *
 * <Topic>
 * "ChatRoomActivity" has 6 methods in class the class used only "chatting" so if you want to chat someone or your friend and anyone
 * you can to send "message", "image", "voice", "reservation message"
 * there weren't the "ViewModel" because i thought that we don't need a "Dependency injection" in class so that if it used has many boiler code.
 * <p>
 * Let me introduce "ChatRoomActivity Methods"
 * 1. image method
 * 2. video select method
 * 3. voice select method
 *
 * </Topic>
 */


public class ChatRoomActivity extends AppCompatActivity implements BaseInterface, View.OnKeyListener, View.OnTouchListener, View.OnClickListener {
    private ActivityChatroomBinding activityChatroomBinding;
    private ChatRoomRecyclerAdapter chat_room_recycler_adapter;
    private ArrayList<ChatRoomModel> chat_room_list;
    private DatabaseReference databaseReference;
    private String get_other_name, get_chat_key, get_current_my_uid, get_other_uid;
    public String get_phone_number;

    private boolean dataSet = false;
    private InputMethodManager inputMethodManager;

    private int maxMessageKey;


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

        // 먼저 chat 루트에 있는 key 값들을 나의 방에 맞는 데이터를 가지고 온다음에
        // 그 데이터 중에서 가장 최신값을 따른 변수에 담고
        // 그 변수를 전역변수로 설정한 다음
        // 전역변수를 send_button을 눌렀을 때 조건을 걸어 확인을 한 뒤
        // 조건에 맞게 데이터 입력.

        // 만약 채팅 루트가 존재 하지 않는다면
        // 만약 채팅 루트가 존재 한다면

        default_init();
        get_from_chat_recycler_adapter();
        get_message_list();
        on_focus_text_field();
        click_listener();
    }

    public void check_message_key() {
        Log.d("체킹시작", "");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // chat 이라는 루트를 가지고 있다면
                // 1. chat 루트의 키 값을 검사하고
                // 만약 키 값이 동일하다면
                // 그 키 값의 메세지 키 값들을 가지고 온다.
                // 그 키 값들 중 가장 큰 키 값을 전역변수로 반환한다.
                if(snapshot.hasChild("chat")) {
                    for(DataSnapshot dataSnapshot : snapshot.child("chat").getChildren()) {

                        String key_check = dataSnapshot.getKey();
                        Log.d("dataSnapshot", String.valueOf(key_check));
                        // get_chat_key 값이랑 동일하다면
                        if(key_check.equals(get_chat_key)) {
                            ArrayList<Integer> messageKeyMaxList = new ArrayList<>();
                            for(DataSnapshot messageKeySnapShot : dataSnapshot.child("message").getChildren()) {
                                messageKeyMaxList.add(Integer.valueOf(Objects.requireNonNull(messageKeySnapShot.getKey())));
                                // messageKeyValue 확인.
                                Log.d("messageKeySnapshot", String.valueOf(messageKeySnapShot.getKey()));
                            }
                            // for문이 다 끝나고 리스트에 다 추가 되었으면 maxMessageKey 값의 최대값을 전역변수에 넘겨준다.
                            maxMessageKey = Collections.max(messageKeyMaxList);
                            Log.d("maxMessageKey", String.valueOf(maxMessageKey));
                        }
                    }
                } else {
                    Log.d("아직 chat이 없어요", "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                    // 메세지를 가지고 있다면
                    if (chatSnapShot.hasChild("message")) {
                        chat_room_list.clear();
                        // message 를 가지고 온다.
                        for (DataSnapshot messageSnapShot : chatSnapShot.child("message").getChildren()) {
                            // 메세지를 가지고 와서 그 메세지 값들이 메세지와 나의 키 값이 있다면
                            // 메시지, 보낸 사람의 키 값, dateTime
                            dataSet = false;
                            if (messageSnapShot.hasChild("msg") && messageSnapShot.hasChild("mineKey")) {
                                final String setListMessage = messageSnapShot.child("msg").getValue(String.class);
                                final String setKey = messageSnapShot.child("mineKey").getValue(String.class);
                                final String setDate = messageSnapShot.child("save_chat_date").getValue(String.class);
                                final String current_Date = messageSnapShot.child("currentDate").getValue(String.class);

                                if (!dataSet) {
                                    dataSet = true;
                                    chat_room_list.add(new ChatRoomModel(setKey, setListMessage, setDate, current_Date));
                                    chat_room_recycler_adapter.notifyDataSetChanged();
                                    Log.d("chat_room_list 처음 값 ", String.valueOf(chat_room_list.get(0).getChat_message()));
                                    if (!chat_room_list.isEmpty()) {
                                        activityChatroomBinding.chatRoomListRec.scrollToPosition(chat_room_list.size() - 1);
                                    }
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
            // 포커스가 활성화 되었을 때
            if (b) {
                if (view != null) {
                    activityChatroomBinding.chatRoomListRec.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!chat_room_list.isEmpty()) {
                                activityChatroomBinding.chatRoomListRec.smoothScrollToPosition(chat_room_list.size() - 1);
                            }
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
//            Log.d("getOtherName", get_other_name);
//            Log.d("getChatKey", get_chat_key);
//            Log.d("getCurrentMyUID", get_current_my_uid);
//            Log.d("getOtherUID", get_other_uid);
//            Log.d("getPhoneNumber", get_phone_number);
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
            chatRoomBottomSheetDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
            chatRoomBottomSheetDialog.show(getSupportFragmentManager(), "ChatRoomBottomSheetDialog");
            Log.d("chatRoomBottomSheetDialog", "");
        }
        // 1. 이미지 접근
        // 2. 동영상 접근
        // 3. 예약 메세지
        // 4. 음성인식
        // 5. 전화하기
        // 6. 카메라
    }

    // data binding -> back_pressed
    public void back_pressed() {
        finish();
    }


    // data binding -> send_button
    public void send_button() {
        final String chat_text = activityChatroomBinding.chatRoomTextField.getText().toString();
        // 텍스트가 비어있지 않다면 database 쓰기 로직 실행.
        if (!chat_text.isEmpty()) {
            check_message_key();
            Log.d("비어있는지 확인", "");
            // 만약에 채팅을 처음 보낸다면
            // 채팅을 처음 보내는게 아닌 만약 채팅을 보낸적이 있다면

            Date now_date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("오후" + "HH:mm");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat todayDateFormat = new SimpleDateFormat("yyyyMMddss");

            // 날짜 포멧 지정 (저장용)
            // 로컬 시계로 주고있네 에뮬레이터 상 시간이라서 깜짝놀랬네
            // 정상적으로 작동하는거 맞음.
            String set_date = simpleDateFormat.format(now_date);
            Log.d("set_date 확인", set_date);
            String current_date = currentDateFormat.format(now_date);
            int date_time = Integer.parseInt(todayDateFormat.format(now_date));
            // 채팅을 보낼때 그 채팅방의 날짜가 이전 날짜보다 작다면 이전 날짜의 1을 더해서 출력한다.
            // ex)오늘 데이터 값이 생성되고 데이터를 넣을려고 하는데 2022073110 있는데 이전의 생성된 값의 가장 큰 값이 2022073112
            // 왜 어쩔떄는 maxmessageKEy 값이 같아서 넣어지지
            // 딜레이를 주자
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("msg").setValue(chat_text);
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
                }
            }, 300);// 0.6초 정도 딜레이를 준 후 시작

            activityChatroomBinding.chatRoomTextField.setText("");
        }
    }

    public String getGet_phone_number() {
        return get_phone_number;
    }

}
