package com.example.firebase_chat_basic.viewModel;
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
    public MutableLiveData<String> getRegisterFirstName = new MutableLiveData<>();
    public MutableLiveData<String> getRegisterSecondName = new MutableLiveData<>();
    public MutableLiveData<String> getRegisterEmail = new MutableLiveData<>();
    public MutableLiveData<String> getRegisterPassword = new MutableLiveData<>();
    public MutableLiveData<String> getProfileImage = new MutableLiveData<>();
    // mutableLiveData list
    public MutableLiveData<ArrayList<String>> getDataList = new MutableLiveData<>();


    // firebase uuid, emailPattern, passwordPattern
    public String getFirebaseUserUID;
    private final FirebaseUser firebaseUser;
    public Pattern emailPattern = Patterns.EMAIL_ADDRESS;
    public int passwordPattern = 6;


    // registerViewModel constructor
    public RegisterViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }


    // firebase register
    public void firebaseRegister(String checkEmail, String checkPassword) {
        firebaseAuth.createUserWithEmailAndPassword(checkEmail, checkPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    getFirebaseUserUID = firebaseUser.getUid();
                    System.out.print("파이어베이스 UID" + getFirebaseUserUID);
                } else {
                    System.out.println("값이 성공적으로 저장이 되지 않았어요");
                }
            }
        });
    }



    // firebase realTimebase add data
    public void registerButton(){

        // realtime database 저장용.
        ArrayList<String> stringArrayList = new ArrayList<>();
        // firebase password 길이 확인용.
        ArrayList<String> validationPassword = new ArrayList<>();

        // get data from EditText
        String checkFirstName = getRegisterFirstName.getValue();
        String checkSecondName = getRegisterSecondName.getValue();
        String checkEmail = getRegisterEmail.getValue();
        String checkPassword = getRegisterPassword.getValue();
        String checkProfileImage = getProfileImage.getValue();
        String checkName = checkFirstName + checkSecondName;
        if(checkProfileImage == null) checkProfileImage = "Default";


        validationPassword.add(checkPassword);

        assert checkEmail != null;
        // 1. 이메일 조건에도 부합하고, 패스워드 길이도 부합한다.
        // 2. 이메일, 패스워드가 비어있지 않다.
        // 3. 가입이 완료된다면 배열에 값들을 전부 저장시켜주고
        firebaseRegister(checkEmail, checkPassword);
        if(emailPattern.matcher(checkEmail).matches() && validationPassword.size() > passwordPattern && checkPassword != null) {
            System.out.print("파이어베이스 name : " + checkName);
            System.out.print("파이어베이스 email : " + checkEmail);
            System.out.print("파이어베이스 password : " + checkPassword);
            // firebaseCurrentUserID
            System.out.println("파이어베이스 UUID : " + getFirebaseUserUID);


            // into stringArrayList
            stringArrayList.add(checkName);
            stringArrayList.add(checkEmail);
            stringArrayList.add(checkPassword);
            stringArrayList.add(getFirebaseUserUID);
            stringArrayList.add(checkProfileImage);

            if(checkProfileImage == null) {
                checkProfileImage = "Default";
            }

            // stringArrayList into getDataList
            getDataList.setValue(stringArrayList);


            // temp variable
            String finalCheckProfileImage = checkProfileImage;
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("users").hasChild(checkName)){
                        System.out.println("이미 이 계정은 존재해요");
                    } else {

                        // 1. 리얼타임데이터베이스에 저장 email, name
                        databaseReference.child("users").child(getFirebaseUserUID).child("email").setValue(checkEmail);
                        databaseReference.child("users").child(getFirebaseUserUID).child("name").setValue(checkName);
                        // 2. 프로필 이미지는 처음엔 기본 아무것도 없는 상태로 저장.
                        databaseReference.child("users").child(getFirebaseUserUID).child("profileImage").setValue(finalCheckProfileImage);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.print(error);
                }
            });

        } else {
            System.out.println("이메일 이나 패스워드를 다시 확인이 필요합니다.");
        }

    }
}
