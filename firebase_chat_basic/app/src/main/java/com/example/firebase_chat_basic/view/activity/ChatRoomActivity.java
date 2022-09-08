package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ChatRoomRecyclerAdapter;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.ActivityChatroomBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.example.firebase_chat_basic.view.fragment.ChatRoomBottomSheetDialog;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
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
 *
 *     "ChatRoomActivity" has 6 methods in class the class used only "chatting" so if you want to chat someone or your friend and anyone
 *      you can to send "message", "image", "voice", "reservation message"
 *      there weren't the "ViewModel" because i thought that we don't need a "Dependency injection" in class so that if it used has many boiler code
 *
 *      Let me introduce "ChatRoomActivity Methods"
 *      1. image method
 *      2. video select method
 *      3. voice select method
 *
 * </Topic>
 */

// @TODO 입장할때 한번 꺼지는데 왜그러ㅏ지

public class ChatRoomActivity extends AppCompatActivity implements BaseInterface, View.OnKeyListener, View.OnTouchListener, View.OnClickListener {
    private ActivityChatroomBinding activityChatroomBinding;
    private ChatRoomRecyclerAdapter chat_room_recycler_adapter;
    private ArrayList<ChatRoomModel> chat_room_list;
    private DatabaseReference databaseReference;
    private InputMethodManager inputMethodManager;

    private String get_other_name, get_chat_key, get_current_my_uid, get_other_uid, imageURI;
    public String get_phone_number;
    private int messageViewType;
    private int maxMessageKey;

    private boolean dataSet = false;

    // date
    private final Date now_date = new Date();
    private final Handler mHandler = new Handler();
    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
    private final String set_date = simpleDateFormat.format(now_date);
    private final String current_date = currentDateFormat.format(now_date);
    private int firstPositionX;
    public FragmentManager fragmentManager;

    // custom video instance
    private ExoPlayer exoPlayer;
    private DataSource.Factory factory;


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
        refresh_layout();

    }


    public void refresh_layout(){
        activityChatroomBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // @TODO 리프레쉬 할거 있으면 추가하자
                Log.d("refresh 1", "");
                activityChatroomBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });

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
                        ArrayList<Integer> messageKeyList = new ArrayList<>();
                        for(DataSnapshot dataSnapshot : snapshot.child("chat").getChildren()) {
                            String key_check = dataSnapshot.getKey();
                            Log.d("dataSnapshot", String.valueOf(key_check));
                            // get_chat_key 값이랑 동일하다면
                            if (key_check != null && key_check.equals(get_chat_key)) {
                                for (DataSnapshot messageKeySnapShot : dataSnapshot.child("message").getChildren()) {
                                    messageKeyList.add(Integer.valueOf(Objects.requireNonNull(messageKeySnapShot.getKey())));
                                    // messageKeyValue 확인.
                                    Log.d("messageKeySnapshot", String.valueOf(messageKeySnapShot.getKey()));
                                }
                            }

                        }
                        // 최신값을 전역변수에 담는다.
                        maxMessageKey = Collections.max(messageKeyList);
                    Log.d("maxMessageKey", String.valueOf(maxMessageKey));
                } else {
                    Log.d("아직 chat이 없어요", "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // click listener
    @SuppressLint("ClickableViewAccessibility")
    public void click_listener() {
        activityChatroomBinding.chatRoomFunctionsSheet.setOnClickListener(this);
        activityChatroomBinding.chatRoomListRec.setOnTouchListener(this);
        activityChatroomBinding.constraintViewId.setOnTouchListener(this);
    }

    // get from realtime_database
    public void get_message_list() {
        if(chat_room_list.size() == 0) {
            activityChatroomBinding.chatRoomProgressText.setVisibility(View.VISIBLE);
        }
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
                            // 메세지 가지고 올때 기본적인 값들은 전부 가지고옴
                            final String setKey = messageSnapShot.child("mineKey").getValue(String.class);
                            final String setDate = messageSnapShot.child("save_chat_date").getValue(String.class);
                            final String current_Date = messageSnapShot.child("currentDate").getValue(String.class);
                            if(messageSnapShot.child("viewType").getValue(Integer.class) == null) {
                                continue;
                            } else {
                                messageViewType = messageSnapShot.child("viewType").getValue(Integer.class);
                            }

                            // image, video, message
                            dataSet = false;
                            if (messageSnapShot.hasChild("msg") && messageSnapShot.hasChild("mineKey") && messageViewType == Constants.chatMessageViewType) {
                                final String setListMessage = messageSnapShot.child("msg").getValue(String.class);
                                Log.d("viewType", String.valueOf(messageViewType));
                                if (!dataSet) {
                                    dataSet = true;
                                    // 이미지가 없으면 채팅만
                                    chat_room_list.add(new ChatRoomModel(setKey, setListMessage, setDate, current_Date, messageViewType));
                                }
                            } else if(messageSnapShot.hasChild("imageURI") && messageSnapShot.hasChild("mineKey") && messageViewType == Constants.chatImageViewType) {

                                // image url
                                final String imageURI = messageSnapShot.child("imageURI").getValue(String.class);
                                if (!dataSet) {
                                    dataSet = true;
                                    chat_room_list.add(new ChatRoomModel(setKey, setDate, current_Date, messageViewType, imageURI));
                                }
                            } else if(messageSnapShot.hasChild("videoURL") && messageSnapShot.hasChild("mineKey") && messageViewType == Constants.chatVideoViewType) {
                                final String videoURL = messageSnapShot.child("videoURL").getValue(String.class);
                                if (!dataSet) {
                                    dataSet = true;
                                    chat_room_list.add(new ChatRoomModel(setKey, setDate, current_Date, messageViewType, videoURL));
                                    Log.d("리스트 하나 추가", "");
                                }
                            }
                        }
                    }
                }

                if(chat_room_list.size() != 0) {
                    activityChatroomBinding.chatRoomProgressText.setVisibility(View.GONE);
                }

                // previous chat index compare to last chat index
                int chat_count = chat_room_list.size();
                Log.d("previous count", String.valueOf(maxMessageKey));
                Log.d("chat_count", String.valueOf(chat_count));
                if(maxMessageKey < chat_count ) {
                    activityChatroomBinding.chatRoomListRec.scrollToPosition(chat_room_list.size() - 1);
                } else if (maxMessageKey == chat_count) {
                    return;
                } else {
                    return;
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
        fragmentManager = getSupportFragmentManager();

        exoPlayer = new ExoPlayer.Builder(this).build();
        factory = new DefaultDataSource.Factory(this);

        // video instance
        if (chat_room_list == null && chat_room_recycler_adapter == null) {
            chat_room_list = new ArrayList<>();
            chat_room_recycler_adapter = new ChatRoomRecyclerAdapter(chat_room_list, getApplicationContext(), fragmentManager, exoPlayer, factory);
            chat_room_recycler_adapter.setHasStableIds(true);
        }
    }

    // @TODO 다시 한번 로직 따라가면서 짜보자.


    // get data from (chat recycler adapter, profile activity)
    public void get_from_chat_recycler_adapter() {
        Intent getIntent = getIntent();
        if (getIntent != null) {
            get_other_name = getIntent.getStringExtra("getOtherName");
            get_chat_key = getIntent.getStringExtra("getChatKey");
            get_current_my_uid = getIntent.getStringExtra("getCurrentMyUID");
            get_other_uid = getIntent.getStringExtra("getOtherUID");
            get_phone_number = getIntent.getStringExtra("getPhoneNumber");
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            firstPositionX = (int) motionEvent.getX();
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
            Log.d("움직임 감지 down", "");
        }

        // 움직일 때
        int secondPosition;
        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            secondPosition = (int) motionEvent.getX();
            if ( secondPosition - firstPositionX > 600 ) {
                finish();
                secondPosition = 0;
            }

        }

        if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            firstPositionX = 0;
            secondPosition = 0;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        // touch upload image
        // create bottomSheetDialog
        if (view.getId() == activityChatroomBinding.chatRoomFunctionsSheet.getId()) {
            // bundle 을 통해서 get_chat_key 값 전달.
            // 잘 들어오고
            Bundle bottomFragmentBundle = new Bundle();
            bottomFragmentBundle.putString("get_chat_key", get_chat_key);
            bottomFragmentBundle.putString("get_other_uid", get_other_uid);
            bottomFragmentBundle.putString("get_current_my_uid", get_current_my_uid);
            ChatRoomBottomSheetDialog chatRoomBottomSheetDialog = new ChatRoomBottomSheetDialog();
            chatRoomBottomSheetDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.ChatRoomActivityBottomSheetDialog);
            chatRoomBottomSheetDialog.show(getSupportFragmentManager(), "ChatRoomBottomSheetDialog");
            chatRoomBottomSheetDialog.setArguments(bottomFragmentBundle);
            Log.d("bottomFragment", String.valueOf(bottomFragmentBundle));
            Log.d("chatRoomBottomSheetDialog", "");
        }
        // 3. 예약 메세지
        // 4. 음성인식
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
            // 비동기라 위에있는게 늦어지기 때문에 maxMessageKey 값이 처음엔 0값으로 되어있다가 그 메세지 키값을 받으면 messageKey 값으로 변하고 또 뒤늦게오고
            check_message_key();
            Log.d("비어있는지 확인", "");
            // 이게 굉장히 늦게오네
            Log.d("maxMessaheKey", String.valueOf(maxMessageKey));
            // 만약에 채팅을 처음 보낸다면
            // 채팅을 처음 보내는게 아닌 만약 채팅을 보낸적이 있다면

            // 채팅을 보낼때 그 채팅방의 날짜가 이전 날짜보다 작다면 이전 날짜의 1을 더해서 출력한다.
            // ex)오늘 데이터 값이 생성되고 데이터를 넣을려고 하는데 2022073110 있는데 이전의 생성된 값의 가장 큰 값이 2022073112
            // 왜 어쩔떄는 maxMessageKey 값이 같아서 넣어지지
            // 딜레이를 주자
            mHandler.postDelayed(new Runnable()  {
                public void run() {
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("msg").setValue(chat_text);
                    // getChatRoomViewType
                    databaseReference.child("chat").child(get_chat_key).child("message").child(String.valueOf(maxMessageKey+1)).child("viewType").setValue(Constants.chatMessageViewType);
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
            }, 100); // 0.5초후

            activityChatroomBinding.chatRoomTextField.setText("");
            // 채팅을 보낸다음에 maxMessaeKey 값을 초기화 해준다.
        }
    }


    // get phone number
    public String getGet_phone_number() {
        return get_phone_number;
    }

    // get other user name
    public String getOtherName() {
        return get_other_name;
    }

    // get recycler adapter
    public ChatRoomRecyclerAdapter getChatRoomRecyclerAdapter() {
        return chat_room_recycler_adapter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityChatroomBinding = null;
        // exoplayer release
    }

}
