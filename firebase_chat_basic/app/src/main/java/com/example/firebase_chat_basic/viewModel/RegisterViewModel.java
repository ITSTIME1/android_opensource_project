package com.example.firebase_chat_basic.viewModel;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.firebase_chat_basic.databinding.ActivityRegisterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

public class RegisterViewModel extends ViewModel{

    // firebase "realTimeDataBase" url
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";

    // firebaseDatabase
    private final DatabaseReference databaseReference;

    // Two-way dataBinding
    public MutableLiveData<String> getRegisterName = new MutableLiveData<>();
    public MutableLiveData<String> getRegisterEmail = new MutableLiveData<>();
    public MutableLiveData<String> getRegisterPassword = new MutableLiveData<>();



    // RegisterViewModel constructor
    public RegisterViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);

    }


    // firebase realTimebase add data
    public void registerButton(){
        String checkName = getRegisterName.getValue();
        String checkEmail = getRegisterEmail.getValue();
        String checkPassword = getRegisterPassword.getValue();

        if(checkName == null || checkEmail == null || checkPassword == null) {
            System.out.println("값이 없어");
        } else {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("users").hasChild(checkName)){
                        System.out.println("이미 이 계정은 존재해요");
                    } else {

                        // dataBase insert userdata
                        databaseReference.child("users").child("clientName").setValue(checkName);
                        databaseReference.child("users").child("clientEmail").setValue(checkEmail);
                        databaseReference.child("users").child("clientPassword").setValue(checkPassword);

                        System.out.println(checkName);
                        System.out.println(checkEmail);
                        System.out.println(checkPassword);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

}
