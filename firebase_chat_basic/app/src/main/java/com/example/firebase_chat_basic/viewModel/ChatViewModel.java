package com.example.firebase_chat_basic.viewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.Interface.FirebaseInterface;
import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.FragmentChatBinding;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * [ChatViewModel]
 *
 * <Topic>
 *
 *     This is "ChatViewModel"
 *     it's going to check user information from realtime database and it check if it has chatKey.
 *     if chatRoom is nothing
 *     it don't display user information list
 *
 *     I am helped realtime database chat logic by "Learnoset - Learn Coding Online"
 *     Thank you share useful video :)
 *
 * </Topic>
 */

public class ChatViewModel extends AndroidViewModel implements FirebaseInterface, BaseInterface {
    public ArrayList<ChatListModel> chat_array_list;
    public MutableLiveData<ArrayList<ChatListModel>> arrayListMutableLiveData;
    private ChatRecyclerAdapter chat_recycler_adapter;

    private DatabaseReference databaseReference;
    private SharedPreferences preferences, authPreference;

    private final String firebase_my_uid;
    private boolean listSet = false;


    private final Date now_date = new Date();
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat new_date_format = new SimpleDateFormat("yyyy-MM-dd");
    private final String chat_date = new_date_format.format(now_date);



    public ChatViewModel(Application application) {
        super(application);
        default_init();
        firebase_my_uid = authPreference.getString("authentication_uid", "");
        if (chat_array_list == null && chat_recycler_adapter == null && arrayListMutableLiveData == null) {
            chat_array_list = new ArrayList<>();
            chat_recycler_adapter = new ChatRecyclerAdapter(this);
            chat_recycler_adapter.setHasStableIds(true);
            arrayListMutableLiveData = new MutableLiveData<>();
        }
        // when chat viewModel create constructor, execute the method.
        get_user_database();
    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        Application context = getApplication();
        authPreference = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        preferences = context.getSharedPreferences("chatPref", Activity.MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
    }

    @Override
    public void get_user_database() {
        FirebaseInterface.super.get_user_database();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ChatViewModel", "======== onDataChange ========");
                for (DataSnapshot userSnapshot : snapshot.child("users").getChildren()) {
                    listSet = false;
                    if (!Objects.requireNonNull(userSnapshot.child("uid").getValue(String.class)).equals(firebase_my_uid)) {
                        final String getPhoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);

                        Log.d("ChatViewModel", "======== Date 생성 ========");

                        String chatKey = firebase_my_uid + userSnapshot.child("uid").getValue(String.class);
                        Log.d("chatKey ", chatKey);
                        Log.d("ChatViewModel", "======== 채팅방이 없을시 가져가는 [ChatKey] 생성 ========");

                        final String getOtherKey = userSnapshot.child("uid").getValue(String.class);
                        final String getOtherName = userSnapshot.child("name").getValue(String.class);
                        Log.d("getOtherName ", String.valueOf(getOtherName));

                        chat_array_list.clear();

                        // chat logic
                        get_chat_database(getOtherName, getOtherKey, getPhoneNumber);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void get_chat_database(String get_other_name, String get_other_key, String get_phone_number) {
        FirebaseInterface.super.get_chat_database(get_other_name, get_other_key, get_phone_number);
        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("ChatViewModel", "======== 채팅 읽어오기 ========");
                int chat_room_count = (int) snapshot.getChildrenCount();
                // 채팅이 있으면
                if (chat_room_count > 0) {
                    Log.d("ChatViewModel", "======== 채팅방 검사 시작 ========");
                    // 채팅방 내역을 훑고
                    for (DataSnapshot chatDataSnapShot : snapshot.getChildren()) {
                        // 그 해당 채팅방의 보낸 사람과 받은 사람의 키 값을 확인할거임.
                        if (chatDataSnapShot.hasChild("보낸사람") && chatDataSnapShot.hasChild("받은사람")) {
                            final String receiver = chatDataSnapShot.child("받은사람").getValue(String.class);
                            final String sender = chatDataSnapShot.child("보낸사람").getValue(String.class);
                            // 보낸 사람과 받은 사람의 키 값들을 확인하고
                            if ((Objects.equals(receiver, get_other_key) && Objects.requireNonNull(sender).equals(firebase_my_uid)) ||
                                    (Objects.requireNonNull(receiver).equals(firebase_my_uid) && Objects.requireNonNull(sender).equals(get_other_key))) {
                                // 해당 키 값을 저장해주고
                                Log.d("ChatViewModel", "======== 채팅방 검사 완료 ========");

                                final String get_chat_key = chatDataSnapShot.getKey();
                                String get_content = "메세지가 없습니다.";
                                int get_message_count = 0;

                                // 여기 까지 클릭해서 로직이 돌게 된다면 현재 chat_count는 0이에요
                                // 근데 만약 상대방이 나에게 채팅을 보낸다면 거기서 로직이 다시 돌게 됩니다.
                                // 로직을 다시 돌게 되면 chat_count 가 다시 변경 되게 된다.


                                // @TODO 이렇게 chat count 값을 조절하자.
                                // 내가 확인한 채팅의 값은 개수로 치지 않는다
                                // 즉 새로운 메세지의 값만 개수로 친다는거
                                // 그럼 기존의 잇던 chatting 값에다가 boolean 값을 넣어주고 만약 layout을 클릭했을 때 boolean 값을 true 로 설정해준다.
                                // 그럼 마지막까지 왔던 메세지는 true로 설정되어 있고
                                // 새로 들어온 값은 false 이기 때문에 그 false 의 조건을 가지고 있는 메세지 값만 다시 count 해서 for문 로직을 짠다.
                                for (DataSnapshot messageSnapShot : chatDataSnapShot.child("message").getChildren()) {

                                    if(messageSnapShot.hasChild("msg")) {
                                        long before_message_key = 0;
                                        final long recent_message_key = preferences.getLong("chatDateTime", 0);
                                        long child_message_key = Long.parseLong(Objects.requireNonNull(messageSnapShot.getKey()));

                                        if (recent_message_key > child_message_key) {
                                            before_message_key = Long.parseLong(messageSnapShot.getKey());
                                        }
                                        if (recent_message_key > before_message_key) {
                                            get_message_count++;
                                        }

                                        get_content = messageSnapShot.child("msg").getValue(String.class);
                                    } else {
                                        get_content = "사진을 보냈습니다";
                                    }

                                }
                                // list create
                                if (!listSet) {
                                    listSet = true;
                                    chat_array_list.add(new ChatListModel(
                                            get_other_name,
                                            chat_date,
                                            get_content,
                                            String.valueOf(get_message_count),
                                            get_chat_key,
                                            firebase_my_uid,
                                            get_other_key,
                                            get_phone_number));
                                    arrayListMutableLiveData.setValue(chat_array_list);
                                    chat_recycler_adapter.notifyDataSetChanged();
                                    Log.d("ChatViewModel", "======== 채팅방 값을 넘겨준 채로 리스트 생성 완료 ========");
                                }

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ChatViewModel Load DataBase Error", String.valueOf(error));
            }
        });
    }

    @Override
    public String get_user_name(int pos) {
        return chat_array_list.get(pos).getChatName();
    }

    @Override
    public String get_my_uid(int pos) {
        return chat_array_list.get(pos).getChatMyUID();
    }

    @Override
    public String get_profile_image(int pos) {
        return FirebaseInterface.super.get_profile_image(pos);
    }

    @Override
    public String get_content(int pos) {
        return chat_array_list.get(pos).getChatContent();
    }

    @Override
    public String get_count(int pos) {
        return chat_array_list.get(pos).getChatCount();
    }

    @Override
    public String get_date(int pos) {
        return chat_array_list.get(pos).getChatDate();
    }

    @Override
    public String get_chat_key(int pos) {
        return chat_array_list.get(pos).getChatKey();
    }

    @Override
    public String get_other_uid(int pos) {
        return chat_array_list.get(pos).getChatOtherUID();
    }

    public String get_phone_number(int pos) {
        return chat_array_list.get(pos).getChatPhoneNumber();
    }

    public ArrayList<ChatListModel> get_chat_list() {
        return chat_array_list;

    }
    public ChatRecyclerAdapter getChat_recycler_adapter() {
        return chat_recycler_adapter;
    }


}
