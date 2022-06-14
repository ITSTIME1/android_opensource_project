package com.example.firebase_chat_basic.viewModel;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebase_chat_basic.view.activity.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

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
    private FirebaseAuth firebaseAuth;

    // two-way dataBinding
    public MutableLiveData<String> getRegisterFirstName;
    public MutableLiveData<String> getRegisterSecondName;
    public MutableLiveData<String> getRegisterEmail;
    public MutableLiveData<String> getRegisterPassword;
    public MutableLiveData<String> getProfileImage;
    // mutableLiveData list
    public MutableLiveData<ArrayList<String>> getDataList = new MutableLiveData<>();



    // firebase uuid, emailPattern, passwordPattern
    public String getFirebaseUserUID;
    private final FirebaseUser firebaseUser;
    public Pattern emailPattern = Patterns.EMAIL_ADDRESS;
    public int passwordPattern = 6;


    ArrayList<String> stringArrayList;
    String checkFirstName;
    String checkSecondName;
    String checkEmail;
    String checkPassword;
    String checkProfileImage;
    String checkName;

    // registerViewModel constructor
    public RegisterViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        stringArrayList = new ArrayList<>();
        getRegisterFirstName = new MutableLiveData<>();
        getRegisterSecondName = new MutableLiveData<>();
        getRegisterEmail = new MutableLiveData<>();
        getRegisterPassword = new MutableLiveData<>();
        getProfileImage = new MutableLiveData<>();

    }



    // firebase realTimebase add data
    public void registerButton(){
        // firebase password 길이 확인용.
//        ArrayList<String> validationPassword = new ArrayList<>();

        // get data from EditText
        checkFirstName = getRegisterFirstName.getValue();
        checkSecondName = getRegisterSecondName.getValue();
        checkEmail = getRegisterEmail.getValue();
        checkPassword = getRegisterPassword.getValue();
        checkProfileImage = getProfileImage.getValue();
        checkName = checkFirstName + checkSecondName;

        if(checkProfileImage == null) checkProfileImage = "Default";
//        validationPassword.add(checkPassword);

        // firebase createUser
        firebaseRegister(checkEmail, checkPassword);
    }



    // firebase register
    public void firebaseRegister(String checkEmail, String checkPassword) {
        firebaseAuth.createUserWithEmailAndPassword(checkEmail, checkPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    System.out.println("성공");


                    // 현재 내가 가입해서 얻은 uid
                    final String currentUserUID = firebaseUser.getUid();
                    // temp variable
                    String finalCheckProfileImage = checkProfileImage;


                    stringArrayList.add(currentUserUID);
                    stringArrayList.add(checkName);
                    stringArrayList.add(checkEmail);
                    stringArrayList.add(finalCheckProfileImage);

                    getDataList.setValue(stringArrayList);
                    Log.d("getDataList", "데이터가 성공적으로 getDataList 에 저장되었습니다.");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("users").hasChild(currentUserUID)){
                                Log.d("realtimeDataBase", "현재 users 하위에 같은 UID 가 존재 합니다.");
                            } else {

                                // 1. 리얼타임데이터베이스에 저장 email, name
                                databaseReference.child("users").child(currentUserUID).child("uid").setValue(currentUserUID);
                                databaseReference.child("users").child(currentUserUID).child("name").setValue(checkName);
                                databaseReference.child("users").child(currentUserUID).child("email").setValue(checkEmail);
                                // 2. 프로필 이미지는 처음엔 기본 아무것도 없는 상태로 저장.
                                databaseReference.child("users").child(currentUserUID).child("profileImage").setValue(finalCheckProfileImage);
                                Log.d("realtimeDataBase", "회원가입 내용을 성공적으로 리얼타임 데이터베이스에 저장 하였습니다.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.print(error);
                        }
                    });


                } else {
                    System.out.println("값이 성공적으로 저장이 되지 않았어요");
                }
            }
        });
    }


}
