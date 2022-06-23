package com.example.firebase_chat_basic.view.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityRegisterBinding;
import com.example.firebase_chat_basic.view.fragment.ChatFragment;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.example.firebase_chat_basic.viewModel.RegisterViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * [RegisterActivity]
 *
 * 1.
 * The class used to "DataBinding" and "MVVM"
 * If client want to "register" then move here.
 *
 * 2.
 * and then receives data written by the client and get data from "RegisterViewModel"
 * Finally, it is handled here.
 */
public class RegisterActivity extends AppCompatActivity implements BaseInterface {
    private ActivityRegisterBinding activityRegisterBinding;
    private RegisterViewModel registerViewModel;
    private FirebaseUser firebaseUser;
    private SharedPreferences preferences;
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Intent registerIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        // 실행 중에 파이어베이스 유저 정보가 없다면
        check_firebase_user();
        defaultInit();
        observerIntent();
    }

    public void check_firebase_user(){
        preferences = getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // 유저가 없으면 회원가입 해야 되니까 아무 실행 안함.
        // 유저가 있으면 유저 정보가 나의 정보가 맞나 확인하고 정보를 넘김.
        if (firebaseUser == null) {
            Log.d("Authentication User is Not", "");
        } else if(firebaseUser.getUid().equals(preferences.getString("authenticationUID", ""))) {

            Log.d("User Checking", String.valueOf(firebaseUser.getUid().equals(preferences.getString("authenticationUID", ""))));
            registerIntent = new Intent(this, MainActivity.class);
            // preference 에서 값을 가지고 온 뒤
            final String authentication_UID = preferences.getString("authenticationUID", "");
            final String authentication_Name = preferences.getString("authenticationName", "");
            final String authentication_Email = preferences.getString("authenticationEmail", "");
            final String authentication_ProfileImage = preferences.getString("authenticationCheckProfileImage", "");

            registerIntent.putExtra("clientUID", authentication_UID);
            registerIntent.putExtra("clientName", authentication_Name);
            registerIntent.putExtra("clientEmail", authentication_Email);
            registerIntent.putExtra("clientProfileImage", authentication_ProfileImage);

            Log.d("Authentication User is exist", String.valueOf(authentication_UID));

            startActivity(registerIntent);
            Toast.makeText(this, "로그인 완료" + authentication_UID, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.d("유저정보도 없고, 값도 틀립니다.", "");
        }
    }

    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);
    }

    @Override
    public void observerIntent() {
        BaseInterface.super.observerIntent();
        registerViewModel.getDataList.observe(this, registerData -> {
            registerIntent = new Intent(this, MainActivity.class);
            if(registerData.get(0) != null && registerData.get(1) != null && registerData.get(2) != null) {
                registerIntent.putExtra("clientUID", registerData.get(0));
                registerIntent.putExtra("clientName", registerData.get(1));
                registerIntent.putExtra("clientEmail", registerData.get(2));
                registerIntent.putExtra("clientProfileImage", registerData.get(3));
                Log.d("registerIntent", "success");
            } else {
                Log.d("registerData", "register Data null");
            }
            startActivity(registerIntent);
            finish();
        });
    }
}
