package com.example.firebase_chat_basic.viewModel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class ChatRoomViewModel extends ViewModel{
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    final DatabaseReference databaseReference;
    public MutableLiveData<String> messageLiveData = new MutableLiveData<>();



    public ChatRoomViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
    }


    public void sendMessage(){
        // @TODO 메세지 보내는 로직
    }

}
