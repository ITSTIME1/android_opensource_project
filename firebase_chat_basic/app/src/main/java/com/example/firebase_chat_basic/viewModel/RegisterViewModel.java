package com.example.firebase_chat_basic.viewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

/**
 * [RegisterViewModel]
 *
 *
 * 1.
 * The class is "ViewModel Class" for RegisterActivity.
 * This includes "RealTimeDatabase", "MutableLiveData", "MutableLivedata<List<String>>".
 *
 * 2.
 * If client is push "Register Button" and then get data from editText.getText() in "activity_register.xml".
 * and then data is insert to "RealTimeDatabase" after going thorough a lot of conditions.
 *
 * 3.
 * If the process all succeeded, In the RegisterActivity, through observer can data observe so then insert the data in the "registerIntent".
 * */

public class RegisterViewModel extends ViewModel{

    // firebase "realTimeDataBase" url
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";

    // firebaseDatabase instance
    private final DatabaseReference databaseReference;

    // Two-way dataBinding
    public MutableLiveData<String> getRegisterName = new MutableLiveData<>();
    public MutableLiveData<String> getRegisterEmail = new MutableLiveData<>();
    public MutableLiveData<String> getRegisterPassword = new MutableLiveData<>();
    public MutableLiveData<String> getProfileImage = new MutableLiveData<>();


    // MutableLiveData list
    public MutableLiveData<ArrayList<String>> getDataList = new MutableLiveData<>();


    // RegisterViewModel constructor
    public RegisterViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);

    }


    // firebase realTimebase add data
    public void registerButton(){

        ArrayList<String> stringArrayList = new ArrayList<>();

        String checkName = getRegisterName.getValue();
        String checkEmail = getRegisterEmail.getValue();
        String checkPassword = getRegisterPassword.getValue();

        // uuid create
        UUID uuid = UUID.randomUUID();

        // image url
        String checkProfile = getProfileImage.getValue();


        // into stringArrayList
        stringArrayList.add(checkName);
        stringArrayList.add(checkEmail);
        stringArrayList.add(checkPassword);
        stringArrayList.add(uuid.toString());

        // stringArrayList into getDataList
        getDataList.setValue(stringArrayList);

        // register logic
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
                        // create randomUUID
                        databaseReference.child("users").child(uuid.toString()).child("email").setValue(checkEmail);
                        databaseReference.child("users").child(uuid.toString()).child("name").setValue(checkName);
                        databaseReference.child("users").child(uuid.toString()).child("password").setValue(checkPassword);

                        // first profile picture default
                        databaseReference.child("users").child(checkName).child("profileImage").setValue("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.print(error);
                }
            });
        }

    }
}
