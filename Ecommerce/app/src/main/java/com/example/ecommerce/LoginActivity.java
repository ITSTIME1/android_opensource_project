package com.example.ecommerce;
import android.os.Bundle;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }
}
