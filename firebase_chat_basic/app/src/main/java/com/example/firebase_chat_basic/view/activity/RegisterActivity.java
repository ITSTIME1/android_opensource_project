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

    // check current user
    public void check_firebase_user(){
        SharedPreferences preferences = getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            Log.d("Authentication User is Not", "");
        } else if(firebaseUser.getUid().equals(preferences.getString("authentication_uid", ""))) {

            Log.d("User Checking", String.valueOf(firebaseUser.getUid().equals(preferences.getString("authentication_uid", ""))));
            registerIntent = new Intent(this, MainActivity.class);

            // preference 에서 값을 가지고 온 뒤
            final String authentication_uid = preferences.getString("authentication_uid", "");
            final String authentication_name = preferences.getString("authentication_name", "");
            final String authentication_email = preferences.getString("authentication_email", "");
            final String authentication_profile_image = preferences.getString("authentication_check_profile_image", "");
            final String authentication_phone_number = preferences.getString("authentication_phone_number", "");
            final String authentication_online_state = preferences.getString("authentication_online_state", "");
            final String authentication_profile_background_image = preferences.getString("authentication_profile_background_image", "");
            final String authentication_state_message = preferences.getString("authentication_state_message", "");

            registerIntent.putExtra("client_uid", authentication_uid);
            registerIntent.putExtra("client_name", authentication_name);
            registerIntent.putExtra("client_email", authentication_email);
            registerIntent.putExtra("client_profile_image", authentication_profile_image);
            registerIntent.putExtra("client_phone_number", authentication_phone_number);
            registerIntent.putExtra("client_online_state", authentication_online_state);
            registerIntent.putExtra("client_profile_background_image", authentication_profile_background_image);
            registerIntent.putExtra("client_state_message", authentication_state_message);

            Log.d("Authentication User is exist", String.valueOf(authentication_uid));

            startActivity(registerIntent);
            Toast.makeText(this, "로그인 완료" + authentication_uid, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.d("유저정보도 없고, 값도 틀립니다.", "");
        }
    }

    // default init
    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);
    }

    // observer method
    @Override
    public void observerIntent() {
        BaseInterface.super.observerIntent();
        registerViewModel.getDataList.observe(this, registerData -> {
            registerIntent = new Intent(this, MainActivity.class);
            if(registerData.get(0) != null && registerData.get(1) != null && registerData.get(2) != null && registerData.get(4) != null) {
                registerIntent.putExtra("client_uid", registerData.get(0));
                registerIntent.putExtra("client_name", registerData.get(1));
                registerIntent.putExtra("client_email", registerData.get(2));
                registerIntent.putExtra("client_profile_image", registerData.get(3));
                registerIntent.putExtra("client_phone_number", registerData.get(4));
                registerIntent.putExtra("client_online_state", registerData.get(5));
                registerIntent.putExtra("client_profile_background_image", registerData.get(6));
                registerIntent.putExtra("client_state_message", registerData.get(7));

                Log.d("registerIntent", "success");
            } else {
                Log.d("registerData", "register Data null");
            }
            startActivity(registerIntent);
            finish();
        });
    }
}
