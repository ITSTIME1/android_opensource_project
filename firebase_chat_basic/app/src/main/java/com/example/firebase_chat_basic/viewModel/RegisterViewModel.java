package com.example.firebase_chat_basic.viewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebase_chat_basic.view.activity.MainActivity;
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
import java.util.ArrayList;
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

public class RegisterViewModel extends AndroidViewModel {

    // firebase "realTimeDataBase" url
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";

    // firebaseDatabase instance
    private final DatabaseReference databaseReference;
    private final FirebaseAuth firebaseAuth;
    private ArrayList<String> stringArrayList;

    // two-way dataBinding
    public MutableLiveData<String> getRegisterFirstName;
    public MutableLiveData<String> getRegisterSecondName;
    public MutableLiveData<String> getRegisterEmail;
    public MutableLiveData<String> getRegisterPassword;
    public MutableLiveData<String> getProfileImage;
    // mutableLiveData list
    public MutableLiveData<ArrayList<String>> getDataList = new MutableLiveData<>();

    private FirebaseUser firebaseUser;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String currentUserUID;
    String checkFirstName;
    String checkSecondName;
    String checkEmail;
    String checkPassword;
    String checkProfileImage;
    String checkName;

    // registerViewModel constructor
    public RegisterViewModel(Application application) {
        super(application);
        Application context = getApplication();
        preferences = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        firebaseAuth = FirebaseAuth.getInstance();
        stringArrayList = new ArrayList<>();
        getRegisterFirstName = new MutableLiveData<>();
        getRegisterSecondName = new MutableLiveData<>();
        getRegisterEmail = new MutableLiveData<>();
        getRegisterPassword = new MutableLiveData<>();
        getProfileImage = new MutableLiveData<>();
    }



    // firebase realTimebase add data
    public void registerButton(){

        // get data from EditText
        checkFirstName = getRegisterFirstName.getValue();
        checkSecondName = getRegisterSecondName.getValue();
        checkEmail = getRegisterEmail.getValue();
        checkPassword = getRegisterPassword.getValue();
        checkProfileImage = getProfileImage.getValue();
        checkName = checkFirstName + checkSecondName;

        if(checkProfileImage == null) checkProfileImage = "Default";

        // firebase authentication
        firebaseRegister(checkEmail, checkPassword);

    }



    // firebase register
    public void firebaseRegister(String checkEmail, String checkPassword) {
        firebaseAuth.createUserWithEmailAndPassword(checkEmail, checkPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(firebaseUser != null ) {
                        currentUserUID = firebaseUser.getUid();
                        Log.d("currentUserUID", currentUserUID);
                    }
                    System.out.println("성공");

                    String finalCheckProfileImage = checkProfileImage;



                    // 데이터 베이스에 저장.
                    databaseReference.child("users").child(currentUserUID).child("uid").setValue(currentUserUID);
                    databaseReference.child("users").child(currentUserUID).child("name").setValue(checkName);
                    databaseReference.child("users").child(currentUserUID).child("email").setValue(checkEmail);
                    databaseReference.child("users").child(currentUserUID).child("profileImage").setValue(finalCheckProfileImage);



                    // MutableLiveData 를 통해서 값을 받아서 MainActivity 로 보냄
                    stringArrayList.add(currentUserUID);
                    stringArrayList.add(checkName);
                    stringArrayList.add(checkEmail);
                    stringArrayList.add(checkProfileImage);

                    getDataList.setValue(stringArrayList);

                    editor.putString("authenticationUID", currentUserUID);
                    editor.putString("authenticationName", checkName);
                    editor.putString("authenticationEmail", checkEmail);
                    editor.putString("authenticationCheckProfileImage", checkProfileImage);
                    editor.commit();

                    Log.d("authenticationUID", preferences.getString("authenticationUID", ""));


                } else {
                    System.out.println("값이 성공적으로 저장이 되지 않았어요");
                }
            }
        });
    }


}
