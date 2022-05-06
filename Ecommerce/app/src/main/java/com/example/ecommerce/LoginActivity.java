package com.example.ecommerce;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


// * Login Screen *

// This Activity have an "activity_login.xml"
// Login Activity need to write your "Email Address" & "Password" & "ConfirmPassword"
// Written your information is connect to "Firebase Authentication"

// So, Before When you start this project clone you have to consider of two ways


// First. if you don't want "Authentication Server" so then you don't have to copy it.
// I'm saying that just copy what you need.


// Second. if you need to want "Authentication Server" then I recommend to you using "Firebase". for you
// That's because this project used to "Firebase Authentication".


public class LoginActivity extends AppCompatActivity {

    private TextView mainMovButtonView;
    private Button registerMovButton;
    private Button loginMovButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mainMovButtonView = (TextView)findViewById(R.id.alert_move_message);
        registerMovButton = (Button)findViewById(R.id.register_button_move);
        loginMovButton = (Button)findViewById(R.id.login_button);

        // "이동하기" 클릭했을때 MainActivity.class 이동
        mainMovButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityMove();
            }
        });

        // "회원가입" 클릭했을때 RegisterActivity.class 이동
        registerMovButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerActivityMove();
            }
        });


        // "로그인" 클릭했을때 LoginActivity.class 이동
        loginMovButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginActivityMove();
            }
        });
    }



    // MainActivity Move Method
    public void mainActivityMove(){
        Intent mainMovIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainMovIntent);
    }


    // RegisterActivity Move Method
    public void registerActivityMove(){
        Intent registerMovIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerMovIntent);
    }

    // LoginActivity Move Method
    public void loginActivityMove(){
        Intent loginMovIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginMovIntent);
    }
}
