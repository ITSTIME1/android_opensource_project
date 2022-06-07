package com.example.firebase_chat_basic.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.example.firebase_chat_basic.databinding.ActivityRegisterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterViewModel extends ViewModel {

    // firebase "realTimeDataBase" url
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";

    private ActivityRegisterBinding activityRegisterBinding;
    private final DatabaseReference databaseReference;
    private String getRegisterName;
    private String getRegisterEmail;
    private String getRegisterPassword;

    public RegisterViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
    }

    public void registerButton(){

        if (activityRegisterBinding != null) {
            getRegisterName = activityRegisterBinding.registerName.getText().toString();
            getRegisterEmail = activityRegisterBinding.registerEmail.getText().toString();
            getRegisterPassword = activityRegisterBinding.registerPassword.getText().toString();
        }

        if (getRegisterName.isEmpty() || getRegisterEmail.isEmpty() || getRegisterPassword.isEmpty()) {
            System.out.println("비었짜나");
        } else {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("HongTaeSun").hasChild(getRegisterName)) {
                        System.out.println("이미 HongTaeSun 이 이미 존재함");
                    } else {
                        databaseReference.child("HongTaeSun").child(getRegisterName).setValue(getRegisterName);
                        databaseReference.child("HongTaeSun").child(getRegisterEmail).setValue(getRegisterEmail);
                        databaseReference.child("HongTaeSun").child(getRegisterPassword).setValue(getRegisterPassword);
                        System.out.println("회원가입 성공");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

}
