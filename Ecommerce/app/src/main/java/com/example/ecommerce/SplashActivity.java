package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * [Splash Screen]
 *
 * I'm using Handler but if you want to check user authentication valid if then I don't recommend this solution
 * at that time, need to add check solutions but In this project not handling
 */

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(splashIntent);
                finish();
            }
        }, secondsDelayed * splashScreenTime);

    }
}
