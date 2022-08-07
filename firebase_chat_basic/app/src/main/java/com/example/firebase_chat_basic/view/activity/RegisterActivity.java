package com.example.firebase_chat_basic.view.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityRegisterBinding;
import com.example.firebase_chat_basic.viewModel.RegisterViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        default_init();
        observer_intent();
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
    public void default_init() {
        BaseInterface.super.default_init();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);
    }

    // observer method
    @Override
    public void observer_intent() {
        BaseInterface.super.observer_intent();
        registerViewModel.getDataList.observe(this, registerData -> {
            registerIntent = new Intent(this, MainActivity.class);
            if(registerData != null) {
                registerIntent.putExtra("client_uid", registerData.get(0).getUser_uid());
                registerIntent.putExtra("client_name", registerData.get(0).getUser_name());
                registerIntent.putExtra("client_email", registerData.get(0).getUser_email());
                registerIntent.putExtra("client_profile_image", registerData.get(0).getUser_profile_image());
                registerIntent.putExtra("client_phone_number", registerData.get(0).getUser_phone_number());
                registerIntent.putExtra("client_online_state", registerData.get(0).getUser_online_state());
                registerIntent.putExtra("client_profile_background_image", registerData.get(0).getUser_background_image());
                registerIntent.putExtra("client_state_message", registerData.get(0).getUser_state_message());
                Log.d("registerIntent", "success");
            } else {
                Log.d("registerData", "register Data null");
            }
            startActivity(registerIntent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityRegisterBinding = null;
    }
}
