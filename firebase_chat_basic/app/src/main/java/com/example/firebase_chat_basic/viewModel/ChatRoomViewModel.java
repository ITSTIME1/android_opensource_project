package com.example.firebase_chat_basic.viewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ChatRoomViewModel {
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    private final DatabaseReference databaseReference;
    public String getNameToChatFragment;


    public ChatRoomViewModel(String dName) {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        getNameToChatFragment = dName;
    }


    public void sendMessage(){
        // @TODO 메세지 보내는 로직
        //
    }

}
