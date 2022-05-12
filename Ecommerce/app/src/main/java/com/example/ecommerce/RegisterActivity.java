package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * [Register Screen]
 *
 * Register Activity need to write your "Email Address" & "Password" & "ConfirmPassword"
 * written your information is connect to "Firebase Authentication"
 *
 * so, before when you start this project clone you have to consider of two ways
 *
 * First. if you don't want "Authentication Server" so then you don't have to copy it.
 * I'm saying that just copy what you need.
 *
 * Second. if you need to want "Authentication Server" then I recommend to you using "Firebase". for you
 * That's because this project used to "Firebase Authentication".
 */

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private TextView loginMovButtonView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button)findViewById(R.id.register_button);
        loginMovButtonView = (TextView)findViewById(R.id.alert_move_login_message);

        // "회원가입" 클릭했을 때  MainActivity.class 이동
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerButton();
            }
        });


        // "로그인" 클릭했을 때 LoginActivity.class 이동
        loginMovButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginMovButton();
            }
        });
    }


    // MainActivity Move Method
    public void registerButton(){
        Intent registerIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    // LoginActivity Move Method
    public void loginMovButton(){
        Intent loginMovIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginMovIntent);
    }
}
