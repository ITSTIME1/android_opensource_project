package com.example.firebase_chat_basic.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding activitySplashBinding;
    private FirebaseUser firebaseUser;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        preferences = getSharedPreferences("authentication", Activity.MODE_PRIVATE);

        // 현재 로그인한 사용자 정보가 없다면 RegisterActivity로 이동 혹은 LoginActivity로 이동
        // 만약 현재 로그인한 사용자가 있다면

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseUser == null) {
                    Intent registerIntent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                    Log.d("SplashActivity Log", "=======================");
                    Log.d("SplashActivity Log", "SplashActivity Firebase User is Null");
                    Log.d("SplashActivity Log", "Move to RegisterActivity");
                    Log.d("SplashActivity Log", "=======================");

                } else if(firebaseUser.getUid().equals(preferences.getString("authentication_uid", ""))) {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    Log.d("SplashActivity Log", "=======================");
                    Log.d("SplashActivity Log", "SplashActivity Firebase User is exit");
                    Log.d("SplashActivity Log", "Intent...checking..");
                    Log.d("SplashActivity Log", "preference...checking..");
                    Log.d("SplashActivity Log", "=======================");

                    // preference 에서 값을 가지고 온 뒤
                    final String authentication_uid = preferences.getString("authentication_uid", "");
                    final String authentication_name = preferences.getString("authentication_name", "");
                    final String authentication_email = preferences.getString("authentication_email", "");
                    final String authentication_profile_image = preferences.getString("authentication_check_profile_image", "");
                    final String authentication_phone_number = preferences.getString("authentication_phone_number", "");
                    final String authentication_online_state = preferences.getString("authentication_online_state", "");
                    final String authentication_profile_background_image = preferences.getString("authentication_profile_background_image", "");
                    final String authentication_state_message = preferences.getString("authentication_state_message", "");

                    mainIntent.putExtra("client_uid", authentication_uid);
                    mainIntent.putExtra("client_name", authentication_name);
                    mainIntent.putExtra("client_email", authentication_email);
                    mainIntent.putExtra("client_profile_image", authentication_profile_image);
                    mainIntent.putExtra("client_phone_number", authentication_phone_number);
                    mainIntent.putExtra("client_online_state", authentication_online_state);
                    mainIntent.putExtra("client_profile_background_image", authentication_profile_background_image);
                    mainIntent.putExtra("client_state_message", authentication_state_message);

                    Log.d("SplashActivity Log", "=======================");
                    Log.d("SplashActivity Log", "Complete..!");
                    Log.d("SplashActivity Log", "Move to MainActivity");
                    Log.d("SplashActivity Log", "=======================");
                    startActivity(mainIntent);
                    finish();

                } else {
                    Log.d("유저정보도 없고, 값도 틀립니다.", "");
                }
            }
        }, 3000); //딜레이 타임 조절


        Glide.with(this).load(R.raw.splashicon).into(activitySplashBinding.splashIcon);

        String[] loadingText = getResources().getStringArray(R.array.splash_random_text);
        Random random = new Random();
        int loadingTextIndex = random.nextInt(loadingText.length-1);
        activitySplashBinding.splashLoadingText.setText(loadingText[loadingTextIndex]);


    }
}
