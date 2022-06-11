package com.example.firebase_chat_basic.test;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SharedActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = preferences.edit();

        String getName = "하이";


        preferences.getInt("MyInt01", 0);
        editor.putString("MyString", getName);
    }
}
